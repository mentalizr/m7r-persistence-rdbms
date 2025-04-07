package org.mentalizr.persistence.rdbms.edao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.ConnectionManager;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserAccessKeyVO;
import org.mentalizr.serviceObjects.requestObjects.UserListQuerySO;
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

    private static final String FIND_ALL_BY_PROGRAM_BY = "SELECT user.active, creation, firstActive, lastActive, user_access_key.user_id, accessKey" +
            "FROM user_access_key" +
            " INNER JOIN user ON user_access_key.user_id = user.id" +
            " INNER JOIN patient_program ON user_access_key.user_id = patient_program.user_id" +
            " INNER JOIN role_patient ON user_access_key.user_id = role_patient.user_id" +
            " WHERE (? = 1 AND role_patient.project_id = ?)" +
            " OR (? = 1 AND patient_program.program_id = ?)" +
            " OR (? = 1 AND ? = 1 AND patient_program.program_id = ? AND role_patient.project_id = ?)";

    public static List<UserAccessKeyVO> findAllBy(UserListQuerySO userListQuerySO)
            throws DataSourceException {
        Connection connection = ConnectionManager.openConnection(RoleAccessKeyEDAO.class);
        try {
            return findAllBy(userListQuerySO, connection);
        } catch(SQLException e) {
            throw new DataSourceException(e);
        } finally {
            ConnectionManager.releaseConnection(connection, RoleAccessKeyEDAO.class);
        }
    }

    public static List<UserAccessKeyVO> findAllBy(UserListQuerySO userListQuerySO, Connection connection)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_PROGRAM_BY);
        preparedStatement.setInt(1, userListQuerySO.isProject() ? 1 : 0);
        preparedStatement.setString(2, userListQuerySO.getProjectName());
        preparedStatement.setInt(3, userListQuerySO.isProgram() ? 1 : 0);
        preparedStatement.setString(4, userListQuerySO.getProgramName());
        preparedStatement.setInt(5, userListQuerySO.isProject() ? 1 : 0);
        preparedStatement.setInt(6, userListQuerySO.isProgram() ? 1 : 0);
        preparedStatement.setString(7, userListQuerySO.getProgramName());
        preparedStatement.setString(8, userListQuerySO.getProjectName());

        logger.debug(FIND_ALL_BY_PROGRAM_BY + " [{}]", userListQuerySO.getProgramName() + ":"
                + userListQuerySO.getProjectName());
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
