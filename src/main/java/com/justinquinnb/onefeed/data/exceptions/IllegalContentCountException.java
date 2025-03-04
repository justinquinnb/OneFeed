package com.justinquinnb.onefeed.data.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception to be thrown when OneFeed is hit with a Content count that is not a positive integer.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Illegal Content count")
public class IllegalContentCountException extends IllegalArgumentException {
  public IllegalContentCountException(String message){
    super(message);
  }
}
