package com.justinquinnb.onefeed.datastorage.token;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

/**
 * An exception to be thrown when a {@link TokenEntry} cannot be found for the
 * desired Content Source ID in the provided {@link TokenStorage}.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Access token not found")
public class TokenEntryNotFound extends NoSuchElementException {
    public TokenEntryNotFound(String message) {
        super(message);
    }
}