package dev.jqb.onefeed.content.pipeline;

import dev.jqb.onefeed.content.model.Feed;
import dev.jqb.onefeed.content.model.NormalizedContent;
import java.util.HashMap;
import java.util.List;

/**
 * An aggregator of content across multiple {@link Provider}s
 */
public interface Aggregator {

    /**
     * Attempts to aggregate the given {@code amount} of content from the given {@code sources} with
     * the given {@code filters} applied.
     *
     * @param amount the target amount of content to return after all filters have been applied
     * @param feeds the feeds to aggregate content from
     * @param filters the filters to apply to the aggregated content, limiting the results
     * @param config a map of configuration options for the provider to use, containing info like
     *               API keys
     *
     * @return a list of all content that was retrieved, in descending order of creation timestamp
     */
    List<NormalizedContent> aggregate(int amount, List<Feed<?>> feeds,
        List<ContentFilter<?>> filters, HashMap<String, String> config);
}
