package com.jasper.exception;

import org.springframework.http.HttpStatus;

public class CotizaAPIException extends RuntimeException{

    private HttpStatus status;
    private String message;

    public CotizaAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public CotizaAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
