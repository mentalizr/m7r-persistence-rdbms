package org.mentalizr.persistence.rdbms.userAdmin;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.ProjectDAO;
import org.mentalizr.persistence.rdbms.barnacle.vo.ProjectVO;

import static de.arthurpicht.utils.core.assertion.MethodPreconditions.assertArgumentNotNullAndNotEmpty;

public class Project {

    public static void add(String projectId, String label) throws DataSourceException {
        assertArgumentNotNullAndNotEmpty("projectId", projectId);

        ProjectVO projectVO = new ProjectVO(projectId);
        projectVO.setLabel(label);
        ProjectDAO.create(projectVO);
    }

}
