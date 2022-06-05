package ru.lanit.exceptions;

public class StashException extends RuntimeException {

    public StashException() {
    }

    public StashException(String message) {
        super(message);
    }

    public StashException(String message, Throwable cause) {
        super(message, cause);
    }

    public StashException(Throwable cause) {
        super(cause);
    }

    public StashException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
