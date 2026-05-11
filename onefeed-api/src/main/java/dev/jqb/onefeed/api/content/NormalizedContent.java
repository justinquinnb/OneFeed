package dev.jqb.onefeed.api.content;

import dev.jqb.onefeed.api.pipeline.Provider;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * {@link Provider}-generated content that has since been normalized,
 * making it ready for caching and distribution by OneFeed
 */
@NoArgsConstructor
@Getter
@Setter
public abstract class NormalizedContent extends Content {

    /**
     * Constructs a piece of {@code NormalizedContent} attributed to the provided {@code source}
     * and published at the given time.
     *
     * @param source the origin of the {@code Content}
     * @param published the time the {@code Content} was published on its {@code source}
     */
    public NormalizedContent(Source source, Instant published) {
        super(source, published);
    }
}
