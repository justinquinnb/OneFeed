package dev.jqb.onefeed.api.caching;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * An entry to some cache for an object of type {@code T}
 *
 * @param <T> the type of object being cached
 */
@Getter
@Setter
@AllArgsConstructor
public class CacheEntry<T> {

    /**
     * The data to be cached
     */
    public T data;

    /**
     * The last time the data the cache entry is for has been retrieved from its source. An
     * indicator of data staleness.
     */
    public Instant lastRetrieved;

    /**
     * The Hard TTL cutoff to prevent hoarding objects in the cache
     */
    public Instant expireOn;
}
