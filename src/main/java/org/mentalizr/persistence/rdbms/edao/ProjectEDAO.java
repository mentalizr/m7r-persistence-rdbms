package org.mentalizr.persistence.rdbms.edao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.ConnectionManager;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.vo.ProjectVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProjectEDAO {

    private static final String SQL_COUNT = "SELECT COUNT(*) FROM " + ProjectVO.TABLENAME;

    public static int getProjectCount() throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(UserLoginEDAO.class);
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_COUNT);
        ) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new IllegalStateException("Error while getting project count.");
            }
        } catch (SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, UserLoginEDAO.class);
        }
    }


}
