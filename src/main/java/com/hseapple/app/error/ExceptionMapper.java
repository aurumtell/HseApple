package com.hseapple.app.error;

import com.hseapple.app.error.exception.AuthorizationException;
import com.hseapple.app.error.exception.BusinessException;
import com.hseapple.app.error.exception.TechnicalException;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionMapper {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(ExceptionMapper.class);

    @ExceptionHandler(value = BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException e) {
        log.error(e.getMessage(), e);
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = TechnicalException.class)
    protected ResponseEntity<com.hseapple.app.error.ErrorResponse> handleTechnicalException(
            TechnicalException e) {
        log.error("Internal technical error", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new InternalErrorResponse());
    }

    @ExceptionHandler(value = AuthorizationException.class)
    protected ResponseEntity<ErrorResponse> handleAuthorizationException(
            AuthorizationException e) {
        log.error(e.getMessage(), e);
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }



}
