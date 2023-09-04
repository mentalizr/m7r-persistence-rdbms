package org.mentalizr.persistence.rdbms.edao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.ConnectionManager;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.UserLoginDAO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserLoginVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class UserLoginEDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserLoginEDAO.class);

    private static final String UPDATE_PASSWORD_HASH_STATEMENT = "UPDATE user_login SET password_hash = ? WHERE user_id = ?";

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

    public static void updatePasswordHash(String userId, String passwordHash, Connection connection) throws SQLException {
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
        String sql = "UPDATE " + UserLoginVO.TABLENAME + " SET "
                + UserLoginVO.RENEWPASSWORDREQUIRED + " = false"
                + " WHERE "
                + UserLoginVO.USERID + " = " + getValueExpression(userId, "VARCHAR(255)");
        logger.debug(sql);
        Statement statement = connection.createStatement();
        statement.execute(sql);
        try { statement.close(); } catch (SQLException ignored) {}
    }

    private static String getValueExpression(Object o, String sqlType) {
        if (o == null) { return "NULL"; }
        if (sqlType.startsWith("VARCHAR") || sqlType.equals("DATE")) { return "'" + o + "'"; }
        return "" + o;
    }


}
