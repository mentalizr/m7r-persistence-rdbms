package org.mentalizr.persistence.rdbms.edao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.ConnectionManager;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.UserLoginDAO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserLoginVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UserLoginEDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserLoginEDAO.class);

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
        String sql = "UPDATE " + UserLoginVO.TABLENAME + " SET "
                + UserLoginVO.PASSWORDHASH + " = " + getValueExpression(passwordHash, "VARCHAR(255)") + ", "
                + " WHERE "
                + UserLoginVO.USERID + " = " + getValueExpression(userId, "VARCHAR(255)");
        logger.debug(sql);
        Statement statement = connection.createStatement();
        statement.execute(sql);
        try { statement.close(); } catch (SQLException ignored) {}
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
