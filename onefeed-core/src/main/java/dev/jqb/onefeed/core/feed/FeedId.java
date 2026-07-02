package dev.jqb.onefeed.core.feed;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * A means of identifying a single feed of content
 *
 * @param providerId the unique identifier of the provider plugin exposing the feed
 * @param feedName the ID of the feed as exposed by the provider
 */
public record FeedId(String providerId, String feedName) implements FeedIdentifiable {

    /**
     * Converts this {@code FeedNameentifier} to a string suitable for use as a unique key
     * @return a string of format: {@link #providerId}{@code :}{@link #feedName}
     */
    @Override
    @JsonValue
    public String toString() {
        return providerId + ":" + feedName;
    }

    /**
     * Converts a string of format {@link #providerId}{@code :}{@link #feedName} into a
     * {@code FeedId} object.
     *
     * @param idString the string to convert
     * @return a {@code FeedId} object representing the given string
     */
    @JsonCreator
    public static FeedId fromString(String idString) {
        String[] parts = idString.split(":");

        if (parts.length != 2) {
            String reason = String.format("Expected 2 ':'-separated parts. Found %d.", parts.length);
            throw new MalformedFeedIdException(idString, reason);
        }

        return new FeedId(parts[0], parts[1]);
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
