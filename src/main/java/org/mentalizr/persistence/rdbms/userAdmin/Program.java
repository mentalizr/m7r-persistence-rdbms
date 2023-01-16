package org.mentalizr.persistence.rdbms.userAdmin;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.ProgramDAO;
import org.mentalizr.persistence.rdbms.barnacle.vo.ProgramVO;

import static de.arthurpicht.utils.core.assertion.MethodPreconditions.assertArgumentNotNullAndNotEmpty;

public class Program {

    public static void add(String programId) throws DataSourceException {
        assertArgumentNotNullAndNotEmpty("id", programId);

        ProgramVO programVO = new ProgramVO(programId);
        ProgramDAO.create(programVO);
    }

}
