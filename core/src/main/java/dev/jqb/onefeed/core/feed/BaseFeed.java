package dev.jqb.onefeed.core.feed;

import dev.jqb.onefeed.core.content.Content;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A single feed of content from a single provider
 */
@Getter
@Setter
@ToString
public abstract class BaseFeed implements Feed {

    /**
     * The unique ID of the feed
     */
    protected final FeedId id;

    /**
     * The URL of the feed on its source platform
     */
    protected String url;

    /**
     * The Feed's current read/write permissions
     */
    protected FeedPermissions permissions;

    /**
     * Creates a new {@code Feed} with ID {@code id}.
     *
     * @param id the unique ID of the feed
     * @param url the URL of the feed on its source platform
     * @param permissions the operations currently permitted by the feed
     */
    public BaseFeed(FeedId id, String url, FeedPermissions permissions) {
        this.id = id;
        this.url = url;
        this.permissions = permissions;
    }

    @Override
    public String getProviderId() {
        return id.getProviderId();
    }

    /**
     * Gets the attribution for this feed
     */
    public FeedAttribution getAttribution() {
        return new FeedAttribution(id, url);
    }
}
