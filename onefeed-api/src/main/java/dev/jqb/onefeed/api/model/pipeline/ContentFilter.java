package dev.jqb.onefeed.api.model.pipeline;

import dev.jqb.onefeed.api.model.data.ContentInfo;
import java.util.List;

/**
 * A means of filtering provider content post-retrieval
 *
 * @param <T> the type of {@link ContentInfo} to filter
 */
public interface ContentFilter<T extends ContentInfo> {

    /**
     * Filters the given {@code content}.
     * @param content the content to filter
     * @return the filtered {@code content}
     */
    List<T> filter(List<T> content);
}
