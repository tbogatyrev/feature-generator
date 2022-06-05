package ru.lanit.exceptions;

public class InvalidFunctionUsageException extends RuntimeException {

    public InvalidFunctionUsageException(String message) {
        super(message);
    }
}
