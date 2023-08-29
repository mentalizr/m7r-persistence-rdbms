package org.mentalizr.persistence.rdbms.barnacle.manual.vo;

import org.mentalizr.persistence.rdbms.barnacle.vo.PatientProgramVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.RolePatientVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserAccessKeyVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserVO;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;

public class UserAccessKeyPatientCompositeVO implements Serializable {

    private static final Long serialVersionUID = 2023070301L;

    private final UserAccessKeyCompositeVO userAccessKeyCompositeVO;
    private final RolePatientVO rolePatientVO;
    private final PatientProgramVO patientProgramVO;

    public UserAccessKeyPatientCompositeVO(
            UserVO userVO,
            UserAccessKeyVO userAccessKeyVO,
            RolePatientVO rolePatientVO,
            PatientProgramVO patientProgramVO
    ) {
        this.userAccessKeyCompositeVO = new UserAccessKeyCompositeVO(userVO, userAccessKeyVO);
        this.rolePatientVO = rolePatientVO;
        this.patientProgramVO = patientProgramVO;
    }

    public UserAccessKeyPatientCompositeVO(
            UserAccessKeyCompositeVO userAccessKeyCompositeVO,
            RolePatientVO rolePatientVO,
            PatientProgramVO patientProgramVO
    ) {
        this.userAccessKeyCompositeVO = userAccessKeyCompositeVO;
        this.rolePatientVO = rolePatientVO;
        this.patientProgramVO = patientProgramVO;
    }

    public String getUserId() {
        return this.userAccessKeyCompositeVO.getUserId();
    }

    public boolean isActive() {
        return this.userAccessKeyCompositeVO.isActive();
    }

    public Long getCreation() {
        return this.userAccessKeyCompositeVO.getCreation();
    }

    public Long getFirstActive() {
        return this.userAccessKeyCompositeVO.getFirstActive();
    }

    public Long getLastActive() {
        return this.userAccessKeyCompositeVO.getLastActive();
    }

//    public ZonedDateTime getFirstActive() {
//        return this.userAccessKeyCompositeVO.getFirstActive();
//    }
//
//    public ZonedDateTime getLastActive() {
//        return this.userAccessKeyCompositeVO.getLastActive();
//    }

    public String getAccessKey() {
        return this.userAccessKeyCompositeVO.getAccessKey();
    }

    public String getProgramId() {
        return this.patientProgramVO.getProgramId();
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

    public PatientProgramVO getPatientProgramVO() {
        return this.patientProgramVO;
    }
}
