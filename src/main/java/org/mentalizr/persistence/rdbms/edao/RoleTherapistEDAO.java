package org.mentalizr.persistence.rdbms.edao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.ConnectionManager;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RoleTherapistEDAO {

    private static final String FIND_ALL_ID_STATEMENT = "SELECT user_id FROM role_therapist";

    public static List<String> findAllIds() throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(RoleTherapistEDAO.class);
        try {
            return Commons.findAllIds(connection, FIND_ALL_ID_STATEMENT, "user_id");
        } catch (SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, RoleTherapistEDAO.class);
        }
    }

}
