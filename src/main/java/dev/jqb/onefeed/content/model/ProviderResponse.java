package dev.jqb.onefeed.content.model;

import dev.jqb.onefeed.content.pipeline.ContentFilter;
import dev.jqb.onefeed.content.pipeline.Provider;
import java.time.Instant;
import java.util.List;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * A response from a {@link Provider} containg retrieved,
 * {@link RawContent} and metadata about it/the retrieval
 *
 * @param <Out> the type of {@link RawContent} contained in this response
 */
@Getter @Setter
public class ProviderResponse<Out extends RawContent> {

    /**
     * The requested content, normalized and ready for distribution
     */
    @NonNull
    private List<Out> content;

    /**
     * The {@link ContentFilter}s that were used to derive the {@link #content}
     */
    @NonNull
    private List<ContentFilter> appliedFilters;

    /**
     * The time at which {@code this} respones was generated
     */
    private Instant generated;
}
