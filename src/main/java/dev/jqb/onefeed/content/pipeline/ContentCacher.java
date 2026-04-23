package dev.jqb.onefeed.content.pipeline;

import dev.jqb.onefeed.content.model.Author;
import dev.jqb.onefeed.content.model.NormalizedContent;
import dev.jqb.onefeed.content.model.Platform;
import java.time.Instant;
import java.util.List;
import org.pf4j.ExtensionPoint;

/**
 * Provides a means of caching and retrieving {@link NormalizedContent}
 *
 * @param <T> the type of {@link NormalizedContent} being cache
 */
public interface ContentCacher<T extends NormalizedContent> extends ExtensionPoint {

    /**
     * Gets the {@code amount} most recent content from the cache.
     *
     * @param platform the platform whose content to retrieve
     * @param author the author whose content to retrieve
     * @param amount the amount of content to try retrieving
     *
     * @return at most {@code amount} pieces of cached content from the {@code author} on
     * {@code platform}
     */
    List<T> getMostRecent(Platform platform, Author author, int amount);

    /**
     * Gets the last time the cache was updated for the given {@code platform} and {@code author}.
     *
     * @param platform the platform whose content to check the latest retrieval time of
     * @param author the specific author whose content to check the latest retrieval time of
     *
     * @return the last time the cache was updated for the given {@code platform} and {@code author}
     * @throws IllegalStateException if no content has been cached for that platform-author pair
     */
    Instant getTimeOfLastUpdate(Platform platform, Author author) throws IllegalStateException;

    /**
     * Caches the given {@code content} for the given {@code platform}-{@code author} combo.
     * @param platform the platform whose content to cache
     * @param content the content to cache
     *
     * @implSpec If the cache already contains a piece of content, update any changed fields
     * and its last updated timestamp
     */
    void cache(Platform platform, List<T> content);
}
