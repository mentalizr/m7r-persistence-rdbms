package org.mentalizr.persistence.rdbms.barnacle.manual.dao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.ConnectionManager;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.RolePatientDAO;
import org.mentalizr.persistence.rdbms.barnacle.manual.vo.UserLoginPatientCompositeVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.*;
import org.mentalizr.persistence.rdbms.edao.RolePatientEDAO;
import org.mentalizr.serviceObjects.requestObjects.UserListQuerySO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserLoginPatientCompositeDAO {
    private static final Logger logger = LoggerFactory.getLogger(RolePatientEDAO.class);

    private static final String FIND_ALL_BY_STATEMENT = "SELECT role_patient.user_id, therapist_id, project_id, patient_program.program_id, user_login.username, password_hash, email, first_name, last_name, gender, second_fa, email_confirmation, email_conf_token, email_conf_code, renew_pw_req, user.active, creation, firstActive, lastActive" +
            " FROM role_patient" +
            " INNER JOIN patient_program ON role_patient.user_id = patient_program.user_id" +
            " INNER JOIN user_login ON role_patient.user_id = user_login.user_id" +
            " INNER JOIN user ON role_patient.user_id = user.id" +
            " WHERE (? = 1 AND role_patient.project_id = ?) OR (? = 1 AND patient_program.program_id = ?) OR (? = 1 AND ? = 1 AND patient_program.program_id = ? AND role_patient.project_id = ?)";

    public static List<UserLoginPatientCompositeVO> findAllUserBy(UserListQuerySO userListQuerySO)
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

    public static List<UserLoginPatientCompositeVO> findAllUserBy(UserListQuerySO userListQuerySO, Connection connection)
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
        List<UserLoginPatientCompositeVO> userLoginCompositeVOs = new ArrayList<>();

        while (resultSet.next()) {
            UserLoginPatientCompositeVO userLoginPatientCompositeVO = processResultSet(resultSet);
            userLoginCompositeVOs.add(userLoginPatientCompositeVO);
        }

        return userLoginCompositeVOs;
    }

    private static UserLoginPatientCompositeVO processResultSet(ResultSet resultSet) throws SQLException {
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

        RolePatientVO rolePatientVO = new RolePatientVO(userVO.getId());
        rolePatientVO.setTherapistId(resultSet.getString("therapist_id"));
        rolePatientVO.setProjectId(resultSet.getString("project_id"));

        PatientProgramPK patientProgramPK
                = new PatientProgramPK(userVO.getId(), resultSet.getString("program_id"));
        PatientProgramVO patientProgramVO = new PatientProgramVO(patientProgramPK);

        return new UserLoginPatientCompositeVO(userVO, userLoginVO, patientProgramVO, rolePatientVO);
    }

}
