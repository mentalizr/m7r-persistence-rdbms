package org.mentalizr.persistence.rdbms.barnacle.manual.dao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.EntityNotFoundException;
import org.mentalizr.persistence.rdbms.barnacle.dao.UserAccessKeyDAO;
import org.mentalizr.persistence.rdbms.barnacle.dao.UserDAO;
import org.mentalizr.persistence.rdbms.barnacle.manual.vo.UserAccessKeyCompositeVO;
import org.mentalizr.persistence.rdbms.barnacle.manual.vo.UserLoginCompositeVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserAccessKeyVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UserAccessKeyCompositeDAO {

    private static Logger logger = LoggerFactory.getLogger(UserAccessKeyCompositeDAO.class);

    public static void create(UserAccessKeyCompositeVO userAccessKeyCompositeVO) throws DataSourceException {
        UserDAO.create(userAccessKeyCompositeVO.getUserVO());
        UserAccessKeyDAO.create(userAccessKeyCompositeVO.getUserAccessKeyVO());
    }

    public static UserAccessKeyCompositeVO load(String userId) throws DataSourceException, EntityNotFoundException {
        UserVO userVO = UserDAO.load(userId);
        UserAccessKeyVO userAccessKeyVO = UserAccessKeyDAO.load(userId);
        return new UserAccessKeyCompositeVO(userVO, userAccessKeyVO);
    }

    public static UserAccessKeyCompositeVO findByAccessKey(String accessKey) throws DataSourceException, EntityNotFoundException {
        UserAccessKeyVO userAccessKeyVO = UserAccessKeyDAO.findByUk_accessKey(accessKey);
        UserVO userVO = UserDAO.load(userAccessKeyVO.getUserId());
        return new UserAccessKeyCompositeVO(userVO, userAccessKeyVO);
    }

    public static List<UserAccessKeyCompositeVO> findAll() throws DataSourceException, EntityNotFoundException {
        List<UserAccessKeyCompositeVO> userAccessKeyCompositeVOs = new ArrayList<>();

        List<UserAccessKeyVO> userAccessKeyVOs = UserAccessKeyDAO.findAll();
        for (UserAccessKeyVO userAccessKeyVO : userAccessKeyVOs) {
            UserVO userVO = UserDAO.load(userAccessKeyVO.getUserId());
            UserAccessKeyCompositeVO userAccessKeyCompositeVO = new UserAccessKeyCompositeVO(userVO, userAccessKeyVO);
            userAccessKeyCompositeVOs.add(userAccessKeyCompositeVO);
        }
        return userAccessKeyCompositeVOs;
    }

    public static void delete(String userId) throws DataSourceException {
        UserAccessKeyDAO.delete(userId);
        UserDAO.delete(userId);
    }

}
