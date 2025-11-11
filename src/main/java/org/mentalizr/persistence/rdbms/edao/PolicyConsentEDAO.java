package org.mentalizr.persistence.rdbms.edao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.ConnectionManager;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.UserLoginDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import static org.mentalizr.persistence.rdbms.barnacle.vo.PolicyConsentVO.TABLENAME;
import static org.mentalizr.persistence.rdbms.barnacle.vo.PolicyConsentVO.USERID;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection"})
public class PolicyConsentEDAO {

    private static final Logger logger = LoggerFactory.getLogger(PolicyConsentEDAO.class);

    private static final String DELETE_ALL_FOR_USER_STATEMENT
            = "DELETE FROM " + TABLENAME + " WHERE " + USERID + " = ?";

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
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL_FOR_USER_STATEMENT);
        preparedStatement.setObject(1, userId, Types.VARCHAR);
        logger.debug(DELETE_ALL_FOR_USER_STATEMENT + " [{}]", userId);
        try { preparedStatement.executeQuery(); } catch (SQLException ignored) {}
    }

}
