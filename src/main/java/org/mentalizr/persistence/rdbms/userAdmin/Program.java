package org.mentalizr.persistence.rdbms.userAdmin;

import de.arthurpicht.utils.core.assertion.AssertMethodPrecondition;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.ProgramDAO;
import org.mentalizr.persistence.rdbms.barnacle.vo.ProgramVO;
import org.mentalizr.persistence.rdbms.userAdmin.exception.UserAdminInternalException;

public class Program {

    public static void add(String programId) throws DataSourceException {

        AssertMethodPrecondition.parameterNotNullAndNotEmpty("id", programId);

        ProgramVO programVO = new ProgramVO(programId);
        ProgramDAO.create(programVO);
    }

}
