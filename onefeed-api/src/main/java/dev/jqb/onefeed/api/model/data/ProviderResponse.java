package dev.jqb.onefeed.api.model.data;

import dev.jqb.onefeed.api.model.pipeline.ContentFilter;
import dev.jqb.onefeed.api.model.pipeline.Provider;
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
public class ProviderResponse<Out extends RawContent> extends ContentPackage<Out> {
    /**
     * The {@link ContentFilter}s that were used to derive the content
     */
    @NonNull
    private List<ContentFilter<?>> appliedFilters;
}
