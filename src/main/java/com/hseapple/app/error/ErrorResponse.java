package com.hseapple.app.error;


public class ErrorResponse {
    private String error;

    public String getMessage() {
        return error;
    }

    public ErrorResponse(String error) {
        this.error = error;
    }

    public ErrorResponse() {}
}
