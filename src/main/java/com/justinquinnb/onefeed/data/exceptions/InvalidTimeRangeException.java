package com.justinquinnb.onefeed.data.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception to be thrown when a provided time range encoding is malformed or invalid.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Invalid or malformed time range")
public class InvalidTimeRangeException extends IllegalArgumentException {
    public InvalidTimeRangeException(String message){
        super(message);
    }
}
