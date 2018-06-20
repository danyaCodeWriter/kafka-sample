
package ru.gavrilov.sergey.exception;

public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
            super(message);
    }
}
