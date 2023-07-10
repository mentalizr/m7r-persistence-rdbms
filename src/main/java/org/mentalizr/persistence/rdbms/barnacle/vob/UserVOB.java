package org.mentalizr.persistence.rdbms.barnacle.vob;


import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.PolicyConsentDAO;
import org.mentalizr.persistence.rdbms.barnacle.vo.PolicyConsentPK;
import org.mentalizr.persistence.rdbms.barnacle.vo.PolicyConsentVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserVO;

import java.util.List;

public class UserVOB extends UserVO {

    public UserVOB(String userId) {
        super(userId);
    }

    public boolean isTherapist() throws DataSourceException {
        return this.getRoleTherapistVO().size() > 0;
    }

    public boolean isPatient() throws DataSourceException {
        return this.getRolePatientVO().size() > 0;
    }

    public boolean hasPolicy(String version) throws DataSourceException {
        List<PolicyConsentVO> policyConsentVOList = this.getPolicyConsentVOs();
        return policyConsentVOList.stream()
                .anyMatch(policyConsentVO -> policyConsentVO.getVersion().equals(version));
    }

    public void createPolicyConsentAtEpoch(String version) throws DataSourceException {
        PolicyConsentPK policyPK = new PolicyConsentPK(this.id, version);
        PolicyConsentVO policyConsentVO = new PolicyConsentVO(policyPK);
        policyConsentVO.setConsent(0L);
        PolicyConsentDAO.create(policyConsentVO);
    }

}
