package dev.jqb.onefeed.core.aggregation;

import dev.jqb.onefeed.core.content.Content;
import dev.jqb.onefeed.core.feed.BaseFeed;
import dev.jqb.onefeed.core.feed.Feed;
import dev.jqb.onefeed.core.feed.FeedCursor;
import dev.jqb.onefeed.core.feed.FeedId;
import dev.jqb.onefeed.core.feed.FeedPermissions;
import dev.jqb.onefeed.core.feed.ReadableFeed;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/**
 * An aggregator of content across multiple {@link Feed}s
 */
public class Aggregation<C extends Content> extends BaseFeed implements ReadableFeed<C> {
    private static final Logger logger = LoggerFactory.getLogger(Aggregation.class);

    /**
     * The sources of content to pull from
     */
    private final List<ReadableFeed<? extends Content>> feeds;

    /**
     * The mappers used to normalize the various feeds' output content types into a uniform type
     */
    private final Map<String, Function<? extends Content, C>> mappers;

    /**
     * The options to adjust the contents of the returned aggregation
     */
    private final AggregationOptions options;

    /**
     * Creates a new aggregation of content from the given {@code feeds} with the given
     * {@code options} applied.
     *
     * @param id the ID of the created aggregation
     * @param url a URL to the aggregation's source
     * @param feeds the feeds to aggregate content from
     * @param mappers the mapper functions to apply to the content before returning it, 
     *                    normalizing their form
     * @param options the options to adjust the contents of the returned aggregation
     */
    public Aggregation(
        FeedId id,
        String url,
        List<ReadableFeed<? extends Content>> feeds,
        Map<String, Function<? extends Content, C>> mappers,
        AggregationOptions options,
        FeedPermissions permissions
    ) {
        super(id, url, permissions);
        this.options = options;

        this.feeds = feeds;
        this.mappers= mappers;
    }

    @Override
    public Flux<C> fetchRecentContent(int amount) {
        Map<FeedId, Integer> targetAmounts = options.getTargetAmounts(amount);
        List<Flux<C>> normalizedContentStreams = new ArrayList<>(feeds.size());

        for (ReadableFeed<? extends Content> feed : feeds) {
            Function<Content, C> contentMapper =
                (Function<Content, C>) mappers.get(feed.getProviderId());

            Flux<? extends Content> feedStream = feed.fetchRecentContent(
                targetAmounts.get(feed.getId()));

            normalizedContentStreams.add(
                feedStream
                    .map(contentMapper::apply)
                    .doOnError(err -> logger.warn(
                        "Error fetching content from feed '{}': {}", feed.getId().feedName(),
                        err.getStackTrace()))
                    .onErrorComplete()
            );
        }

        return Flux.merge(normalizedContentStreams);
    }

    /**
     * @param aggregateCursor an aggregate cursor for the feed to retrieve content after, inclusive
     */
    @Override
    public Flux<C> fetchRecentContent(int amount, FeedCursor aggregateCursor) {
        if (!(aggregateCursor instanceof AggregateCursor)) {
            throw new IllegalArgumentException("The provided cursor must be an aggregate cursor");
        }

        Map<FeedId, Integer> targetAmounts = options.getTargetAmounts(amount);
        Map<FeedId, FeedCursor> cursors = ((AggregateCursor) aggregateCursor).separate();
        List<Flux<C>> normalizedContentStreams = new ArrayList<>(feeds.size());

        for (ReadableFeed<? extends Content> feed : feeds) {
            Function<Content, C> contentMapper =
                (Function<Content, C>) mappers.get(feed.getProviderId());

            Flux<? extends Content> feedStream = feed.fetchRecentContent(
                targetAmounts.get(feed.getId()), cursors.get(feed.getId()));

            normalizedContentStreams.add(
                feedStream
                    .map(contentMapper::apply)
                    .doOnError(err -> logger.warn(
                        "Error fetching content from feed '{}': {}", feed.getId().feedName(),
                        err.getStackTrace()))
                    .onErrorComplete()
            );
        }

        return Flux.merge(normalizedContentStreams);
    }
}
