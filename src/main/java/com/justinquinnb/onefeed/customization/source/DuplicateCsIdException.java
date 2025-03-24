package com.justinquinnb.onefeed.customization.source;

import com.justinquinnb.onefeed.content.details.ContentSourceId;

/**
 * An exception to be thrown when a {@link ContentSourceId} is attempted to instantiated with an ID that matches
 * another, already-instantiated {@link ContentSource}'s {@code ContentSourceId}.
 */
public class DuplicateCsIdException extends RuntimeException {
    public DuplicateCsIdException(String message) {
        super(message);
    }
}
