
package ru.gavrilov.sergey.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "plohoi zapros")
public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
