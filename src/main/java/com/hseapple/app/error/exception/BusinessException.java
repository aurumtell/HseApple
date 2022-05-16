package com.hseapple.app.error.exception;

public class BusinessException extends AppException {
    //extend with response type
    public BusinessException(String message) {
        super(message);
    }
}
