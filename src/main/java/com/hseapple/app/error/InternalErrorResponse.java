package com.hseapple.app.error;

public class InternalErrorResponse extends ErrorResponse {
    @Override
    public String getMessage() {
        return "Internal error occured. Please see logs for more information";
    }
}
