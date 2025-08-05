package org.mentalizr.persistence.rdbms.edao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.ConnectionManager;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.vo.ProgramVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProgramEDAO {

    private static final String SQL_COUNT = "SELECT COUNT(*) FROM " + ProgramVO.TABLENAME;

    public static int getProgramCount() throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(UserLoginEDAO.class);
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_COUNT);
        ) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new IllegalStateException("Error while getting program count.");
            }
        } catch (SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, UserLoginEDAO.class);
        }
    }


}
