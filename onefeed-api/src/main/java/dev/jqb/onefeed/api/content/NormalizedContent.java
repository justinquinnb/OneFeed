package dev.jqb.onefeed.api.content;

import dev.jqb.onefeed.api.feed.Author;
import dev.jqb.onefeed.api.feed.SourceInfo;
import dev.jqb.onefeed.api.pipeline.Provider;
import java.time.Instant;
import lombok.NoArgsConstructor;

/**
 * {@link Provider}-generated content that has since been normalized,
 * making it ready for caching and distribution by OneFeed
 */
@NoArgsConstructor
public abstract class NormalizedContent extends Content {
    public NormalizedContent(SourceInfo<? extends Author> source, Instant published) {
        super(source, published);
    }
}
