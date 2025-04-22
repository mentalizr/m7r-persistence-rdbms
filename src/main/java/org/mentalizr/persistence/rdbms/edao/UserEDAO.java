package org.mentalizr.persistence.rdbms.edao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.ConnectionManager;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.UserDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserEDAO {

    private static final String FIND_ALL_ID_STATEMENT = "SELECT id FROM user";

    public static List<String> findAllIds() throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(UserEDAO.class);
        try {
            return Commons.findAllIds(connection, FIND_ALL_ID_STATEMENT, "id");
        } catch (SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, UserEDAO.class);
        }
    }

}
