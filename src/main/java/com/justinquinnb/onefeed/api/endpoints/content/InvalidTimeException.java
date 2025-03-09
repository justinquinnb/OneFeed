package com.justinquinnb.onefeed.api.endpoints.content;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception to be thrown when a provided time and date encoding is malformed or invalid.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Invalid or malformed time")
public class InvalidTimeException extends IllegalArgumentException {
    public InvalidTimeException(String message){
        super(message);
    }
}
