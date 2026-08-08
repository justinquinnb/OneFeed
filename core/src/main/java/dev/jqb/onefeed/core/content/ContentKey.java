package dev.jqb.onefeed.core.content;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import dev.jqb.onefeed.core.feed.FeedId;
import dev.jqb.onefeed.core.feed.FeedIdentifiable;
import java.util.regex.Pattern;

/**
 * A unique identifier for a piece of content in OneFeed
 *
 * @param feedId the unique identifier of the feed the content is from
 * @param idOnPlatform the unique identifier of the content on its source platform
 */
public record ContentKey(FeedId feedId, String idOnPlatform) implements FeedIdentifiable {
    /**
     * The character used to separate the feed ID from the platform ID component of the encoded
     * {@code ContentKey}
     */
    public static final String PLATFORM_ID_PREFIX = ":";

    @Override
    public FeedId getFeedId() {
        return feedId;
    }

    @Override
    public String getProviderId() {
        return feedId.getProviderId();
    }

    /**
     * Gets a string representation of this content key.
     * @return a string representation of this content key in format
     * {@link FeedId#toString}{@link #PLATFORM_ID_PREFIX}{@link #idOnPlatform}
     */
    @JsonValue
    public String toKeyString() {
        return feedId.toString() + PLATFORM_ID_PREFIX + idOnPlatform;
    }

    /**
     * Parses a {@code ContentKey} from a string.
     * @param keyString the string to parse, of format
     * {@link FeedId#toString}{@link #PLATFORM_ID_PREFIX}{@link #idOnPlatform}
     * @return the {@code ContentKey} represented by {@code keyString}
     */
    @JsonCreator
    public static ContentKey fromKeyString(String keyString) {
        String[] parts = keyString.split(Pattern.quote(PLATFORM_ID_PREFIX));
        return new ContentKey(FeedId.decode(parts[0]), parts[1]);
    }
}
