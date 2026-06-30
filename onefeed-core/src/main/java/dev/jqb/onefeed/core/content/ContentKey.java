package dev.jqb.onefeed.core.content;

import dev.jqb.onefeed.core.feed.FeedId;
import dev.jqb.onefeed.core.feed.FeedIdentifiable;

/**
 * A unique identifier for a piece of content in OneFeed
 *
 * @param feedId the unique identifier of the feed the content is from
 * @param idOnPlatform the unique identifier of the content on its source platform
 */
public record ContentKey(FeedId feedId, String idOnPlatform) implements FeedIdentifiable {
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
     * {@link FeedId#toString}{@code :}{@link #idOnPlatform}
     */
    public String toKeyString() {
        return String.format("%s:%s", feedId, idOnPlatform);
    }
}
