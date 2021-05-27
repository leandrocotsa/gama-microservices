package com.thesis.gamamicroservices.paymentservice.controller;

import com.thesis.gamamicroservices.paymentservice.service.AlreadyPayedException;
import com.thesis.gamamicroservices.paymentservice.service.NoDataFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {

    //aqui ponho os handler para os tipos de erros comuns a todos os controllers, ex: handle quando um argumento not null é inserido
    //as exceções especificas estao nos controllers especificos

    private static final Logger local_logger = LoggerFactory.getLogger(RestErrorHandler.class);

    public static final String ACCESS_DENIED = "Access denied!";
    public static final String INVALID_REQUEST = "Invalid request";
    public static final String ERROR_MESSAGE_TEMPLATE = "message: %s %n requested uri: %s";
    public static final String LIST_JOIN_DELIMITER = ",";
    public static final String FIELD_ERROR_SEPARATOR = ": ";
    private static final String ERRORS_FOR_PATH = "errors {} for path {}";
    private static final String PATH = "path";
    private static final String ERRORS = "error";
    private static final String STATUS = "status";
    private static final String MESSAGE = "message";
    private static final String TIMESTAMP = "timestamp";
    private static final String TYPE = "type";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        final String path = request.getDescription(false);
        body.put(TIMESTAMP, LocalDate.now());
        body.put(STATUS, status.value());
        body.put(TYPE, ex.getClass().getSimpleName());
        body.put(PATH, path);
        body.put(MESSAGE, getMessageForStatus(status));
        //url ficava bem hmmm

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }




    @ExceptionHandler(NoDataFoundException.class)
    protected ResponseEntity<Object> handleNoDataFoundException(NoDataFoundException ex, WebRequest request) {
        return getExceptionResponseEntity(ex, HttpStatus.NOT_FOUND, request);
    }


    @ExceptionHandler(AlreadyPayedException.class)
    protected ResponseEntity<Object> handleAlreadyPayedException(AlreadyPayedException ex, WebRequest request) {
        return getExceptionResponseEntity(ex, HttpStatus.CONFLICT, request);
    }

    private String getMessageForStatus(HttpStatus status) {
        switch (status) {
            case UNAUTHORIZED:
                return ACCESS_DENIED;
            case BAD_REQUEST:
                return INVALID_REQUEST;
            default:
                return status.getReasonPhrase();
        }
    }

    private ResponseEntity<Object> getExceptionResponseEntity(final Exception exception,
                                                              final HttpStatus status,
                                                              final WebRequest request) {
        final Map<String, Object> body = new LinkedHashMap<>();
        final String path = request.getDescription(false);
        body.put(TIMESTAMP, Instant.now());
        body.put(STATUS, status.value());
        body.put(TYPE, exception.getClass().getSimpleName());
        body.put(PATH, path);
        body.put(MESSAGE, exception.getMessage());

        return new ResponseEntity<>(body, status);
    }

}
