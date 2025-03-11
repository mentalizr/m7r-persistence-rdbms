package org.mentalizr.persistence.rdbms.barnacle.manual.dao;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.EntityNotFoundException;
import org.mentalizr.persistence.rdbms.barnacle.dao.PatientProgramDAO;
import org.mentalizr.persistence.rdbms.barnacle.dao.RolePatientDAO;
import org.mentalizr.persistence.rdbms.barnacle.dao.UserDAO;
import org.mentalizr.persistence.rdbms.barnacle.dao.UserLoginDAO;
import org.mentalizr.persistence.rdbms.barnacle.manual.vo.UserLoginCompositeVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.RolePatientVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserLoginVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserVO;
import org.mentalizr.persistence.rdbms.edao.PatientProgramEDAO;
import org.mentalizr.persistence.rdbms.edao.RolePatientEDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

public class UserLoginCompositeDAO {

    private static Logger logger = LoggerFactory.getLogger(UserLoginCompositeDAO.class);

    public static void create(UserLoginCompositeVO userLoginCompositeVO) throws DataSourceException {
        UserDAO.create(userLoginCompositeVO.getUserVO());
        UserLoginDAO.create(userLoginCompositeVO.getUserLoginVO());
    }

    public static UserLoginCompositeVO load(String userId) throws DataSourceException, EntityNotFoundException {
        UserVO userVO = UserDAO.load(userId);
        UserLoginVO userLoginVO = UserLoginDAO.load(userId);
        return new UserLoginCompositeVO(userVO, userLoginVO);
    }

    public static UserLoginCompositeVO findByUk_username(String username) throws DataSourceException, EntityNotFoundException {
        UserLoginVO userLoginVO = UserLoginDAO.findByUk_username(username);
        UserVO userVO = UserDAO.load(userLoginVO.getUserId());
        return new UserLoginCompositeVO(userVO, userLoginVO);
    }

    public static List<UserLoginCompositeVO> findAll() throws DataSourceException, EntityNotFoundException {
        List<UserLoginCompositeVO> userLoginCompositeVOs = new ArrayList<>();

        List<UserLoginVO> userLoginVOs = UserLoginDAO.findAll();
        for (UserLoginVO userLoginVO : userLoginVOs) {
            UserVO userVO = UserDAO.load(userLoginVO.getUserId());
            UserLoginCompositeVO userLoginCompositeVO = new UserLoginCompositeVO(userVO, userLoginVO);
            userLoginCompositeVOs.add(userLoginCompositeVO);
        }
        return userLoginCompositeVOs;
    }

    public static List<UserLoginCompositeVO> findAllPatients() throws DataSourceException {
        List<UserLoginCompositeVO> userLoginCompositeVOs = new ArrayList<>();
        List<RolePatientVO> rolePatientVOs = RolePatientDAO.findAll();

        for (RolePatientVO rolePatientVO : rolePatientVOs) {

            try {
                UserLoginVO userLoginVO = UserLoginDAO.load(rolePatientVO.getUserId());
                UserVO userVO = UserDAO.load(rolePatientVO.getUserId());

                UserLoginCompositeVO userLoginCompositeVO = new UserLoginCompositeVO(userVO, userLoginVO);
                userLoginCompositeVOs.add(userLoginCompositeVO);

            } catch (EntityNotFoundException e) {
                // do intentionally nothing
            }

        }

        return userLoginCompositeVOs;
    }

    public static List<UserLoginCompositeVO> findAllPatientsByProjectId(String projectId) throws DataSourceException {
        List<UserLoginCompositeVO> userLoginCompositeVOs = new ArrayList<>();
        List<String> userIDs = RolePatientEDAO.findAllUserIDsByProject(projectId);

        for (String userid : userIDs) {

            try {
                UserLoginVO userLoginVO = UserLoginDAO.load(userid);
                UserVO userVO = UserDAO.load(userid);

                UserLoginCompositeVO userLoginCompositeVO = new UserLoginCompositeVO(userVO, userLoginVO);
                userLoginCompositeVOs.add(userLoginCompositeVO);

            } catch (EntityNotFoundException e) {
                // do intentionally nothing
            }

        }

        return userLoginCompositeVOs;
    }

    public static List<UserLoginCompositeVO> findAllPatientsByProgramId(String programId) throws DataSourceException {
        List<UserLoginCompositeVO> userLoginCompositeVOs = new ArrayList<>();
        List<String> userIDs = PatientProgramEDAO.findUserIdsByFk_program_id(programId);

        for (String userId : userIDs) {

            try {
                UserLoginVO userLoginVO = UserLoginDAO.load(userId);
                UserVO userVO = UserDAO.load(userId);

                UserLoginCompositeVO userLoginCompositeVO = new UserLoginCompositeVO(userVO, userLoginVO);
                userLoginCompositeVOs.add(userLoginCompositeVO);

            } catch (EntityNotFoundException e) {
                // do intentionally nothing
            }

        }

        return userLoginCompositeVOs;
    }

    public static List<UserLoginCompositeVO> findAllPatientsByProgramIdAndProjectId(String programId, String projectId) throws DataSourceException {
        List<UserLoginCompositeVO> userLoginCompositeVOs = new ArrayList<>();
        List<String> userIDs = RolePatientEDAO.findAllUserByProgramAndProject(projectId, programId);

        for (String userId : userIDs) {

            try {
                UserLoginVO userLoginVO = UserLoginDAO.load(userId);
                UserVO userVO = UserDAO.load(userId);

                UserLoginCompositeVO userLoginCompositeVO = new UserLoginCompositeVO(userVO, userLoginVO);
                userLoginCompositeVOs.add(userLoginCompositeVO);

            } catch (EntityNotFoundException e) {
                // do intentionally nothing
            }

        }

        return userLoginCompositeVOs;
    }
}
