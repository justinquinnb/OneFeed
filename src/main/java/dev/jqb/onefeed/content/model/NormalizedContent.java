package dev.jqb.onefeed.content.model;

import dev.jqb.onefeed.content.pipeline.Provider;
import java.time.Instant;
import lombok.Getter;

/**
 * {@link Provider}-generated content that has since been normalized,
 * making it ready for caching and distribution by OneFeed
 */
@Getter
public abstract class NormalizedContent implements ContentInfo {
    /**
     * Info identifying the origin of the content piece
     */
    private FeedInfo source;

    /**
     * The time at which the content was published
     */
    private Instant published;
}
