package org.mentalizr.persistence.rdbms.barnacle.manual.vo;

import org.mentalizr.persistence.rdbms.barnacle.vo.PatientProgramVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.RolePatientVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserLoginVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserVO;

import java.io.Serializable;

public class UserLoginPatientCompositeVO implements Serializable {

    private final UserLoginCompositeVO userLoginCompositeVO;
    private final RolePatientVO rolePatientVO;
    private final PatientProgramVO patientProgramVO;

    public UserLoginPatientCompositeVO(UserLoginCompositeVO userLoginCompositeVO,
                                       RolePatientVO rolePatientVO,
                                       PatientProgramVO patientProgramVO) {
        this.userLoginCompositeVO = userLoginCompositeVO;
        this.rolePatientVO = rolePatientVO;
        this.patientProgramVO = patientProgramVO;
    }

    public UserLoginPatientCompositeVO(UserVO userVO,
                                       UserLoginVO userLoginVO,
                                       PatientProgramVO patientProgramVO,
                                       RolePatientVO rolePatientVO) {
        this.userLoginCompositeVO = new UserLoginCompositeVO(userVO, userLoginVO);
        this.patientProgramVO = patientProgramVO;
        this.rolePatientVO = rolePatientVO;
    }

    public String getUserId() {
        return userLoginCompositeVO.getUserId();
    }

    public String getUsername() {
        return userLoginCompositeVO.getUserLoginVO().getUsername();
    }

    public Long getCreation() {
        return userLoginCompositeVO.getUserVO().getCreation();
    }

    public boolean getActive() {
        return userLoginCompositeVO.getUserVO().getActive();
    }

    public Long getFirstActive() {
        return this.userLoginCompositeVO.getUserVO().getFirstActive();
    }

    public Long getLastActive() {
        return this.userLoginCompositeVO.getUserVO().getLastActive();
    }

    public String getPasswordHash() {
        return this.userLoginCompositeVO.getUserLoginVO().getPasswordHash();
    }

    public String getEmail() {
        return this.userLoginCompositeVO.getUserLoginVO().getEmail();
    }

    public String getFirstName() {
        return this.userLoginCompositeVO.getUserLoginVO().getFirstName();
    }

    public String getLastName() {
        return this.userLoginCompositeVO.getUserLoginVO().getLastName();
    }

    public int getGender() {
        return this.userLoginCompositeVO.getUserLoginVO().getGender();
    }

    public boolean getSecondFA() {
        return this.userLoginCompositeVO.getUserLoginVO().getSecondFA();
    }

    public long getEmailConfirmation() {
        return this.getUserLoginCompositeVO().getUserLoginVO().getEmailConfirmation();
    }

    public String getEmailConfToken() {
        return this.getUserLoginCompositeVO().getUserLoginVO().getEmailConfToken();
    }

    public String getEmailConfCode() {
        return this.userLoginCompositeVO.getUserLoginVO().getEmailConfCode();
    }

    public boolean getRenewPasswordRequired() {
        return this.userLoginCompositeVO.getUserLoginVO().getRenewPasswordRequired();
    }

    public String getProgramId() {
        return this.patientProgramVO.getProgramId();
    }

    public boolean getBlocking() {
        return this.patientProgramVO.getBlocking();
    }

    public String getTherapistId() {
        return this.rolePatientVO.getTherapistId();
    }

    public String getProjectId() {
        return this.rolePatientVO.getProjectId();
    }

    public UserLoginCompositeVO getUserLoginCompositeVO() {
        return this.userLoginCompositeVO;
    }

    public RolePatientVO getRolePatientVO() {
        return this.rolePatientVO;
    }

    public PatientProgramVO getPatientProgramVO() {
        return this.patientProgramVO;
    }


}
