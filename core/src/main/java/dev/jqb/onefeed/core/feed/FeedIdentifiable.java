package dev.jqb.onefeed.core.feed;

import dev.jqb.onefeed.core.provider.ProviderIdentifiable;

/**
 * A type whose source feed can be identified
 */
public interface FeedIdentifiable extends ProviderIdentifiable {

    /**
     * Gets the identifier of the feed the object is from
     * @return the identifier of the feed the object is from
     */
    FeedId getFeedId();
}
