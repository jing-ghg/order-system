package com.backend.ordersystem.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomResponseStatusException extends ResponseStatusException {
    private CustomException errorResponse;
    private HttpStatus statusCode;

    public CustomResponseStatusException(HttpStatus status, String reason) {
        super(status, reason);
        this.errorResponse = new CustomException(reason);
        this.statusCode = status;
    }

    public CustomException getErrorResponse() {
        return errorResponse;
    }
    public HttpStatus getStatusCode() {
        return statusCode;
    }
}




