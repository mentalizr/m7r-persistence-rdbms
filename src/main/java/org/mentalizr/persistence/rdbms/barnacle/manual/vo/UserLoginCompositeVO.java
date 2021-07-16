package org.mentalizr.persistence.rdbms.barnacle.manual.vo;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.RoleAdminDAO;
import org.mentalizr.persistence.rdbms.barnacle.dao.RolePatientDAO;
import org.mentalizr.persistence.rdbms.barnacle.dao.RoleTherapistDAO;
import org.mentalizr.persistence.rdbms.barnacle.vo.*;

import java.util.Date;
import java.util.List;

public class UserLoginCompositeVO {

    private final UserVO userVO;
    private final UserLoginVO userLoginVO;

    public UserLoginCompositeVO(UserVO userVO, UserLoginVO userLoginVO) {
        this.userVO = userVO;
        this.userLoginVO = userLoginVO;
    }

    public UserVO getUserVO() {
        return this.userVO;
    }

    public UserLoginVO getUserLoginVO() {
        return this.userLoginVO;
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

    public String getUsername() {
        return this.userLoginVO.getUsername();
    }

    public String getPasswordHash() {
        return this.userLoginVO.getPasswordHash();
    }

    public String getEmail() {
        return this.userLoginVO.getEmail();
    }

    public String getFirstName() {
        return this.userLoginVO.getFirstName();
    }

    public String getLastName() {
        return this.userLoginVO.getLastName();
    }

    public int getGender() {
        return this.userLoginVO.getGender();
    }

    public boolean isInRolePatient() throws DataSourceException {
        return RolePatientDAO.findByFk_patient_user_id(this.userVO.getUserId()).size() > 0;
    }

    public RolePatientVO getRolePatientVO() throws DataSourceException {
        List<RolePatientVO> rolePatientVOs = RolePatientDAO.findByFk_patient_user_id(this.userVO.getUserId());
        if (rolePatientVOs.size() == 0) throw new IllegalStateException("UserLogin not in role 'patient'.");
        return rolePatientVOs.get(0);
    }

    public boolean isInRoleTherapist() throws DataSourceException {
        return RoleTherapistDAO.findByFk_therapist_user_id(this.userVO.getUserId()).size() > 0;
    }

    public RoleTherapistVO getRoleTherapistVO() throws DataSourceException {
        List<RoleTherapistVO> roleTherapistVOs = RoleTherapistDAO.findByFk_therapist_user_id(this.userVO.getUserId());
        if (roleTherapistVOs.size() == 0) throw new IllegalStateException("UserLogin not in role 'therapist'.");
        return roleTherapistVOs.get(0);
    }

    public boolean isInRoleAdmin() throws DataSourceException {
        return RoleAdminDAO.findByFk_admin_user_id(this.userVO.getUserId()).size() > 0;
    }

    public RoleAdminVO getRoleAdminVO() throws DataSourceException {
        List<RoleAdminVO> roleAdminVOs = RoleAdminDAO.findByFk_admin_user_id(this.userVO.getUserId());
        if (roleAdminVOs.size() == 0) throw new IllegalStateException("UserLogin not in role 'admin'.");
        return roleAdminVOs.get(0);
    }

}
