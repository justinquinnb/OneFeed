package dev.jqb.onefeed;

import dev.jqb.onefeed.content.model.Feed;
import dev.jqb.onefeed.content.model.NormalizedContent;
import dev.jqb.onefeed.content.pipeline.Aggregator;
import dev.jqb.onefeed.content.pipeline.ContentFilter;
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
