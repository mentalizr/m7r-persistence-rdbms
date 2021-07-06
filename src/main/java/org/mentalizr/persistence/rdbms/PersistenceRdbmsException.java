package org.mentalizr.persistence.rdbms;

public class PersistenceRdbmsException extends Exception {

    public PersistenceRdbmsException() {
    }

    public PersistenceRdbmsException(String message) {
        super(message);
    }

    public PersistenceRdbmsException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistenceRdbmsException(Throwable cause) {
        super(cause);
    }

    public PersistenceRdbmsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
