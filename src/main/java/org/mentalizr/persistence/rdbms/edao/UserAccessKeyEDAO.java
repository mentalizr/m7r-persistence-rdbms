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

    private static final String GET_UNUSED_ACCESS_KEYS_OLDER_THAN_STATEMENT =
            "SELECT user.id, user.active, user.creation, uak.accessKey, patient_program.program_id, " +
                    "role_patient.therapist_id, role_patient.project_id FROM user_access_key uak " +
                    "INNER JOIN user ON uak.user_id = user.id " +
                    "INNER JOIN role_patient ON user.id = role_patient.user_id " +
                    "INNER JOIN patient_program ON user.id = patient_program.user_id " +
                    "WHERE user.creation <= ? AND user.firstActive IS NULL";

    public static List<UserAccessKeyPatientCompositeVO> getUnusedAccessKeysOlderThan(
            long creationTimestamp) throws DataSourceException {

        Connection connection = ConnectionManager.openConnection(UserAccessKeyEDAO.class);
        try {
            return getUnusedAccessKeysOlderThan(creationTimestamp, connection);
        } catch (SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, UserLoginDAO.class);
        }
    }

    public static List<UserAccessKeyPatientCompositeVO> getUnusedAccessKeysOlderThan(
            long creationTimestamp,
            Connection connection) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(GET_UNUSED_ACCESS_KEYS_OLDER_THAN_STATEMENT);
        preparedStatement.setObject(1, creationTimestamp, Types.BIGINT);
        logger.debug(GET_UNUSED_ACCESS_KEYS_OLDER_THAN_STATEMENT + " [" + creationTimestamp + "]");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<UserAccessKeyPatientCompositeVO> accessKeys = new ArrayList<>();
        try {
            while (resultSet.next()) {
                UserVO userVO = new UserVO(resultSet.getString("user_id"));
                userVO.setActive(resultSet.getBoolean("active"));
                userVO.setCreation(resultSet.getLong("creation"));
                userVO.setFirstActive(null);
                userVO.setLastActive(null);

                UserAccessKeyVO userAccessKeyVO = new UserAccessKeyVO(resultSet.getString("user_id"));
                userAccessKeyVO.setAccessKey(resultSet.getObject("accessKey", String.class));

                PatientProgramVO patientProgramVO = new PatientProgramVO(
                        new PatientProgramPK(
                                resultSet.getString("user_id"),
                                resultSet.getString("program_id")
                        )
                );
                patientProgramVO.setBlocking(resultSet.getBoolean("blocking"));

                RolePatientVO rolePatientVO = new RolePatientVO(resultSet.getString("user_id"));
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
