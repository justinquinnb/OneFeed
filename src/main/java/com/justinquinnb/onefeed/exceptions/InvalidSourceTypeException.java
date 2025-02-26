package com.justinquinnb.onefeed.exceptions;

import com.justinquinnb.onefeed.data.model.source.ContentSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception to be thrown when an operation is requested on a {@link ContentSource} that does not inherit the desired
 * functionality.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Operation not supported for desired Content Source")
public class InvalidSourceTypeException extends UnsupportedOperationException {
    public InvalidSourceTypeException(String message) {
        super(message);
    }
}
