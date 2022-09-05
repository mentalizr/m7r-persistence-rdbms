package org.mentalizr.persistence.rdbms.userAdmin;

import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.manual.dao.UserLoginCompositeDAO;
import org.mentalizr.persistence.rdbms.barnacle.manual.vo.UserLoginCompositeVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserLoginVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserVO;
import org.mentalizr.persistence.rdbms.utils.Argon2Hash;
import org.mentalizr.persistence.rdbms.utils.PasswordGenerator;

import java.util.UUID;

public class UserLogin {

    public static UserLoginCompositeVO add(
            boolean active,
            String username,
            char[] password,
            String email,
            String firstname,
            String lastname,
            int gender,
            boolean secondFA,
            boolean requirePolicyConsent,
            boolean requireEmailConfirmation,
            boolean renewPasswordRequired
    ) throws DataSourceException {

        String userUUID = UUID.randomUUID().toString();
        String passwordHash = Argon2Hash.getHash(password);

        Long policyConsent = requirePolicyConsent ? null : 0L;

        Long emailConfirmation;
        String emailConfirmationToken;
        String emailConfirmationCode;
        if (requireEmailConfirmation) {
            emailConfirmation = null;
            emailConfirmationToken =
                    new PasswordGenerator(12, PasswordGenerator.LOW_LETTERS, PasswordGenerator.FIGURES)
                            .getPassword();
            emailConfirmationCode =
                    new PasswordGenerator(6, PasswordGenerator.FIGURES).getPassword();
        } else {
            emailConfirmation = 0L;
            emailConfirmationToken = null;
            emailConfirmationCode = null;
        }

        return restore(
                userUUID,
                active,
                null,
                null,
                policyConsent,
                username,
                passwordHash,
                email,
                firstname,
                lastname,
                gender,
                secondFA,
                emailConfirmation,
                emailConfirmationToken,
                emailConfirmationCode,
                renewPasswordRequired
        );
    }

    public static UserLoginCompositeVO restore(
            String userUUID,
            boolean active,
            Long firstActive,
            Long lastActive,
            Long policyConsent,
            String username,
            String passwordHash,
            String email,
            String firstname,
            String lastname,
            int gender,
            boolean secondFA,
            Long emailConfirmation,
            String emailConfirmationToken,
            String emailConfirmationCode,
            boolean renewPasswordRequired
    ) throws DataSourceException {

        UserVO userVO = new UserVO(userUUID);
        userVO.setActive(active);
        userVO.setFirstActive(firstActive);
        userVO.setLastActive(lastActive);
        userVO.setPolicyConsent(policyConsent);

        UserLoginVO userLoginVO = new UserLoginVO(userUUID);
        userLoginVO.setUsername(username);
        userLoginVO.setPasswordHash(passwordHash);
        userLoginVO.setEmail(email);
        userLoginVO.setFirstName(firstname);
        userLoginVO.setLastName(lastname);
        userLoginVO.setGender(gender);
        userLoginVO.setSecondFA(secondFA);
        userLoginVO.setEmailConfirmation(emailConfirmation);
        userLoginVO.setEmailConfToken(emailConfirmationToken);
        userLoginVO.setEmailConfCode(emailConfirmationCode);
        userLoginVO.setRenewPasswordRequired(renewPasswordRequired);

        UserLoginCompositeVO userLoginCompositeVO = new UserLoginCompositeVO(userVO, userLoginVO);

        UserLoginCompositeDAO.create(userLoginCompositeVO);

        return userLoginCompositeVO;
    }

}
