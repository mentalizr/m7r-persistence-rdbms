package org.mentalizr.persistence.rdbms.barnacle.manual.vo;

import org.mentalizr.persistence.rdbms.barnacle.vo.RolePatientVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserAccessKeyVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserVO;

import java.util.Date;

public class UserAccessKeyPatientCompositeVO {

    private final UserAccessKeyCompositeVO userAccessKeyCompositeVO;
    private final RolePatientVO rolePatientVO;

    public UserAccessKeyPatientCompositeVO(UserVO userVO, UserAccessKeyVO userAccessKeyVO, RolePatientVO rolePatientVO) {
        this.userAccessKeyCompositeVO = new UserAccessKeyCompositeVO(userVO, userAccessKeyVO);
        this.rolePatientVO = rolePatientVO;
    }

    public UserAccessKeyPatientCompositeVO(UserAccessKeyCompositeVO userAccessKeyCompositeVO, RolePatientVO rolePatientVO) {
        this.userAccessKeyCompositeVO = userAccessKeyCompositeVO;
        this.rolePatientVO = rolePatientVO;
    }

    public String getUserId() {
        return this.userAccessKeyCompositeVO.getUserId();
    }

    public boolean isActive() {
        return this.userAccessKeyCompositeVO.isActive();
    }

    public Date getFirstActive() {
        return this.userAccessKeyCompositeVO.getFirstActive();
    }

    public Date getLastActive() {
        return this.userAccessKeyCompositeVO.getLastActive();
    }

    public String getAccessKey() {
        return this.userAccessKeyCompositeVO.getAccessKey();
    }

    public String getProgramId() {
        return this.rolePatientVO.getProgramId();
    }

    public String getTherapistId() {
        return this.rolePatientVO.getTherapistId();
    }

    public UserAccessKeyCompositeVO getUserAccessKeyCompositeVO() {
        return userAccessKeyCompositeVO;
    }

    public RolePatientVO getRolePatientVO() {
        return rolePatientVO;
    }
}
