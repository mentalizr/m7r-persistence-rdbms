package org.mentalizr.persistence.rdbms.userAdmin;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.manual.dao.UserLoginCompositeDAO;
import org.mentalizr.persistence.rdbms.barnacle.manual.vo.UserLoginCompositeVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserLoginVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserVO;

import java.util.UUID;

public class UserLogin {

    private static final int HASH_ITERATIONS = 20;
    private static final int HASH_MEMORY_KIBIBYTE = 65536;
    private static final int HASH_PARALLELISM = 1;

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
        String passwordHash = getHash(password);

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

    private static String getHash(char[] password) {
        Argon2 argon2 = Argon2Factory.create();
        try {
            return argon2.hash(HASH_ITERATIONS, HASH_MEMORY_KIBIBYTE, HASH_PARALLELISM, password);
        } finally {
            argon2.wipeArray(password);
        }
    }

}
