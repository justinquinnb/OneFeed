package dev.jqb.onefeed.app;

import dev.jqb.onefeed.api.model.data.Feed;
import dev.jqb.onefeed.api.model.data.NormalizedContent;
import dev.jqb.onefeed.api.model.pipeline.Aggregator;
import dev.jqb.onefeed.api.model.pipeline.ContentFilter;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OneFeedAggregator implements Aggregator {
    @Override
    public List<NormalizedContent> aggregate(int amount, List<Feed<?>> feeds,
        List<ContentFilter<?>> filters, HashMap<String, String> config) {
        return null;
    }
}
