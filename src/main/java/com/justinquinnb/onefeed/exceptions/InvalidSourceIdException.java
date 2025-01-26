package com.justinquinnb.onefeed.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception to be thrown when OneFeed is hit with a Content Source ID that cannot be found in the app.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Invalid Content Source ID")
public class InvalidSourceIdException extends RuntimeException {
    private String message;

    public InvalidSourceIdException(){}

    public InvalidSourceIdException(String message){
        super(message);
        this.message = message;
    }
}