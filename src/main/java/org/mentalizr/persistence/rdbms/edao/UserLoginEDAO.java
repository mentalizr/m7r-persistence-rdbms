package org.mentalizr.persistence.rdbms.edao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.ConnectionManager;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.UserDAO;
import org.mentalizr.persistence.rdbms.barnacle.dao.UserLoginDAO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserLoginVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserLoginEDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserLoginEDAO.class);

    private static final String UPDATE_PASSWORD_HASH_STATEMENT = "UPDATE user_login SET password_hash = ? WHERE user_id = ?";
    private static final String FIND_ALL_ID_STATEMENT = "SELECT user_id FROM user_login";
    private static final String UNSET_RENEW_PASSWORD_STATEMENT =
            "UPDATE %s SET %s = false WHERE %s = ?";

    public static void updatePasswordHash(String userId, String passwordHash) throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(UserLoginDAO.class);
        try {
            updatePasswordHash(userId, passwordHash, connection);
        } catch (SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, UserLoginDAO.class);
        }
    }

    public static void updatePasswordHash(String userId, String passwordHash, Connection connection)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD_HASH_STATEMENT);
        preparedStatement.setObject(1, passwordHash, Types.VARCHAR);
        preparedStatement.setObject(2, userId, Types.VARCHAR);
        logger.debug(UPDATE_PASSWORD_HASH_STATEMENT + " [" + passwordHash + "][" + userId + "]");
        preparedStatement.executeUpdate();
        try { preparedStatement.close(); } catch (SQLException ignored) {}
    }

    public static void unsetRenewPasswordRequired(String userId) throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(UserLoginDAO.class);
        try {
            unsetRenewPasswordRequired(userId, connection);
        } catch (SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, UserLoginDAO.class);
        }
    }

    public static void unsetRenewPasswordRequired(String userId, Connection connection) throws SQLException {
        String finalStatement = String
                .format(UNSET_RENEW_PASSWORD_STATEMENT,
                        UserLoginVO.TABLENAME,
                        UserLoginVO.RENEWPASSWORDREQUIRED,
                        UserLoginVO.USERID);

        PreparedStatement preparedStatement = connection.prepareStatement(finalStatement);
        preparedStatement.setObject(1, getValueExpression(userId, "VARCHAR(255)"));

        logger.debug(preparedStatement + "[{}], ", userId);
        try { preparedStatement.executeQuery(); } catch (SQLException ignored) {}
    }

    private static String getValueExpression(Object o, String sqlType) {
        if (o == null) { return "NULL"; }
        if (sqlType.startsWith("VARCHAR") || sqlType.equals("DATE")) { return "'" + o + "'"; }
        return "" + o;
    }

    public static List<String> findAllIds() throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(UserLoginEDAO.class);
        try {
            return Commons.findAllIds(connection, FIND_ALL_ID_STATEMENT, "user_id");
        } catch (SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, UserLoginEDAO.class);
        }
    }

}
