package dev.jqb.onefeed.core.exception;

/**
 * Thrown when an encoded string is malformed
 */
public class MalformedEncodingException extends IllegalArgumentException {

    /**
     * Creates a new {@code MalformedEncodingException} with the given malformed position and reason
     * @param malformedEncoding the malformed, encoded string
     * @param targetType the type of the object the string was attempted to be decoded into
     * @param reason the reason the encoded string is considered malformed
     */
    public MalformedEncodingException(String malformedEncoding, Class<?> targetType, String reason) {
        super(String.format("Malformed %s string encoding '%s': %s", targetType.getSimpleName(),
            malformedEncoding, reason));
    }
}
