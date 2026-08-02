package dev.jqb.onefeed.core.feed;

/**
 * Thrown when a feed operation is attempted that is supported but not permitted
 */
public class UnpermittedOperationException extends RuntimeException {
    public UnpermittedOperationException(String message) {
        super(message);
    }
}
