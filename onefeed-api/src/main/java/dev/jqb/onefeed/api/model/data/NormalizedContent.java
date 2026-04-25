package dev.jqb.onefeed.api.model.data;

import dev.jqb.onefeed.api.model.pipeline.Provider;
import java.time.Instant;
import lombok.Getter;

/**
 * {@link Provider}-generated content that has since been normalized,
 * making it ready for caching and distribution by OneFeed
 */
public abstract class NormalizedContent implements ContentInfo {
    /**
     * Info identifying the origin of the content piece
     */
    private SourceInfo source;

    /**
     * The time at which the content was published
     */
    private Instant published;

    @Override
    public SourceInfo getSource() {
        return source;
    }

    @Override
    public Instant getPublished() {
        return published;
    }
}
