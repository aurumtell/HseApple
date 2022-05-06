package com.hseapple.app.error.exception;

public class BusinessException extends AppException {
    //extend with response type
    public BusinessException(String message) {
        super(message);
    }

//    public BusinessException(LocalizedMessage localizedMessage, ErrorCode errorCode) {
//        super(Translator.localize(localizedMessage));
//        this.errorCode = errorCode;
//    }
//
//    public BusinessException(String message, Throwable cause) {
//        super(message, cause);
//    }
//
//    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
//        super(message, cause, enableSuppression, writableStackTrace);
//    }
}
