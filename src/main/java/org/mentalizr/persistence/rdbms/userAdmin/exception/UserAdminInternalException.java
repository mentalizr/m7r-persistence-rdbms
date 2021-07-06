package org.mentalizr.persistence.rdbms.userAdmin.exception;

public class UserAdminInternalException extends UserAdminException {

    public UserAdminInternalException() {
    }

    public UserAdminInternalException(String message) {
        super(message);
    }

    public UserAdminInternalException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAdminInternalException(Throwable cause) {
        super(cause);
    }

    public UserAdminInternalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
