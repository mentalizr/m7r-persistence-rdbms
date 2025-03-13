package org.mentalizr.persistence.rdbms.edao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.ConnectionManager;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.UserLoginDAO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserAccessKeyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("StringConcatenationArgumentToLogCall")
public class UserAccessKeyEDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserAccessKeyEDAO.class);

    private static final String GET_UNUSED_ACCESS_KEYS_OLDER_THAN_STATEMENT =
            "SELECT uak.user_id, uak.accessKey FROM user_access_key uak " +
            "INNER JOIN user ON uak.user_id = user.id" +
            "WHERE user.creation <= ? AND user.firstActive IS NULL";

    public static List<UserAccessKeyVO> getUnusedAccessKeysOlderThan(long creationTimestamp) throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(UserAccessKeyEDAO.class);
        try {
            return getUnusedAccessKeysOlderThan(creationTimestamp, connection);
        } catch (SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, UserLoginDAO.class);
        }
    }

    public static List<UserAccessKeyVO> getUnusedAccessKeysOlderThan(long creationTimestamp, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(GET_UNUSED_ACCESS_KEYS_OLDER_THAN_STATEMENT);
        preparedStatement.setObject(1, creationTimestamp, Types.BIGINT);
        logger.debug(GET_UNUSED_ACCESS_KEYS_OLDER_THAN_STATEMENT + " [" + creationTimestamp + "]");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<UserAccessKeyVO> accessKeys = new ArrayList<>();
        try {
            while (resultSet.next()) {
                UserAccessKeyVO userAccessKeyVO = new UserAccessKeyVO(resultSet.getObject("user_id", String.class));
                userAccessKeyVO.setAccessKey(resultSet.getObject("accessKey", String.class));
                accessKeys.add(userAccessKeyVO);
            }
        } finally {
            if (resultSet != null) { try { resultSet.close(); } catch (SQLException ignored) {}}
            try { preparedStatement.close(); } catch (SQLException ignored) {}
        }
        return accessKeys;
    }

}
