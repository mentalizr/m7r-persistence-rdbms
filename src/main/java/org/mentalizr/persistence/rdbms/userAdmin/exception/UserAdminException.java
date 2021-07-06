package org.mentalizr.persistence.rdbms.userAdmin.exception;

public class UserAdminException extends Exception {

    public UserAdminException() {
    }

    public UserAdminException(String message) {
        super(message);
    }

    public UserAdminException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAdminException(Throwable cause) {
        super(cause);
    }

    public UserAdminException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
