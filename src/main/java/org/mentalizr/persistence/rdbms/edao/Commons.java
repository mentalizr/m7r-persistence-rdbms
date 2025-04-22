package org.mentalizr.persistence.rdbms.edao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Commons {

    private static final Logger logger = LoggerFactory.getLogger(Commons.class);

    public static List<String> findAllIds(Connection connection, String sqlStatement, String idColumnName) throws SQLException {
        Statement statement = connection.createStatement();
        logger.debug(sqlStatement);
        //noinspection SqlSourceToSinkFlow
        ResultSet resultSet = statement.executeQuery(sqlStatement);
        List<String> idList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                String id = resultSet.getObject(idColumnName, String.class);
                idList.add(id);
            }
            return idList;
        } finally {
            if (resultSet != null) { try { resultSet.close(); } catch (SQLException ignored) {}}
            try { statement.close(); } catch (SQLException ignored) {}
        }
    }

}
