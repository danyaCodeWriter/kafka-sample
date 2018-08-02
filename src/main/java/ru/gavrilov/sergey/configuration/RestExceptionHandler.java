package ru.gavrilov.sergey.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.gavrilov.sergey.exception.RestExceptionDto;
import ru.gavrilov.sergey.exception.ServiceException;

/**
 * @author Danil_Slesarenko
 */
@ControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<RestExceptionDto> handleValidationException(ServiceException ex) {
        return new ResponseEntity<>(
                new RestExceptionDto(
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * Base exception handler.
     *
     * @return ErrorInfo
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestExceptionDto> handleException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(
                new RestExceptionDto(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
