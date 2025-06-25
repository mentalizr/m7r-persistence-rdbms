package org.mentalizr.persistence.rdbms.barnacle.manual.dao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.ConnectionManager;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.manual.vo.UserLoginPatientCompositeVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.*;
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
    private static final Logger logger = LoggerFactory.getLogger(UserLoginPatientCompositeDAO.class);

    private static final String FIND_ALL_BY_STATEMENT =
            "SELECT rp.user_id, rp.therapist_id, rp.project_id, " +
                    "pp.program_id, pp.blocking, " +
                    "ul.username, ul.password_hash, ul.email, ul.first_name, ul.last_name, ul.gender, ul.second_fa, " +
                    "ul.email_confirmation, ul.email_conf_token, ul.email_conf_code, ul.renew_pw_req, " +
                    "u.active, u.creation, u.firstActive, u.lastActive " +
                    "FROM role_patient rp " +
                    "INNER JOIN patient_program pp ON rp.user_id = pp.user_id " +
                    "INNER JOIN user_login ul ON rp.user_id = ul.user_id " +
                    "INNER JOIN user u ON rp.user_id = u.id " +
                    "WHERE CASE " +
                    "WHEN (? = 1 AND ? = 1) THEN pp.program_id = ? AND rp.project_id = ? " +
                    "WHEN (? = 1 AND ? = 0) THEN pp.program_id = ? " +
                    "WHEN (? = 0 AND ? = 1) THEN rp.project_id = ? " +
                    "ELSE TRUE " +
                    "END";
//    private static final String FIND_ALL_BY_STATEMENT =
//            "SELECT role_patient.user_id, therapist_id, project_id, patient_program.program_id, blocking, " +
//                    "user_login.username, password_hash, email, first_name, last_name, gender, second_fa, " +
//                    "email_confirmation, email_conf_token, email_conf_code, renew_pw_req, " +
//                    "user.active, creation, firstActive, lastActive" +
//                    " FROM role_patient" +
//                    " INNER JOIN patient_program ON role_patient.user_id = patient_program.user_id" +
//                    " INNER JOIN user_login ON role_patient.user_id = user_login.user_id" +
//                    " INNER JOIN user ON role_patient.user_id = user.id" +
//                    " WHERE CASE " +
//                    " WHEN (? = 1 AND ? = 1) THEN patient_program.program_id = ? AND role_patient.project_id = ?" +
//                    " WHEN (? = 1 AND ? = 0) THEN patient_program.program_id = ?" +
//                    " WHEN (? = 0 AND ? = 1) THEN role_patient.project_id = ?" +
//                    " ELSE TRUE" +
//                    " END";

    public static List<UserLoginPatientCompositeVO> findAllUserBy(UserListQuerySO userListQuerySO)
            throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(UserLoginPatientCompositeDAO.class);
        try {
            return findAllUserBy(userListQuerySO, connection);
        } catch(SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, UserLoginPatientCompositeDAO.class);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    public static List<UserLoginPatientCompositeVO> findAllUserBy(UserListQuerySO userListQuerySO, Connection connection)
            throws SQLException {
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_STATEMENT)) {
            preparedStatement.setInt(1, userListQuerySO.hasProgram() ? 1 : 0);
            preparedStatement.setInt(2, userListQuerySO.hasProject() ? 1 : 0);
            preparedStatement.setString(3, userListQuerySO.getProgram());
            preparedStatement.setString(4, userListQuerySO.getProject());
            preparedStatement.setInt(5, userListQuerySO.hasProgram() ? 1 : 0);
            preparedStatement.setInt(6, userListQuerySO.hasProject() ? 1 : 0);
            preparedStatement.setString(7, userListQuerySO.getProgram());
            preparedStatement.setInt(8, userListQuerySO.hasProgram() ? 1 : 0);
            preparedStatement.setInt(9, userListQuerySO.hasProject() ? 1 : 0);
            preparedStatement.setString(10, userListQuerySO.getProject());

            logger.debug(FIND_ALL_BY_STATEMENT + " [{}] [{}] [{}] [{}]",
                    userListQuerySO.hasProgram(),
                    userListQuerySO.getProgram(),
                    userListQuerySO.hasProject(),
                    userListQuerySO.getProject());

            resultSet = preparedStatement.executeQuery();
            List<UserLoginPatientCompositeVO> userLoginCompositeVOs = new ArrayList<>();

            while (resultSet.next()) {
                UserLoginPatientCompositeVO userLoginPatientCompositeVO = processResultSet(resultSet);
                userLoginCompositeVOs.add(userLoginPatientCompositeVO);
            }

            return userLoginCompositeVOs;
        }
    }

    private static UserLoginPatientCompositeVO processResultSet(ResultSet resultSet) throws SQLException {
        UserVO userVO = UserDAOCommons.createUserVO(resultSet);

        UserLoginVO userLoginVO = new UserLoginVO(resultSet.getObject("user_id", String.class));
        userLoginVO.setPasswordHash(resultSet.getObject("password_hash", String.class));
        userLoginVO.setUsername(resultSet.getObject("username", String.class));
        userLoginVO.setEmail(resultSet.getObject("email", String.class));
        userLoginVO.setEmailConfirmation(resultSet.getObject("email_confirmation", Long.class));
        userLoginVO.setEmailConfCode(resultSet.getObject("email_conf_code", String.class));
        userLoginVO.setEmailConfToken(resultSet.getObject("email_conf_token", String.class));
        userLoginVO.setFirstName(resultSet.getObject("first_name",String.class));
        userLoginVO.setLastName(resultSet.getObject("last_name", String.class));
        userLoginVO.setGender(resultSet.getObject("gender", Integer.class));
        userLoginVO.setSecondFA(resultSet.getObject("second_fa", Boolean.class));
        userLoginVO.setRenewPasswordRequired(resultSet.getObject("renew_pw_req", Boolean.class));

        RolePatientVO rolePatientVO = UserDAOCommons.createRolePatientVO(resultSet);
        PatientProgramVO patientProgramVO = UserDAOCommons.createPatientProgramVO(resultSet);

        return new UserLoginPatientCompositeVO(userVO, userLoginVO, patientProgramVO, rolePatientVO);
    }

}
