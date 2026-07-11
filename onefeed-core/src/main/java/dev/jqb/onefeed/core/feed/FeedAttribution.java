package dev.jqb.onefeed.core.feed;

import dev.jqb.onefeed.core.compat.rss.RssSource;

/**
 * Attributes an item to a source feed
 * @param feedId the ID of the source feed
 * @param feedUrl the URL of the source feed
 */
public record FeedAttribution(FeedId feedId, String feedUrl) implements RssSource {

    @Override
    public String getRssSourceValue() {
        return feedId.feedName();
    }

    @Override
    public String getRssSourceUrl() {
        return feedUrl;
    }
}
