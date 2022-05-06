package com.hseapple.app.error;

import org.slf4j.Logger;

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
