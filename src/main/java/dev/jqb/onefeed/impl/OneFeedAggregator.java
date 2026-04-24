package dev.jqb.onefeed.impl;

import dev.jqb.onefeed.model.data.Feed;
import dev.jqb.onefeed.model.data.NormalizedContent;
import dev.jqb.onefeed.model.pipeline.Aggregator;
import dev.jqb.onefeed.model.pipeline.ContentFilter;
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
