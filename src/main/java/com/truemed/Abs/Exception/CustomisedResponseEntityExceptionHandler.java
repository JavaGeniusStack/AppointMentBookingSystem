package com.truemed.Abs.Exception;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomisedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest request) {
        return ResponseEntity.internalServerError().body(buildErrorDetails(ex, request));
    }

    @ExceptionHandler(apiCustomException.class)
    public final ResponseEntity<ErrorDetails> apiCustomException(apiCustomException ex, WebRequest request) {
        return ResponseEntity.internalServerError().body(buildErrorDetails(ex, request));
    }

    private ErrorDetails buildErrorDetails(Exception ex, WebRequest request) {
        String uri = request.getDescription(false).replace("uri=", "");
        return ErrorDetails.builder()
                .timeStamp(LocalDateTime.now())
                .message(ex.getMessage())
                .Details("Request URI: " + uri)
                .build();
    }

}
