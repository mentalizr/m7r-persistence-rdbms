package org.mentalizr.persistence.rdbms.edao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.ConnectionManager;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.PatientProgramDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientProgramEDAO {

    private static final Logger logger = LoggerFactory.getLogger(PatientProgramEDAO.class);

    private static final String FK_PROGRAM_ID_STATEMENT = "SELECT * FROM patient_program WHERE program_id = ?";

    public static List<String> findUserIdsByFk_program_id(String programId) throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(PatientProgramDAO.class);
        try {
            return findUserIdsByFk_program_id(programId, connection);
        } catch (SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, PatientProgramDAO.class);
        }
    }

    public static List<String> findUserIdsByFk_program_id(String programId, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(FK_PROGRAM_ID_STATEMENT);
        preparedStatement.setObject(1, programId, Types.VARCHAR);
        logger.debug(FK_PROGRAM_ID_STATEMENT + " [{}]", programId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> patientProgramVOList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                String userId = resultSet.getObject("user_id", String.class);
                patientProgramVOList.add(userId);
            }
            return patientProgramVOList;
        } finally {
            if (resultSet != null) { try { resultSet.close(); } catch (SQLException ignored) {}}
            try { preparedStatement.close(); } catch (SQLException ignored) {}
        }
    }

}
