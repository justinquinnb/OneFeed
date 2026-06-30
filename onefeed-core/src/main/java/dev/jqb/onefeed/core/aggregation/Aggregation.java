package dev.jqb.onefeed.core.aggregation;

import dev.jqb.onefeed.core.actor.Actor;
import dev.jqb.onefeed.core.content.Content;
import dev.jqb.onefeed.core.feed.Feed;
import dev.jqb.onefeed.core.feed.FeedCursor;
import dev.jqb.onefeed.core.feed.FeedId;
import dev.jqb.onefeed.core.provider.Provider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import reactor.core.publisher.Flux;

/**
 * An aggregator of content across multiple {@link Feed}s
 */
public abstract class Aggregation<C extends Content> extends Feed<C> {

    /**
     * The sources of content to pull from
     */
    private final List<Feed<C>> feeds;

    /**
     * The providers exposing the feeds and other platform data
     */
    private final List<Provider<? extends Content, ? extends Actor>> providers;

    /**
     * The options to adjust the contents of the returned aggregation
     */
    private final AggregationOptions options;

    /**
     * Creates a new aggregation of content from the given {@code feeds} with the given {@code options}
     * applied.
     *
     * @param id the ID of the created aggregation
     * @param feedIds the IDs of the feeds to aggregate content from
     * @param providers the providers to aggregate content from
     * @param options the options to adjust the contents of the returned aggregation
     */
    public Aggregation(
        FeedId id,
        List<FeedId> feedIds,
        List<Provider<? extends Content, ? extends Actor>> providers,
        AggregationOptions options
    ) {
        super(id);
        this.providers = providers;
        this.options = options;

        this.feeds = new ArrayList<>(feedIds.size());

        for (Provider<?,?> provider : providers) {
            for (Feed feed : provider.getFeeds()) {
                if (feed.getId().equals(id)) {
                    feeds.add(feed);
                }
            }
        }
    }

    /**
     * @param aggregateCursor an aggregate cursor for the feed to retrieve content after, inclusive
     */
    @Override
    public abstract Flux<C> fetchRecentContent(int amount, FeedCursor aggregateCursor);

    /**
     * Generates an aggregate cursor {@code String} from a list of {@code content}.
     *
     * @param content a list of the content to generate the cursor from
     * @return the aggregate nextPageCursor, encoded in base 64
     */
    protected FeedCursor generateAggregateCursor(List<C> content) {
        List<C> sortedContent = new ArrayList<>(content);
        sortedContent.sort(Content::compareTo);

        HashMap<FeedId, FeedCursor> oldestFeedCursors = new HashMap<>();

        // Because the content is in descending timestamp order, the last piece of content with a
        // cursor for a feed is easy to get with this
        for (C c : sortedContent) {
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
    protected static Map<FeedId, FeedCursor> decodeAggregateCursor(FeedCursor aggregateCursor) {
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
