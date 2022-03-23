package org.mentalizr.persistence.rdbms.userAdmin;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.manual.dao.UserLoginCompositeDAO;
import org.mentalizr.persistence.rdbms.barnacle.manual.vo.UserLoginCompositeVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserLoginVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserVO;
import org.mentalizr.persistence.rdbms.utils.Argon2Hash;

import java.util.UUID;

public class UserLogin {

    public static UserLoginCompositeVO add(
            boolean active,
            String username,
            char[] password,
            String email,
            String firstname,
            String lastname,
            int gender
    ) throws DataSourceException {

        String userUUID = UUID.randomUUID().toString();
        String passwordHash = Argon2Hash.getHash(password);

        return restore(
                userUUID,
                active,
                username,
                passwordHash,
                email,
                firstname,
                lastname,
                gender
        );
    }

    public static UserLoginCompositeVO restore(
            String userUUID,
            boolean active,
            String username,
            String passwordHash,
            String email,
            String firstname,
            String lastname,
            int gender
    ) throws DataSourceException {

        UserVO userVO = new UserVO(userUUID);
        userVO.setActive(active);

        UserLoginVO userLoginVO = new UserLoginVO(userUUID);
        userLoginVO.setUsername(username);
        userLoginVO.setPasswordHash(passwordHash);
        userLoginVO.setEmail(email);
        userLoginVO.setFirstName(firstname);
        userLoginVO.setLastName(lastname);
        userLoginVO.setGender(gender);

        UserLoginCompositeVO userLoginCompositeVO = new UserLoginCompositeVO(userVO, userLoginVO);

        UserLoginCompositeDAO.create(userLoginCompositeVO);

        return userLoginCompositeVO;
    }

}
