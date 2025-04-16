package org.mentalizr.persistence.rdbms.edao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.ConnectionManager;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.UserLoginDAO;
import org.mentalizr.persistence.rdbms.barnacle.manual.vo.UserAccessKeyPatientCompositeVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("StringConcatenationArgumentToLogCall")
public class UserAccessKeyEDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserAccessKeyEDAO.class);
    private enum Type { UNUSED, ACTIVATED }

    private static final String GET_EXPIRED_STATEMENT =
            "SELECT user.id, user.active, user.creation, uak.accessKey, patient_program.program_id, " +
                    "patient_program.blocking, role_patient.therapist_id, role_patient.project_id " +
                    "FROM user_access_key uak " +
                    "INNER JOIN user ON uak.user_id = user.id " +
                    "INNER JOIN role_patient ON user.id = role_patient.user_id " +
                    "INNER JOIN patient_program ON user.id = patient_program.user_id " +
                    "WHERE user.creation <= ? AND user.firstActive IS NULL";

    private static final String GET_LAST_USED_BEFORE_STATEMENT =
            "SELECT user.id, user.active, user.creation, user.firstActive, user.lastActive, uak.accessKey, patient_program.program_id, " +
                    "patient_program.blocking, role_patient.therapist_id, role_patient.project_id " +
                    "FROM user_access_key uak " +
                    "INNER JOIN user ON uak.user_id = user.id " +
                    "INNER JOIN role_patient ON user.id = role_patient.user_id " +
                    "INNER JOIN patient_program ON user.id = patient_program.user_id " +
                    "WHERE user.lastActive <= ?";

    public static List<UserAccessKeyPatientCompositeVO> getExpired(
            long creationTimestamp) throws DataSourceException {

        Connection connection = ConnectionManager.openConnection(UserAccessKeyEDAO.class);
        try {
            return getExpiredByType(Type.UNUSED, creationTimestamp, connection);
        } catch (SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, UserLoginDAO.class);
        }
    }

    public static List<UserAccessKeyPatientCompositeVO> getLastUsedBefore(
            long creationTimestamp) throws DataSourceException {

        Connection connection = ConnectionManager.openConnection(UserAccessKeyEDAO.class);
        try {
            return getExpiredByType(Type.ACTIVATED, creationTimestamp, connection);
        } catch (SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, UserLoginDAO.class);
        }
    }

    private static List<UserAccessKeyPatientCompositeVO> getExpiredByType(
            Type type,
            long creationTimestamp,
            Connection connection) throws SQLException {

        PreparedStatement preparedStatement;
        if (type == Type.ACTIVATED) {
            preparedStatement = connection.prepareStatement(GET_LAST_USED_BEFORE_STATEMENT);
        } else if (type == Type.UNUSED) {
            preparedStatement = connection.prepareStatement(GET_EXPIRED_STATEMENT);
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type);
        }

        preparedStatement.setObject(1, creationTimestamp, Types.BIGINT);
        if (type == Type.ACTIVATED) {
            logger.debug(GET_LAST_USED_BEFORE_STATEMENT + " [" + creationTimestamp + "]");
        } else {
            logger.debug(GET_EXPIRED_STATEMENT + " [" + creationTimestamp + "]");
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        List<UserAccessKeyPatientCompositeVO> accessKeys = new ArrayList<>();
        try {
            while (resultSet.next()) {
                UserVO userVO = new UserVO(resultSet.getString("id"));
                userVO.setActive(resultSet.getBoolean("active"));
                userVO.setCreation(resultSet.getLong("creation"));
                if (type == Type.ACTIVATED) {
                    userVO.setFirstActive(resultSet.getLong("firstActive"));
                    userVO.setLastActive(resultSet.getLong("lastActive"));
                } else {
                    userVO.setFirstActive(null);
                    userVO.setLastActive(null);
                }

                UserAccessKeyVO userAccessKeyVO = new UserAccessKeyVO(resultSet.getString("id"));
                userAccessKeyVO.setAccessKey(resultSet.getObject("accessKey", String.class));

                PatientProgramVO patientProgramVO = new PatientProgramVO(
                        new PatientProgramPK(
                                resultSet.getString("id"),
                                resultSet.getString("program_id")
                        )
                );
                patientProgramVO.setBlocking(resultSet.getBoolean("blocking"));

                RolePatientVO rolePatientVO = new RolePatientVO(resultSet.getString("id"));
                rolePatientVO.setTherapistId(resultSet.getString("therapist_id"));
                rolePatientVO.setProjectId(resultSet.getString("project_id"));

                UserAccessKeyPatientCompositeVO userAccessKeyPatientCompositeVO
                        = new UserAccessKeyPatientCompositeVO(userVO, userAccessKeyVO, rolePatientVO, patientProgramVO);

                accessKeys.add(userAccessKeyPatientCompositeVO);
            }
        } finally {
            if (resultSet != null) { try { resultSet.close(); } catch (SQLException ignored) {}}
            try { preparedStatement.close(); } catch (SQLException ignored) {}
        }
        return accessKeys;
    }

}
