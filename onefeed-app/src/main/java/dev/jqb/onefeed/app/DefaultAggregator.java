package dev.jqb.onefeed.app;

import dev.jqb.onefeed.api.content.NormalizedContent;
import dev.jqb.onefeed.api.feed.Feed;
import dev.jqb.onefeed.api.feed.Aggregator;
import dev.jqb.onefeed.api.content.ContentFilter;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * A default, built-in {@link Aggregator} implementation for OneFeed
 */
@Service
public class DefaultAggregator implements Aggregator {
    @Override
    public List<NormalizedContent> aggregate(int amount, List<Feed> feeds,
        List<ContentFilter<?>> filters, HashMap<String, String> config) {
        return null;
    }
}
