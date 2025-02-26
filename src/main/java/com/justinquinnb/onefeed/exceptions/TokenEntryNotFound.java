package com.justinquinnb.onefeed.exceptions;

import com.justinquinnb.onefeed.data.model.token.TokenStorage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

/**
 * An exception to be thrown when a {@link com.justinquinnb.onefeed.data.model.token.TokenEntry} cannot be found for the
 * desired Content Source ID in the provided {@link TokenStorage}.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Access token not found")
public class TokenEntryNotFound extends NoSuchElementException {
    public TokenEntryNotFound(String message) {
        super(message);
    }
}