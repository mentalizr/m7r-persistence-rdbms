package org.mentalizr.persistence.rdbms.barnacle.manual.dao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.EntityNotFoundException;
import org.mentalizr.persistence.rdbms.barnacle.dao.PatientProgramDAO;
import org.mentalizr.persistence.rdbms.barnacle.dao.RolePatientDAO;
import org.mentalizr.persistence.rdbms.barnacle.dao.UserAccessKeyDAO;
import org.mentalizr.persistence.rdbms.barnacle.dao.UserDAO;
import org.mentalizr.persistence.rdbms.barnacle.manual.vo.UserAccessKeyCompositeVO;
import org.mentalizr.persistence.rdbms.barnacle.manual.vo.UserAccessKeyPatientCompositeVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UserAccessKeyPatientCompositeDAO {

    private static Logger logger = LoggerFactory.getLogger(UserAccessKeyPatientCompositeDAO.class);

    public static void create(UserAccessKeyPatientCompositeVO userAccessKeyPatientCompositeVO) throws DataSourceException {
        UserAccessKeyCompositeDAO.create(userAccessKeyPatientCompositeVO.getUserAccessKeyCompositeVO());
        RolePatientDAO.create(userAccessKeyPatientCompositeVO.getRolePatientVO());
        PatientProgramDAO.create(userAccessKeyPatientCompositeVO.getPatientProgramVO());
    }

    public static UserAccessKeyPatientCompositeVO load(String userId) throws DataSourceException, EntityNotFoundException {
        UserAccessKeyCompositeVO userAccessKeyCompositeVO = UserAccessKeyCompositeDAO.load(userId);
        RolePatientVO rolePatientVO = RolePatientDAO.load(userId);
        PatientProgramVO patientProgramVO = PatientProgramDAO.findByUk_user_id(userId);
        return new UserAccessKeyPatientCompositeVO(userAccessKeyCompositeVO, rolePatientVO, patientProgramVO);
    }

    public static UserAccessKeyPatientCompositeVO findByAccessKey(String accessKey) throws DataSourceException, EntityNotFoundException {
        UserAccessKeyCompositeVO userAccessKeyCompositeVO = UserAccessKeyCompositeDAO.findByAccessKey(accessKey);
        String userId = userAccessKeyCompositeVO.getUserId();
        RolePatientVO rolePatientVO = RolePatientDAO.load(userId);
        PatientProgramVO patientProgramVO = PatientProgramDAO.findByUk_user_id(userId);
        return new UserAccessKeyPatientCompositeVO(userAccessKeyCompositeVO, rolePatientVO, patientProgramVO);
    }

    public static List<UserAccessKeyPatientCompositeVO> findAll() throws DataSourceException, EntityNotFoundException {
        List<UserAccessKeyPatientCompositeVO> userAccessKeyPatientCompositeVOs = new ArrayList<>();
        List<UserAccessKeyCompositeVO> userAccessKeyCompositeVOs = UserAccessKeyCompositeDAO.findAll();
        for (UserAccessKeyCompositeVO userAccessKeyCompositeVO : userAccessKeyCompositeVOs) {
            String userId = userAccessKeyCompositeVO.getUserId();
            RolePatientVO rolePatientVO = RolePatientDAO.load(userId);
            PatientProgramVO patientProgramVO = PatientProgramDAO.findByUk_user_id(userId);
            UserAccessKeyPatientCompositeVO userAccessKeyPatientCompositeVO
                    = new UserAccessKeyPatientCompositeVO(userAccessKeyCompositeVO, rolePatientVO, patientProgramVO);
            userAccessKeyPatientCompositeVOs.add(userAccessKeyPatientCompositeVO);
        }
        return userAccessKeyPatientCompositeVOs;
    }

    public static void delete(String userId) throws DataSourceException {
        RolePatientDAO.delete(userId);
        UserAccessKeyCompositeDAO.delete(userId);
    }

}
