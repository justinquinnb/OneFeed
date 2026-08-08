package dev.jqb.onefeed.core.feed;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import dev.jqb.onefeed.core.exception.MalformedEncodingException;
import java.util.regex.Pattern;

/**
 * A means of identifying a single feed of content
 *
 * @param providerId the unique identifier of the provider plugin exposing the feed
 * @param feedName the ID of the feed as exposed by the provider
 */
public record FeedId(String providerId, String feedName) implements FeedIdentifiable {

    /**
     * The character used to separate the provider ID from the feed name component of the encoded
     * {@code FeedId}
     */
    public static final String FEED_NAME_PREFIX = ":";

    /**
     * Encodeds {@code this} {@code FeedId} to a string suitable for use as a unique key
     * @return a string of format: {@link #providerId}{@link #FEED_NAME_PREFIX}{@link #feedName}
     */
    public String encode() {
        return providerId + FEED_NAME_PREFIX + feedName;
    }

    @Override
    public String toString() {
        return encode();
    }

    /**
     * Converts an encoded ID string of format {@link #providerId}{@link #FEED_NAME_PREFIX}{@link #feedName}
     * into a {@code FeedId} object.
     * @param encodedId the encoded ID string to convert
     *
     * @return a {@code FeedId} object representing the given string
     * @throws MalformedEncodingException if the given string is not in the expected format
     */
    public static FeedId decode(String encodedId) throws MalformedEncodingException {
        String[] parts = encodedId.split(Pattern.quote(FEED_NAME_PREFIX));

        if (parts.length != 2) {
            String reason = String.format("Expected 2 '%s'-separated parts. Found %d.", FEED_NAME_PREFIX, parts.length);
            throw new MalformedEncodingException(encodedId, FeedId.class, reason);
        }

        return new FeedId(parts[0], parts[1]);
    }

    /**
     * Validates that the given, encoded ID string is of format {@link #providerId}{@link #FEED_NAME_PREFIX}{@link #feedName}.
     * @param encodedId the encoded ID string to validate
     * @throws MalformedEncodingException if the given string is not in the expected format
     */
    public static void validateEncoded(String encodedId) throws MalformedEncodingException {
        String[] parts = encodedId.split(Pattern.quote(FEED_NAME_PREFIX));

        if (parts.length != 2) {
            String reason = String.format("Expected 2 '%s'-separated parts. Found %d.", FEED_NAME_PREFIX, parts.length);
            throw new MalformedEncodingException(encodedId, FeedId.class, reason);
        }
    }

    @Override
    public String getProviderId() {
        return providerId;
    }

    @Override
    public FeedId getFeedId() {
        return this;
    }
}
