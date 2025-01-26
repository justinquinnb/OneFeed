package com.justinquinnb.onefeed.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception to be thrown when a provided time range encoding is malformed or invalid.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Invalid or malformed time range")
public class InvalidTimeRangeException extends RuntimeException {
    private String message;

    public InvalidTimeRangeException(){}

    public InvalidTimeRangeException(String message){
        super(message);
        this.message = message;
    }
}
