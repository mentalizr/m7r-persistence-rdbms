package org.mentalizr.persistence.rdbms.edao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.ConnectionManager;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.UserLoginDAO;
import org.mentalizr.persistence.rdbms.barnacle.vo.PolicyConsentVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection"})
public class PolicyConsentEDAO {

    private static final Logger logger = LoggerFactory.getLogger(PolicyConsentEDAO.class);

    private static final String DELETE_ALL_FOR_USER_STATEMENT = "DELETE FROM %s WHERE %s = ?";

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
        String finalStatement = String
                .format(DELETE_ALL_FOR_USER_STATEMENT,
                        PolicyConsentVO.TABLENAME,
                        PolicyConsentVO.USERID);

        PreparedStatement preparedStatement = connection.prepareStatement(finalStatement);
        preparedStatement.setObject(1, userId);
        logger.debug(preparedStatement + "[{}] ", userId);
        try { preparedStatement.executeQuery(); } catch (SQLException ignored) {}
    }

}
