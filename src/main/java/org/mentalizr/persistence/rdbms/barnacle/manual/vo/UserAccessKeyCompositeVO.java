package org.mentalizr.persistence.rdbms.barnacle.manual.vo;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.RolePatientDAO;
import org.mentalizr.persistence.rdbms.barnacle.vo.RolePatientVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserAccessKeyVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserLoginVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserVO;

import java.util.Date;
import java.util.List;

public class UserAccessKeyCompositeVO {

    private UserVO userVO;
    private UserAccessKeyVO userAccessKeyVO;

    public UserAccessKeyCompositeVO(UserVO userVO, UserAccessKeyVO userAccessKeyVO) {
        this.userVO = userVO;
        this.userAccessKeyVO = userAccessKeyVO;
    }

    public UserVO getUserVO() {
        return this.userVO;
    }

    public UserAccessKeyVO getUserAccessKeyVO() {
        return this.userAccessKeyVO;
    }

    public String getUserId() {
        return this.userVO.getUserId();
    }

    public boolean isActive() {
        return this.userVO.getActive();
    }

    public Date getFirstActive() {
        return this.userVO.getFirstActive();
    }

    public Date getLastActive() {
        return this.userVO.getLastActive();
    }

    public String getAccessKey() {
        return this.userAccessKeyVO.getAccessKey();
    }

    public RolePatientVO getRolePatientVO() throws DataSourceException {
        List<RolePatientVO> rolePatientVOs = RolePatientDAO.findByFk_patient_user_id(this.userVO.getUserId());
        if (rolePatientVOs.size() == 0) throw new IllegalStateException("UserLogin not in role 'patient'.");
        return rolePatientVOs.get(0);
    }


}
