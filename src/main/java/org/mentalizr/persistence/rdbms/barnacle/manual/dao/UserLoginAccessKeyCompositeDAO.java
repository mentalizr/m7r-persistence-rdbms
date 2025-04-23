package org.mentalizr.persistence.rdbms.barnacle.manual.dao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.ConnectionManager;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.manual.vo.UserAccessKeyPatientCompositeVO;
import org.mentalizr.persistence.rdbms.barnacle.manual.vo.UserLoginAccessKeyCompositeVO;
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
            " ELSE patient_program.program_id = '' AND role_patient.project_id = ''" +
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

    public static List<UserAccessKeyPatientCompositeVO> findAllUserBy(UserListQuerySO userListQuerySO, Connection connection)
            throws SQLException {
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_STATEMENT)) {
            preparedStatement.setInt(1, userListQuerySO.isProgram() ? 1 : 0);
            preparedStatement.setInt(2, userListQuerySO.isProject() ? 1 : 0);
            preparedStatement.setString(3, userListQuerySO.getProgramName());
            preparedStatement.setString(4, userListQuerySO.getProjectName());
            preparedStatement.setInt(5, userListQuerySO.isProgram() ? 1 : 0);
            preparedStatement.setInt(6, userListQuerySO.isProject() ? 1 : 0);
            preparedStatement.setString(7, userListQuerySO.getProgramName());
            preparedStatement.setInt(8, userListQuerySO.isProgram() ? 1 : 0);
            preparedStatement.setInt(9, userListQuerySO.isProject() ? 1 : 0);
            preparedStatement.setString(10, userListQuerySO.getProjectName());

            logger.debug(FIND_ALL_BY_STATEMENT + " [{}] [{}] [{}] [{}]",
                    userListQuerySO.isProgram(),
                    userListQuerySO.getProgramName(),
                    userListQuerySO.isProject(),
                    userListQuerySO.getProjectName());

            resultSet = preparedStatement.executeQuery();
        }
        List<UserAccessKeyPatientCompositeVO> userLoginAccessKeyCompositeVOs = new ArrayList<>();

        while (resultSet.next()) {
            UserAccessKeyPatientCompositeVO userLoginAccessKeyCompositeVO = processResultSet(resultSet);
            userLoginAccessKeyCompositeVOs.add(userLoginAccessKeyCompositeVO);
        }
        return userLoginAccessKeyCompositeVOs;
    }

    private static UserAccessKeyPatientCompositeVO processResultSet(ResultSet resultSet) throws SQLException {
        UserVO userVO = new UserVO(resultSet.getObject("user_id", String.class));
        userVO.setActive(resultSet.getObject("active", Boolean.class));
        userVO.setCreation(resultSet.getObject("creation", Long.class));
        userVO.setFirstActive(resultSet.getObject("firstActive", Long.class));
        userVO.setLastActive(resultSet.getObject("lastActive", Long.class));

        UserAccessKeyVO userAccessKeyVO = new UserAccessKeyVO(userVO.getId());
        userAccessKeyVO.setUserId(userVO.getId());
        userAccessKeyVO.setAccessKey(resultSet.getObject("accessKey", String.class));

        RolePatientVO rolePatientVO = new RolePatientVO(userVO.getId());
        rolePatientVO.setTherapistId(resultSet.getObject("therapist_id", String.class));
        rolePatientVO.setProjectId(resultSet.getObject("project_id", String.class));

        PatientProgramPK patientProgramPK
                = new PatientProgramPK(userVO.getId(), resultSet.getObject("program_id", String.class));
        PatientProgramVO patientProgramVO = new PatientProgramVO(patientProgramPK);
        patientProgramVO.setBlocking(resultSet.getObject("blocking", Boolean.class));

        return new UserAccessKeyPatientCompositeVO(userVO, userAccessKeyVO, rolePatientVO, patientProgramVO);
    }

}
