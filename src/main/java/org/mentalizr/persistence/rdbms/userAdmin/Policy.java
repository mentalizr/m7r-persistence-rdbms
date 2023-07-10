package org.mentalizr.persistence.rdbms.userAdmin;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.PolicyConsentDAO;
import org.mentalizr.persistence.rdbms.barnacle.vo.PolicyConsentPK;
import org.mentalizr.persistence.rdbms.barnacle.vo.PolicyConsentVO;

public class Policy {

    public static void create(
            String userId,
            String version,
            Long consent) throws DataSourceException {

        PolicyConsentPK policyConsentPK = new PolicyConsentPK(userId, version);
        PolicyConsentVO policyVO = new PolicyConsentVO(policyConsentPK);
        policyVO.setConsent(consent);

        PolicyConsentDAO.create(policyVO);
    }

}
