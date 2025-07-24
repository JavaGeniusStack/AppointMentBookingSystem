package com.truemed.Abs.Exception;
import org.springframework.http.HttpStatus;

public class apiCustomException extends RuntimeException {

    private final HttpStatus httpStatus;

    public apiCustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

