package org.mentalizr.persistence.rdbms.barnacle.manual.dao;

import org.mentalizr.persistence.rdbms.barnacle.vo.PatientProgramPK;
import org.mentalizr.persistence.rdbms.barnacle.vo.PatientProgramVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.RolePatientVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserVO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOCommons {

    public static UserVO createUserVO(ResultSet resultSet) throws SQLException {
        UserVO userVO = new UserVO(resultSet.getObject("user_id", String.class));
        userVO.setActive(resultSet.getObject("active", Boolean.class));
        userVO.setCreation(resultSet.getObject("creation", Long.class));
        userVO.setFirstActive(resultSet.getObject("firstActive", Long.class));
        userVO.setLastActive(resultSet.getObject("lastActive", Long.class));
        return userVO;
    }

    public static RolePatientVO createRolePatientVO(ResultSet resultSet) throws SQLException {
        RolePatientVO rolePatientVO = new RolePatientVO(resultSet.getObject("user_id", String.class));
        rolePatientVO.setTherapistId(resultSet.getObject("therapist_id", String.class));
        rolePatientVO.setProjectId(resultSet.getObject("project_id", String.class));
        return rolePatientVO;
    }

    public static PatientProgramVO createPatientProgramVO(ResultSet resultSet) throws SQLException {
        PatientProgramPK patientProgramPK
                = new PatientProgramPK(
                resultSet.getObject("user_id", String.class),
                resultSet.getObject("program_id", String.class));
        PatientProgramVO patientProgramVO = new PatientProgramVO(patientProgramPK);
        patientProgramVO.setBlocking(resultSet.getObject("blocking", Boolean.class));
        return patientProgramVO;
    }

}
