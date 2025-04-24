package org.mentalizr.persistence.rdbms.barnacle.manual.dao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.ConnectionManager;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.manual.vo.UserAccessKeyPatientCompositeVO;
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

public class UserLoginAccessKeyCompositeDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginAccessKeyCompositeDAO.class);

    private static final String FIND_ALL_BY_STATEMENT = "SELECT user.active, creation, firstActive, lastActive, user_access_key.user_id, accessKey, therapist_id, patient_program.program_id, blocking, role_patient.project_id" +
            " FROM user_access_key" +
            " INNER JOIN user ON user_access_key.user_id = user.id" +
            " INNER JOIN patient_program ON user_access_key.user_id = patient_program.user_id" +
            " INNER JOIN role_patient ON user_access_key.user_id = role_patient.user_id" +
            " WHERE CASE" +
            " WHEN (? = 1 AND ? = 1) THEN patient_program.program_id = ? AND role_patient.project_id = ?" +
            " WHEN (? = 1 AND ? = 0) THEN patient_program.program_id = ?" +
            " WHEN (? = 0 AND ? = 1) THEN role_patient.project_id = ?" +
            " ELSE TRUE" +
            " END";

    public static List<UserAccessKeyPatientCompositeVO> findAllUserBy(UserListQuerySO userListQuerySO)
            throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(UserLoginAccessKeyCompositeDAO.class);
        try {
            return findAllUserBy(userListQuerySO, connection);
        } catch(SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, UserLoginAccessKeyCompositeDAO.class);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    public static List<UserAccessKeyPatientCompositeVO> findAllUserBy(UserListQuerySO userListQuerySO, Connection connection)
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
            List<UserAccessKeyPatientCompositeVO> userLoginAccessKeyCompositeVOs = new ArrayList<>();

            while (resultSet.next()) {
                UserAccessKeyPatientCompositeVO userLoginAccessKeyCompositeVO = processResultSet(resultSet);
                userLoginAccessKeyCompositeVOs.add(userLoginAccessKeyCompositeVO);
            }

            return userLoginAccessKeyCompositeVOs;
        }
    }

    private static UserAccessKeyPatientCompositeVO processResultSet(ResultSet resultSet) throws SQLException {
        UserVO userVO = UserDAOCommons.createUserVO(resultSet);

        UserAccessKeyVO userAccessKeyVO = new UserAccessKeyVO(userVO.getId());
        userAccessKeyVO.setUserId(userVO.getId());
        userAccessKeyVO.setAccessKey(resultSet.getObject("accessKey", String.class));

        RolePatientVO rolePatientVO = UserDAOCommons.createRolePatientVO(resultSet);
        PatientProgramVO  patientProgramVO = UserDAOCommons.createPatientProgramVO(resultSet);

        return new UserAccessKeyPatientCompositeVO(userVO, userAccessKeyVO, rolePatientVO, patientProgramVO);
    }

}
