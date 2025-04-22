package org.mentalizr.persistence.rdbms.edao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.ConnectionManager;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.RolePatientDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection"})
public class RolePatientEDAO {

    private static final Logger logger = LoggerFactory.getLogger(RolePatientEDAO.class);

    private static final String FIND_ALL_ID_STATEMENT = "SELECT user_id FROM role_patient";

    private static final String PATIENT_PROGRAM_PROJECT_STATEMENT =
            "SELECT role_patient.user_id FROM role_patient, patient_program WHERE project_id = ? AND patient_program.user_id = role_patient.user_id AND patient_program.program_id = ?";

    private static final String PATIENT_PROGRAM_UNASSIGNED_PROJECT_STATEMENT =
            "SELECT role_patient.user_id FROM role_patient, patient_program WHERE project_id IS NULL AND patient_program.user_id = role_patient.user_id AND patient_program.program_id = ?";

    public static List<String> findAllIds() throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(RolePatientEDAO.class);
        try {
            return Commons.findAllIds(connection, FIND_ALL_ID_STATEMENT, "user_id");
        } catch (SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, RolePatientEDAO.class);
        }
    }

    public static List<String> findAllUserIdsForProgramAndProject(String programId, String projectId) throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(RolePatientEDAO.class);
        try {
            return findAllUserIdsForProgramAndProject(programId, projectId, connection);
        } catch(SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, RolePatientDAO.class);
        }
    }

    public static List<String> findAllUserIdsForProgramAndProject(String programId, String projectId, Connection connection)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(PATIENT_PROGRAM_PROJECT_STATEMENT);
        preparedStatement.setObject(1, projectId, Types.VARCHAR);
        preparedStatement.setObject(2, programId, Types.VARCHAR);
        logger.debug(PATIENT_PROGRAM_PROJECT_STATEMENT + " [{}] [{}]", projectId, programId);
        return processResultSet(preparedStatement);
    }

    public static List<String> findAllUserIdsForProgramAndUnassignedProject(String programId)
            throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(RolePatientEDAO.class);
        try {
            return findAllUserIdsForProgramAndUnassignedProject(programId, connection);
        } catch(SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, RolePatientDAO.class);
        }
    }

    public static List<String> findAllUserIdsForProgramAndUnassignedProject(String programId, Connection connection)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(PATIENT_PROGRAM_UNASSIGNED_PROJECT_STATEMENT);
        preparedStatement.setObject(1, programId, Types.VARCHAR);
        logger.debug(PATIENT_PROGRAM_PROJECT_STATEMENT + " [{}]", programId);
        return processResultSet(preparedStatement);
    }

    private static List<String> processResultSet(PreparedStatement preparedStatement) throws SQLException {
        try (preparedStatement; ResultSet resultSet = preparedStatement.executeQuery()) {
            List<String> userIds = new ArrayList<>();

            while (resultSet.next()) {
                String userId = resultSet.getObject("user_id", String.class);
                userIds.add(userId);
            }
            return userIds;
        }
    }

}
