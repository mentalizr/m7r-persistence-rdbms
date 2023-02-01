package org.mentalizr.persistence.rdbms.edao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.ConnectionManager;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.UserLoginDAO;
import org.mentalizr.persistence.rdbms.barnacle.vo.PolicyConsentVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection"})
public class PolicyConsentEDAO {

    private static final Logger logger = LoggerFactory.getLogger(PolicyConsentEDAO.class);

    public static void deleteAllForUser(String userId) throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(UserLoginDAO.class);
        try {
            deleteAllForUser(userId, connection);
        } catch (SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, UserLoginDAO.class);
        }
    }

    public static void deleteAllForUser(String userId, Connection connection) throws SQLException {
        String sql = "DELETE FROM " + PolicyConsentVO.TABLENAME + " WHERE "
                + PolicyConsentVO.USERID + " = \"" + userId + "\"";
        logger.debug(sql);
        Statement statement = connection.createStatement();
        statement.execute(sql);
        if (statement != null) { try { statement.close(); } catch (SQLException e) {}}
    }

}
