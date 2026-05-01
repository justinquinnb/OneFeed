package dev.jqb.onefeed.api.pipeline;

import dev.jqb.onefeed.api.caching.CacheEntry;
import dev.jqb.onefeed.api.content.NormalizedContent;
import dev.jqb.onefeed.api.feed.FeedInfo;
import java.time.Instant;
import java.util.List;
import org.pf4j.ExtensionPoint;

/**
 * Provides a means of caching and retrieving {@link NormalizedContent}
 *
 * @param <T> the type of {@link NormalizedContent} being cached
 */
public interface ContentCacher<T extends NormalizedContent> extends ExtensionPoint {

    /**
     * Gets the {@code amount} most recent content from the cache.
     *
     * @param feed the feed whose content to retrieve
     * @param amount the amount of content to try retrieving
     *
     * @return at most {@code amount} pieces of cached content from the desired feed
     */
    List<T> getMostRecent(FeedInfo<?> feed, int amount);

    /**
     * Gets the refresh timestamp of the content that hasn't been refreshed in the longest amount
     * of time (i.e., is the most stale).
     *
     * @param feed the feed whose data's freshness to check
     *
     * @return the refresh timestamp of the content in the desired {@code feed} that hasn't seen a
     * refresh in the longest amount of time
     *
     * @throws IllegalStateException if no content has been cached for the specified {@code feed}
     */
    Instant getStalestRefreshTime(FeedInfo<?> feed) throws IllegalStateException;

    /**
     * Caches the given {@code content}.
     *
     * @param content the content to cache
     *
     * @implNote the cache already contains a piece of content, update any changed fields
     * and its last updated timestamp
     */
    void cache(List<CacheEntry<T>> content);
}
