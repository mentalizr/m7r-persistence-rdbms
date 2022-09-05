package org.mentalizr.persistence.rdbms.barnacle.manual.vo;

import org.mentalizr.commons.EpochMillis;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.RolePatientDAO;
import org.mentalizr.persistence.rdbms.barnacle.vo.RolePatientVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserAccessKeyVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserVO;

import java.time.ZonedDateTime;
import java.util.List;

public class UserAccessKeyCompositeVO {

    private final UserVO userVO;
    private final UserAccessKeyVO userAccessKeyVO;

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
        return this.userVO.getId();
    }

    public boolean isActive() {
        return this.userVO.getActive();
    }

    public ZonedDateTime getFirstActive() {
        if (this.userVO.getFirstActive() == null) return null;
        return EpochMillis.asZonedDateTimeBerlin(this.userVO.getFirstActive());
    }

    public ZonedDateTime getLastActive() {
        if (this.userVO.getLastActive() == null) return null;
        return EpochMillis.asZonedDateTimeBerlin(this.userVO.getLastActive());
    }

    public String getAccessKey() {
        return this.userAccessKeyVO.getAccessKey();
    }

    public RolePatientVO getRolePatientVO() throws DataSourceException {
        List<RolePatientVO> rolePatientVOs = RolePatientDAO.findByFk_patient_user_id(this.userVO.getId());
        if (rolePatientVOs.size() == 0) throw new IllegalStateException("UserLogin not in role 'patient'.");
        return rolePatientVOs.get(0);
    }

}
