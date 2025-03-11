package org.mentalizr.persistence.rdbms.edao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.ConnectionManager;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.RolePatientDAO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserAccessKeyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleAccessKeyEDAO {

    private static final Logger logger = LoggerFactory.getLogger(RoleAccessKeyEDAO.class);

    private static final String FIND_ALL_BY_PROJECT_ID_STATEMENT = "SELECT * FROM user_access_key WHERE user_id IN (SELECT role_patient.user_id FROM role_patient WHERE project_id = ?)";
    private static final String FIND_ALL_BY_PROGRAM_STATEMENT = "SELECT user_access_key.user_id FROM user_access_key INNER JOIN patient_program ON user_access_key.user_id = patient_program.user_id WHERE patient_program.program_id = ?;";
    private static final String FIND_ALL_BY_PROGRAM_AND_PROJECT_STATEMENT = "SELECT * FROM user_access_key WHERE user_id IN (SELECT patient_program.user_id FROM patient_program WHERE program_id = ?) AND project_id = ?";

    public static List<UserAccessKeyVO> findAllUserByProject(String projectId) throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(RoleAccessKeyEDAO.class);
        try {
            return findAllUserByProject(projectId, connection);
        } catch(SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, RolePatientDAO.class);
        }
    }

    public static List<UserAccessKeyVO> findAllUserByProject(String projectId, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_PROJECT_ID_STATEMENT);
        preparedStatement.setString(1, projectId);
        logger.debug(FIND_ALL_BY_PROJECT_ID_STATEMENT + "[{}]", projectId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<UserAccessKeyVO> userAccessKeyDAOs = new ArrayList<>();
        while (resultSet.next()) {
            UserAccessKeyVO userAccessKeyVO = new UserAccessKeyVO(resultSet.getObject("user_id", String.class));
            userAccessKeyVO.setAccessKey(resultSet.getObject("accessKey", String.class));
            userAccessKeyDAOs.add(userAccessKeyVO);
        }

        return userAccessKeyDAOs;
    }

    public static List<String> findAllUserByProgram(String programId) throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(RoleAccessKeyEDAO.class);
        try {
            return findAllUserByProgram(programId, connection);
        } catch(SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, RoleAccessKeyEDAO.class);
        }
    }

    public static List<String> findAllUserByProgram(String programId, Connection connection)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_PROGRAM_STATEMENT);
        preparedStatement.setString(1, programId);
        logger.debug(FIND_ALL_BY_PROGRAM_STATEMENT + "[{}]", programId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> userIDs = new ArrayList<>();

        while (resultSet.next()) {
            String userId = resultSet.getObject("user_id", String.class);
            userIDs.add(userId);
        }

        return userIDs;
    }

    public static List<UserAccessKeyVO> findAllUserByProgramAndProject(String programId, String projectId)
            throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(RoleAccessKeyEDAO.class);
        try {
            return findAllUserByProgramAndProject(programId, projectId, connection);
        } catch(SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, RoleAccessKeyEDAO.class);
        }
    }

    public static List<UserAccessKeyVO> findAllUserByProgramAndProject(String programId, String projectId, Connection connection)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_PROGRAM_AND_PROJECT_STATEMENT);
        preparedStatement.setString(1, programId);
        preparedStatement.setString(2, projectId);
        logger.debug(FIND_ALL_BY_PROGRAM_AND_PROJECT_STATEMENT + "[{}]", programId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<UserAccessKeyVO> userAccessKeyDAOs = new ArrayList<>();

        while (resultSet.next()) {
            UserAccessKeyVO userAccessKeyVO = new UserAccessKeyVO(resultSet.getObject("user_id", String.class));
            userAccessKeyVO.setAccessKey(resultSet.getObject("accessKey", String.class));
            userAccessKeyDAOs.add(userAccessKeyVO);
        }

        return userAccessKeyDAOs;
    }
}
