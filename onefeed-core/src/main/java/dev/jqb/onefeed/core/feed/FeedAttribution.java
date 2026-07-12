package dev.jqb.onefeed.core.feed;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSerializeAs;
import com.fasterxml.jackson.annotation.JsonValue;
import dev.jqb.onefeed.core.compat.rss.RssSource;

/**
 * Attributes an item to a source feed
 * @param feedId the ID of the source feed
 * @param feedUrl the URL of the source feed
 */
@JsonSerializeAs(FeedAttribution.class)
public record FeedAttribution(@JsonValue FeedId feedId, @JsonIgnore String feedUrl) implements RssSource {

    @Override
    public String getRssSourceValue() {
        return feedId.feedName();
    }

    @Override
    public String getRssSourceUrl() {
        return feedUrl;
    }
}
