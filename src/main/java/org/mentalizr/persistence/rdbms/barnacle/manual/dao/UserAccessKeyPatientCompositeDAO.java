package org.mentalizr.persistence.rdbms.barnacle.manual.dao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.EntityNotFoundException;
import org.mentalizr.persistence.rdbms.barnacle.dao.RolePatientDAO;
import org.mentalizr.persistence.rdbms.barnacle.dao.UserAccessKeyDAO;
import org.mentalizr.persistence.rdbms.barnacle.dao.UserDAO;
import org.mentalizr.persistence.rdbms.barnacle.manual.vo.UserAccessKeyCompositeVO;
import org.mentalizr.persistence.rdbms.barnacle.manual.vo.UserAccessKeyPatientCompositeVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.RolePatientVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserAccessKeyVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UserAccessKeyPatientCompositeDAO {

    private static Logger logger = LoggerFactory.getLogger(UserAccessKeyPatientCompositeDAO.class);

    public static void create(UserAccessKeyPatientCompositeVO userAccessKeyPatientCompositeVO) throws DataSourceException {
        UserAccessKeyCompositeDAO.create(userAccessKeyPatientCompositeVO.getUserAccessKeyCompositeVO());
        RolePatientDAO.create(userAccessKeyPatientCompositeVO.getRolePatientVO());
    }

    public static UserAccessKeyPatientCompositeVO load(String userId) throws DataSourceException, EntityNotFoundException {
        UserAccessKeyCompositeVO userAccessKeyCompositeVO = UserAccessKeyCompositeDAO.load(userId);
        RolePatientVO rolePatientVO = RolePatientDAO.load(userId);
        return new UserAccessKeyPatientCompositeVO(userAccessKeyCompositeVO, rolePatientVO);
    }

    public static UserAccessKeyPatientCompositeVO findByAccessKey(String accessKey) throws DataSourceException, EntityNotFoundException {
        UserAccessKeyCompositeVO userAccessKeyCompositeVO = UserAccessKeyCompositeDAO.findByAccessKey(accessKey);
        RolePatientVO rolePatientVO = RolePatientDAO.load(userAccessKeyCompositeVO.getUserId());
        return new UserAccessKeyPatientCompositeVO(userAccessKeyCompositeVO, rolePatientVO);
    }

    public static List<UserAccessKeyPatientCompositeVO> findAll() throws DataSourceException, EntityNotFoundException {
        List<UserAccessKeyPatientCompositeVO> userAccessKeyPatientCompositeVOs = new ArrayList<>();
        List<UserAccessKeyCompositeVO> userAccessKeyCompositeVOs = UserAccessKeyCompositeDAO.findAll();
        for (UserAccessKeyCompositeVO userAccessKeyCompositeVO : userAccessKeyCompositeVOs) {
            RolePatientVO rolePatientVO = RolePatientDAO.load(userAccessKeyCompositeVO.getUserId());
            UserAccessKeyPatientCompositeVO userAccessKeyPatientCompositeVO
                    = new UserAccessKeyPatientCompositeVO(userAccessKeyCompositeVO, rolePatientVO);
            userAccessKeyPatientCompositeVOs.add(userAccessKeyPatientCompositeVO);
        }
        return userAccessKeyPatientCompositeVOs;
    }

    public static void delete(String userId) throws DataSourceException {
        RolePatientDAO.delete(userId);
        UserAccessKeyCompositeDAO.delete(userId);
    }

}
