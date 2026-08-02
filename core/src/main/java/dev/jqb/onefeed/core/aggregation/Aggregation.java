package dev.jqb.onefeed.core.aggregation;

import dev.jqb.onefeed.core.content.Content;
import dev.jqb.onefeed.core.content.ContentTransformer;
import dev.jqb.onefeed.core.feed.BaseFeed;
import dev.jqb.onefeed.core.feed.Feed;
import dev.jqb.onefeed.core.feed.FeedCursor;
import dev.jqb.onefeed.core.feed.FeedId;
import dev.jqb.onefeed.core.feed.FeedPermissions;
import dev.jqb.onefeed.core.feed.ReadableFeed;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
     * The providers exposing the feeds and other platform data
     */
    private final Map<String, ContentTransformer<? extends Content, C>> normalizers;

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
     * @param normalizers the normalizers to apply to the content before returning it
     * @param options the options to adjust the contents of the returned aggregation
     */
    public Aggregation(
        FeedId id,
        String url,
        List<ReadableFeed<? extends Content>> feeds,
        Map<String, ContentTransformer<? extends Content, C>> normalizers,
        AggregationOptions options,
        FeedPermissions permissions
    ) {
        super(id, url, permissions);
        this.options = options;

        this.feeds = feeds;
        this.normalizers= normalizers;
    }

    @Override
    public Flux<C> fetchRecentContent(int amount) {
        Map<FeedId, Integer> targetAmounts = options.getTargetAmounts(amount);
        List<Flux<C>> normalizedContentStreams = new ArrayList<>(feeds.size());

        for (ReadableFeed<? extends Content> feed : feeds) {
            ContentTransformer<Content, C> contentNormalizer =
                (ContentTransformer<Content, C>) normalizers.get(feed.getProviderId());

            Flux<? extends Content> feedStream = feed.fetchRecentContent(
                targetAmounts.get(feed.getId()));

            normalizedContentStreams.add(
                feedStream
                    .map(contentNormalizer::transform)
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
        Map<FeedId, Integer> targetAmounts = options.getTargetAmounts(amount);
        Map<FeedId, FeedCursor> decodedCursors = decodeAggregateCursor(aggregateCursor);
        List<Flux<C>> normalizedContentStreams = new ArrayList<>(feeds.size());

        for (ReadableFeed<? extends Content> feed : feeds) {
            ContentTransformer<Content, C> contentNormalizer =
                (ContentTransformer<Content, C>) normalizers.get(feed.getProviderId());

            Flux<? extends Content> feedStream = feed.fetchRecentContent(

            normalizedContentStreams.add(
                feedStream
                    .map(contentNormalizer::transform)
                    .doOnError(err -> logger.warn(
                        "Error fetching content from feed '{}': {}", feed.getId().feedName(),
                        err.getStackTrace()))
                    .onErrorComplete()
            );
        }

        return Flux.merge(normalizedContentStreams);
    }

    /**
     * Generates an aggregate cursor {@code String} from a list of {@code content}.
     *
     * @param content a list of the content to generate the cursor from
     * @return the aggregate nextPageCursor
     */
    public static FeedCursor generateAggregateCursor(List<? extends Content> content) {
        List<? extends Content> sortedContent = new ArrayList<>(content);
        sortedContent.sort(Content::compareTo);

        HashMap<FeedId, FeedCursor> oldestFeedCursors = new HashMap<>();

        /* Because the content is in descending timestamp order, the last piece of content with a
           cursor for a feed is easy to get with this
           NOTE: this differs from the Feed's version of this algo bc it's more efficient to just
           pass through this whole list once as opposed to separating this into feed-specific
           lists of content and going from there
         */
        for (Content c : sortedContent) {
            // First piece of content in list for feed
            if (!oldestFeedCursors.containsKey(c.getFeedId())) {
                FeedCursor initialCursor = new FeedCursor(c.getNextPageCursor(), 0);
                oldestFeedCursors.put(c.getFeedId(), initialCursor);
                continue;
            }

            // Nth piece of content in feed
            // Piece of content has no next page cursor
            FeedCursor currentCursor = oldestFeedCursors.get(c.getFeedId());
            if (c.getNextPageCursor() == null) {
                int currentOffset = currentCursor.getOffsetFromCursor();
                currentCursor.setOffsetFromCursor(currentOffset + 1);
            } else { // Piece of content HAS a next page cursor
                currentCursor.setOffsetFromCursor(0);
                currentCursor.setCursorOnPlatform(c.getNextPageCursor());
            }
        }

        // Comma-separated FeedId:FeedCursor pairs
        // providerId:feedName=cursor+offset,...
        StringBuilder encodedCursorBuilder = new StringBuilder();
        int i = 0;
        for (Entry<FeedId, FeedCursor> entry : oldestFeedCursors.entrySet()) {
            encodedCursorBuilder.append(entry.getKey().toString());
            encodedCursorBuilder.append("=");
            encodedCursorBuilder.append(entry.getValue().toString());

            if (i < oldestFeedCursors.size() - 1) {
                encodedCursorBuilder.append(",");
            }

            i++;
        }

        return new FeedCursor(encodedCursorBuilder.toString(), 0);
    }

    /**
     * Decodes an encoded, aggregate {@link FeedCursor} into a map of Feed IDs to their individual
     * {@code FeedCursor}s.
     *
     * @param aggregateCursor the aggregate cursor to decode
     *
     * @return a mapping of {@link FeedId}s to {@link FeedCursor}s
     */
    public static Map<FeedId, FeedCursor> decodeAggregateCursor(FeedCursor aggregateCursor) {
        String[] encodedCursors = aggregateCursor.getCursorOnPlatform().split(",");
        HashMap<FeedId, FeedCursor> decodedCursors = new HashMap<>();

        for (String encodedCursor : encodedCursors) {
            FeedId feedId = FeedId.fromString(encodedCursor.split("=")[0]);
            FeedCursor cursor = FeedCursor.fromString(encodedCursor.split("=")[1]);
            decodedCursors.put(feedId, cursor);
        }

        return decodedCursors;
    }
}
