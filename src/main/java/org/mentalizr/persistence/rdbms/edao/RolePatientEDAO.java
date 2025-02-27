package org.mentalizr.persistence.rdbms.edao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.ConnectionManager;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.RolePatientDAO;
import org.mentalizr.persistence.rdbms.barnacle.vo.RolePatientVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection"})
public class RolePatientEDAO {

    private static final Logger logger = LoggerFactory.getLogger(RolePatientDAO.class);

    private static final String PATIENT_PROGRAM_PROJECT_STATEMENT =
            "SELECT role_patient.user_id FROM role_patient, patient_program WHERE project_id = ? AND patient_program.user_id = role_patient.user_id AND patient_program.program_id = ?";

    private static final String PATIENT_PROGRAM_UNASSIGNED_PROJECT_STATEMENT =
            "SELECT role_patient.user_id FROM role_patient, patient_program WHERE project_id IS NULL AND patient_program.user_id = role_patient.user_id AND patient_program.program_id = ?";

    private static final String FIND_ALL_BY_PROJECT_ID_STATEMENT = "SELECT * FROM role_patient WHERE project_id = ?";
    private static final String FIND_ALL_BY_PROGRAM_STATEMENT = "SELECT * FROM role_patient WHERE user_id IN (SELECT patient_program.user_id FROM patient_program WHERE program_id = ?)";
    private static final String FIND_ALL_BY_PROGRAM_AND_PROJECT_STATEMENT = "SELECT * FROM role_patient WHERE user_id IN (SELECT patient_program.user_id FROM patient_program WHERE program_id = ?) AND project_id = ?";

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

    public static List<RolePatientVO> findAllUserByProject(String projectId) throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(RolePatientEDAO.class);
        try {
            return findAllUserByProject(projectId, connection);
        } catch(SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, RolePatientDAO.class);
        }
    }

    public static List<RolePatientVO> findAllUserByProject(String projectId, Connection connection)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_PROJECT_ID_STATEMENT);
        preparedStatement.setObject(1, projectId, Types.VARCHAR);
        logger.debug(FIND_ALL_BY_PROJECT_ID_STATEMENT + " [{}]", projectId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<RolePatientVO> rolePatientVOs = new ArrayList<>();
        while (resultSet.next()) {
            RolePatientVO rolePatientVO = new RolePatientVO(resultSet.getObject("user_id", String.class));
            rolePatientVO.setProjectId(resultSet.getObject("project_id", String.class));
            rolePatientVO.setTherapistId(resultSet.getObject("therapist_id", String.class));
            rolePatientVOs.add(rolePatientVO);
        }

        return rolePatientVOs;
    }

    public static List<RolePatientVO> findAllUserByProgram(String programId) throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(RolePatientEDAO.class);
        try {
            return findAllUserByProgram(programId, connection);
        } catch(SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, RolePatientDAO.class);
        }
    }

    public static List<RolePatientVO> findAllUserByProgram(String programId, Connection connection)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_PROGRAM_STATEMENT);
        preparedStatement.setObject(1, programId, Types.VARCHAR);
        logger.debug(FIND_ALL_BY_PROGRAM_STATEMENT + " [{}]", programId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<RolePatientVO> rolePatientVOs = new ArrayList<>();
        while (resultSet.next()) {
            RolePatientVO rolePatientVO = new RolePatientVO(resultSet.getObject("user_id", String.class));
            rolePatientVO.setProjectId(resultSet.getObject("project_id", String.class));
            rolePatientVO.setTherapistId(resultSet.getObject("therapist_id", String.class));
            rolePatientVOs.add(rolePatientVO);
        }

        return rolePatientVOs;
    }

    public static List<RolePatientVO> findAllUserByProgramAndProject(String programId, String projectId)
            throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(RolePatientEDAO.class);
        try {
            return findAllUserByProgramAndProject(programId, projectId, connection);
        } catch(SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, RolePatientDAO.class);
        }
    }

    public static List<RolePatientVO> findAllUserByProgramAndProject(String programId, String projectId, Connection connection)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_PROGRAM_AND_PROJECT_STATEMENT);
        preparedStatement.setObject(1, programId, Types.VARCHAR);
        preparedStatement.setObject(2, projectId, Types.VARCHAR);
        logger.debug(FIND_ALL_BY_PROGRAM_AND_PROJECT_STATEMENT + " [{}]", programId + ":" + projectId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<RolePatientVO> rolePatientVOs = new ArrayList<>();
        while (resultSet.next()) {
            RolePatientVO rolePatientVO = new RolePatientVO(resultSet.getObject("user_id", String.class));
            rolePatientVO.setProjectId(resultSet.getObject("project_id", String.class));
            rolePatientVO.setTherapistId(resultSet.getObject("therapist_id", String.class));
            rolePatientVOs.add(rolePatientVO);
        }

        return rolePatientVOs;
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
