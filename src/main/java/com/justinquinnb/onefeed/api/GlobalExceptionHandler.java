package com.justinquinnb.onefeed.api;

import com.justinquinnb.onefeed.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages global exception handling.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({
            IllegalContentCountException.class,
            InvalidSourceIdException.class,
            InvalidSourceTypeException.class,
            InvalidTimeException.class,
            InvalidTimeRangeException.class,
            TokenEntryNotFound.class
    })
    public ResponseEntity<Object> handleOneFeedException(IllegalArgumentException ex) {
        return buildExceptionResponse(ex, HttpStatus.BAD_REQUEST, "Invalid content request");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        return buildExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "OneFeed could not process the request",
                "An internal server error occurred");
    }

    /**
     * Builds a {@link ResponseEntity} object containing relevant exception details.
     *
     * @param status the {@link HttpStatus} value to respond with
     * @param message a message to the end user
     * @param detail a message to the developer
     *
     * @return a {@link ResponseEntity} object containing relevant exception details.
     */
    private static ResponseEntity<Object> buildExceptionResponse(
            HttpStatus status, String message, String detail
    ) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.ofEpochMilli(System.currentTimeMillis()));
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("detail", detail);

        return new ResponseEntity<>(body, status);
    }

    /**
     * Builds a {@link ResponseEntity} object containing relevant exception details.
     *
     * @param exception the thrown {@link Exception}
     * @param status the {@link HttpStatus} value to respond with
     * @param message a message to the end user
     *
     * @return a {@link ResponseEntity} object containing relevant exception details.
     */
    private static ResponseEntity<Object> buildExceptionResponse(
        Exception exception, HttpStatus status, String message) {
        return buildExceptionResponse(status, message, exception.getMessage());
    }
}