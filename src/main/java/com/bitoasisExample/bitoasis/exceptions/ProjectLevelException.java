package com.bitoasisExample.bitoasis.exceptions;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ProjectLevelException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchFieldException.class)
    public ResponseEntity<String> noSuchElementExceptionHandler(NoSuchFieldException noSuchFieldException)
    {
        return new ResponseEntity<String>("No such value is found in database.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> emptyResultDataAccessExceptionHandler(EmptyResultDataAccessException
                                                                                    emptyResultDataAccessException)
    {
        return new ResponseEntity<String>("No such value is found in database.", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported
            (HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException,
             HttpHeaders httpHeaders, HttpStatus status, WebRequest webRequest)
    {
        return new ResponseEntity<Object>("Change HTTP method type ",HttpStatus.FAILED_DEPENDENCY);
    }
}
