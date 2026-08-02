package dev.jqb.onefeed.core.feed;

import dev.jqb.onefeed.core.provider.ProviderIdentifiable;

/**
 * A single feed of content from a single provider
 */
public interface Feed extends ProviderIdentifiable {

    /**
     * Gets the unique ID of the feed
     */
    FeedId getId();

    /**
     * Gets the attribution for this feed
     */
    FeedAttribution getAttribution();

    /**
     * Gets the Feed's current read/write permissions
     */
    FeedPermissions getPermissions();
}
