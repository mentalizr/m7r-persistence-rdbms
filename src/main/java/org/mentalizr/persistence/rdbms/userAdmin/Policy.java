package org.mentalizr.persistence.rdbms.userAdmin;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.PolicyDAO;
import org.mentalizr.persistence.rdbms.barnacle.dao.ProgramDAO;
import org.mentalizr.persistence.rdbms.barnacle.vo.PolicyPK;
import org.mentalizr.persistence.rdbms.barnacle.vo.PolicyVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.ProgramVO;

import static de.arthurpicht.utils.core.assertion.MethodPreconditions.assertArgumentNotNullAndNotEmpty;

public class Policy {

    public static void create(
            String userId,
            String version,
            Long consent) throws DataSourceException {

        PolicyPK policyPK = new PolicyPK(userId, version);
        PolicyVO policyVO = new PolicyVO(policyPK);
        policyVO.setConsent(consent);

        PolicyDAO.create(policyVO);
    }

}
