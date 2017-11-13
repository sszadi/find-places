package com.fb.controller;

import com.restfb.exception.FacebookException;
import com.restfb.exception.FacebookGraphException;
import com.restfb.exception.FacebookNetworkException;
import com.restfb.exception.FacebookOAuthException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.logging.Logger;


@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    private Logger logger = Logger.getLogger(ExceptionController.class.getName());
    private String message;


    @ExceptionHandler(value = FacebookOAuthException.class)
    protected ResponseEntity<Object> failOAuth(FacebookOAuthException ex, WebRequest request) {
        message = "Authentication failed. Please check your access token. Error code: " + ex.getHttpStatusCode();
        logger.warning(message);
        return handleExceptionInternal(ex, message,
                new HttpHeaders(), HttpStatus.CONFLICT, request);

    }


    @ExceptionHandler(value = FacebookNetworkException.class)
    protected ResponseEntity<Object> connectError(FacebookNetworkException ex, WebRequest request) {
        message = "Error occurred at network level. HTTP status code: " + ex.getHttpStatusCode();
        logger.warning(message);
        return handleExceptionInternal(ex, message,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = FacebookGraphException.class)
    protected ResponseEntity<Object> graphApiError(FacebookGraphException ex, WebRequest request) {
        message = "Error from Graph API: " + ex.getErrorMessage();
        logger.warning(message);
        return handleExceptionInternal(ex, message,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = FacebookException.class)
    protected ResponseEntity<Object> handleOtherErrors(FacebookException ex, WebRequest request) {
        message = "Oops! Something went wrong";
        logger.warning(message);
        return handleExceptionInternal(ex, message,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
