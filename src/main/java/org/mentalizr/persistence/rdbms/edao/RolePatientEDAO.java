package org.mentalizr.persistence.rdbms.edao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.ConnectionManager;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.RolePatientDAO;
import org.mentalizr.persistence.rdbms.barnacle.manual.vo.UserLoginCompositeVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserLoginVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserVO;
import org.mentalizr.serviceObjects.requestObjects.UserListQuerySO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection"})
public class RolePatientEDAO {

    private static final Logger logger = LoggerFactory.getLogger(RolePatientEDAO.class);

    private static final String PATIENT_PROGRAM_PROJECT_STATEMENT =
            "SELECT role_patient.user_id FROM role_patient, patient_program WHERE project_id = ? AND patient_program.user_id = role_patient.user_id AND patient_program.program_id = ?";

    private static final String PATIENT_PROGRAM_UNASSIGNED_PROJECT_STATEMENT =
            "SELECT role_patient.user_id FROM role_patient, patient_program WHERE project_id IS NULL AND patient_program.user_id = role_patient.user_id AND patient_program.program_id = ?";
    private static final String FIND_ALL_BY_STATEMENT = "SELECT role_patient.user_id, user_login.username, password_hash, email, first_name, last_name, gender, second_fa, email_confirmation, email_conf_token, email_conf_code, renew_pw_req, user.active, creation, firstActive, lastActive" +
            " FROM role_patient" +
            " INNER JOIN patient_program ON role_patient.user_id = patient_program.user_id" +
            " INNER JOIN user_login ON role_patient.user_id = user_login.user_id" +
            " INNER JOIN user ON role_patient.user_id = user.id" +
            " WHERE (? = 1 AND role_patient.project_id = ?) OR (? = 1 AND patient_program.program_id = ?) OR (? = 1 AND ? = 1 AND patient_program.program_id = ? AND role_patient.project_id = ?)";

    public static List<String> findAllUserIdsForProgramAndProject(String programId, String projectId) throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(RolePatientEDAO.class);
        try {
            return findAllUserIdsForProgramAndProject(programId, projectId, connection);
        } catch(SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, RolePatientDAO.class);
        }
    }

    public static List<String> findAllUserIdsForProgramAndProject(String programId, String projectId, Connection connection)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(PATIENT_PROGRAM_PROJECT_STATEMENT);
        preparedStatement.setObject(1, projectId, Types.VARCHAR);
        preparedStatement.setObject(2, programId, Types.VARCHAR);
        logger.debug(PATIENT_PROGRAM_PROJECT_STATEMENT + " [{}] [{}]", projectId, programId);
        return processResultSet(preparedStatement);
    }

    public static List<String> findAllUserIdsForProgramAndUnassignedProject(String programId)
            throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(RolePatientEDAO.class);
        try {
            return findAllUserIdsForProgramAndUnassignedProject(programId, connection);
        } catch(SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, RolePatientDAO.class);
        }
    }

    public static List<String> findAllUserIdsForProgramAndUnassignedProject(String programId, Connection connection)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(PATIENT_PROGRAM_UNASSIGNED_PROJECT_STATEMENT);
        preparedStatement.setObject(1, programId, Types.VARCHAR);
        logger.debug(PATIENT_PROGRAM_PROJECT_STATEMENT + " [{}]", programId);
        return processResultSet(preparedStatement);
    }


    public static List<UserLoginCompositeVO> findAllUserBy(UserListQuerySO userListQuerySO)
            throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(RolePatientEDAO.class);
        try {
            return findAllUserBy(userListQuerySO, connection);
        } catch(SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, RolePatientDAO.class);
        }
    }

    public static List<UserLoginCompositeVO> findAllUserBy(UserListQuerySO userListQuerySO, Connection connection)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_STATEMENT);
        preparedStatement.setInt(1, userListQuerySO.isProject() ? 1 : 0);
        preparedStatement.setString(2, userListQuerySO.getProjectName());
        preparedStatement.setInt(3, userListQuerySO.isProgram() ? 1 : 0);
        preparedStatement.setString(4, userListQuerySO.getProgramName());
        preparedStatement.setInt(5, userListQuerySO.isProject() ? 1 : 0);
        preparedStatement.setInt(6, userListQuerySO.isProgram() ? 1 : 0);
        preparedStatement.setString(7, userListQuerySO.getProgramName());
        preparedStatement.setString(8, userListQuerySO.getProjectName());

        logger.debug(FIND_ALL_BY_STATEMENT + " [{}]", userListQuerySO.getProgramName() + ":"
                + userListQuerySO.getProjectName());
        ResultSet resultSet = preparedStatement.executeQuery();
        List<UserLoginCompositeVO> userLoginCompositeVOs = new ArrayList<>();

        while (resultSet.next()) {
            UserLoginCompositeVO userLoginCompositeVO = processResultSet(resultSet);
            userLoginCompositeVOs.add(userLoginCompositeVO);
        }

        return userLoginCompositeVOs;
    }

    private static UserLoginCompositeVO processResultSet(ResultSet resultSet) throws SQLException {
        UserVO userVO = new UserVO(resultSet.getObject("user_id", String.class));
        userVO.setActive(resultSet.getBoolean("active"));
        userVO.setCreation(resultSet.getLong("creation"));
        userVO.setFirstActive(resultSet.getLong("firstActive"));
        userVO.setLastActive(resultSet.getLong("lastActive"));

        UserLoginVO userLoginVO = new UserLoginVO(resultSet.getObject("user_id", String.class));
        userLoginVO.setPasswordHash(resultSet.getString("password_hash"));
        userLoginVO.setUsername(resultSet.getString("username"));
        userLoginVO.setEmail(resultSet.getString("email"));
        userLoginVO.setEmailConfirmation(resultSet.getLong("email_confirmation"));
        userLoginVO.setEmailConfCode(resultSet.getString("email_conf_code"));
        userLoginVO.setEmailConfToken(resultSet.getString("email_conf_token"));
        userLoginVO.setFirstName(resultSet.getString("first_name"));
        userLoginVO.setLastName(resultSet.getString("last_name"));
        userLoginVO.setGender(resultSet.getInt("gender"));
        userLoginVO.setSecondFA(resultSet.getBoolean("second_fa"));
        userLoginVO.setRenewPasswordRequired(resultSet.getBoolean("renew_pw_req"));

        return new UserLoginCompositeVO(userVO, userLoginVO);
    }

    private static List<String> processResultSet(PreparedStatement preparedStatement) throws SQLException {
        try (preparedStatement; ResultSet resultSet = preparedStatement.executeQuery()) {
            List<String> userIds = new ArrayList<>();

            while (resultSet.next()) {
                String userId = resultSet.getObject("user_id", String.class);
                userIds.add(userId);
            }
            return userIds;
        }
    }

}
