package org.mentalizr.persistence.rdbms.barnacle.connectionManager;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {


    public static Connection openConnection(Class<?> aClass) throws DataSourceException {
        try {

            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/MentalizrUserDb");
            return dataSource.getConnection();

        } catch (NamingException | SQLException e) {
            throw new DataSourceException(e);
        }

    }

    public static void releaseConnection(Connection connection, Class<?> aClass) throws DataSourceException {

        try {
            connection.close();
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }


}
