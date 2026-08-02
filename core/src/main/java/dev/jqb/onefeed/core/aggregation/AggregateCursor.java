package dev.jqb.onefeed.core.aggregation;

import dev.jqb.onefeed.core.content.Content;
import dev.jqb.onefeed.core.feed.FeedCursor;
import dev.jqb.onefeed.core.feed.FeedId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AggregateCursor extends FeedCursor {

    private static final Logger logger = LoggerFactory.getLogger(AggregateCursor.class);

    /**
     * The character used to separate a {@code FeedId} from its {@code FeedCursor}
     */
    public static final String VALUE_PREFIX = "=";

    /**
     * The character used to delimit one encoded {@code FeedCursor} from the next
     */
    public static final String CURSOR_SEPARATOR = ",";

    /**
     * The character used to separate the master offset from the encoded aggregate cursor value
     */
    public static final String MASTER_OFFSET_PREFIX = OFFSET_PREFIX.repeat(2);

    /**
     * Creates an {@code AggregateCursor} from a map of {@link FeedId}s to their corresponding
     * {@link FeedCursor}s.
     *
     * @param cursors the map of {@link FeedId}s to their respective {@link FeedCursor}s to convert
     *                into an aggregate cursor
     */
    public AggregateCursor(Map<FeedId, FeedCursor> cursors) {
        super(encodeMasterCursor(cursors), 0);
    }

    /**
     * Creates an {@code AggregateCursor} from a map of {@link FeedId}s to their corresponding
     * {@link FeedCursor}s.
     *
     * @param cursors the map of {@link FeedId}s to their respective {@link FeedCursor}s to convert
     *                into an aggregate cursor
     * @param masterOffset the distance from the first item of the aggregation derived from
     * {@code cursors} to the first item you desire from the aggregation
     */
    public AggregateCursor(Map<FeedId, FeedCursor> cursors, int masterOffset) {
        super(encodeMasterCursor(cursors), masterOffset);
    }

    /**
     * Creates an {@code AggregateCursor} from a map of {@link FeedId}s to their corresponding
     * {@link FeedCursor}s.
     *
     * @param masterCursor the encoded, master cursor combining the {@link FeedCursor}s of each feed into one
     * @param masterOffset the distance from the first item of the aggregation derived from
     * {@code cursors} to the first item you desire from the aggregation
     */
    public AggregateCursor(String masterCursor, int masterOffset) {
        super(masterCursor, masterOffset);
    }

    /**
     * Generates an aggregate cursor {@code String} from a list of {@code content}.
     *
     * @param content a list of the content to generate the cursor from
     * @return the aggregate nextPageCursor
     */
    public static AggregateCursor from(List<? extends Content> content) {
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

        return new AggregateCursor(oldestFeedCursors);
    }

    /**
     * Encodes this {@code AggregateCursor} into a string.
     * @return a string representation of this {@code AggregateCursor} in format
     * {@link #encodeMasterCursor}{@link #OFFSET_PREFIX}{@code OFFSET_PREFIX}{@code offset}. Offset
     * prefix and offset are only included if {@link #offsetFromCursor} is non-zero.
     */
    @Override
    public String encode() {
        if (offsetFromCursor == 0) {
            return cursorOnPlatform;
        } else {
            return String.format("%s%s%d", cursorOnPlatform, MASTER_OFFSET_PREFIX,
                offsetFromCursor);
        }
    }

    /**
     * Encodes the value of the cursor as a string given a map of Feed IDs to their respective
     * {@link FeedCursor}s.
     * @param cursors the map of Feed IDs to their respective {@link FeedCursor}s
     * @return a {@code String} encoding the combined feed cursor information (without a master
     * offset) in format {@code <}{@link FeedId#toString()}{@code >}{@link #VALUE_PREFIX}{@code <}{@link FeedCursor#encode}{@code >}{@link #CURSOR_SEPARATOR}{@code ...}{@link #MASTER_OFFSET_PREFIX}{@code <}{@link #offsetFromCursor}{@code >}.
     * Offsets are only included when non-zero.
     */
    private static String encodeMasterCursor(Map<FeedId, FeedCursor> cursors) {
        // Comma-separated FeedId:FeedCursor pairs
        // providerId:feedName=cursor+offset,...
        StringBuilder encodedCursorBuilder = new StringBuilder();
        int i = 0;
        for (Entry<FeedId, FeedCursor> entry : cursors.entrySet()) {
            encodedCursorBuilder.append(entry.getKey().toString());
            encodedCursorBuilder.append(VALUE_PREFIX);
            encodedCursorBuilder.append(entry.getValue().encode());

            if (i < cursors.size() - 1) {
                encodedCursorBuilder.append(CURSOR_SEPARATOR);
            }

            i++;
        }
        return encodedCursorBuilder.toString();
    }

    /**
     * Parses an {@code AggregateCursor} from a string.
     * @param encodedCursor the string to parse, of format {@code <}{@link FeedId#toString()}{@code >}{@link #VALUE_PREFIX}{@code <}{@link FeedCursor#encode}{@code >}{@link #CURSOR_SEPARATOR}{@code ...}{@link #OFFSET_PREFIX}{@code OFFSET_PREFIX}{@code <}{@link #offsetFromCursor}{@code >}.
     * Offsets are only included when non-zero.
     * @return an {@code AggregateCursor} object representing the given string
     */
    public static AggregateCursor decode(String encodedCursor) {
        logger.debug("Parsing aggregate cursor string: {}", encodedCursor);
        int splitAt = encodedCursor.lastIndexOf(MASTER_OFFSET_PREFIX);

        // Default vals
        String cursor = encodedCursor;
        int masterOffset = 0;

        // If an offset is present, parse it out and separate it from the cursor value
        if (splitAt != -1) {
            cursor = encodedCursor.substring(0, splitAt);
            masterOffset = Integer.parseInt(encodedCursor.substring(splitAt + 1));
        }

        AggregateCursor ag = new AggregateCursor(cursor, masterOffset);
        logger.debug("Parsed into: {}", ag);
        return ag;
    }

    /**
     * Breaks {@code this} {@link AggregateCursor} into a map of Feed IDs to their individual
     * {@code FeedCursor}s.
     **
     * @return a mapping of {@link FeedId}s to {@link FeedCursor}s
     */
    public Map<FeedId, FeedCursor> separate() {
        String[] encodedCursors = cursorOnPlatform.split(CURSOR_SEPARATOR);
        HashMap<FeedId, FeedCursor> decodedCursors = new HashMap<>();

        for (String encodedCursor : encodedCursors) {
            FeedId feedId = FeedId.fromString(encodedCursor.split(VALUE_PREFIX)[0]);
            FeedCursor cursor = FeedCursor.decode(encodedCursor.split(VALUE_PREFIX)[1]);
            decodedCursors.put(feedId, cursor);
        }

        return decodedCursors;
    }
}
