package dev.jqb.onefeed.core.aggregation;

import dev.jqb.onefeed.core.content.Content;
import dev.jqb.onefeed.core.exception.MalformedEncodingException;
import dev.jqb.onefeed.core.feed.FeedId;
import dev.jqb.onefeed.core.feed.FeedPos;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class AggregatePos extends FeedPos {

    /**
     * The character used to separate a {@code FeedId} from its {@code FeedPos}
     */
    public static final String VALUE_PREFIX = "=";

    /**
     * The character used to delimit one encoded {@code FeedPos} from the next
     */
    public static final String POS_SEPARATOR = ",";

    /**
     * Creates an {@code AggregatePos} from a map of {@link FeedId}s to their corresponding
     * {@link FeedPos}s.
     *
     * @param positions the map of {@link FeedId}s to their respective {@link FeedPos}s to build
     *                  an {@code AggregatePos} with
     */
    public AggregatePos(Map<FeedId, FeedPos> positions) {
        super(encodeAsAggregateCursor(positions), 0);
    }

    /**
     * Creates an {@code AggregatePos} from a map of {@link FeedId}s to their corresponding
     * {@link FeedPos}s.
     *
     * @param positions the map of {@link FeedId}s to their respective {@link FeedPos}s to build
     *                  an {@code AggregatePos} with
     * @param aggregateOffset the distance from the first item of the aggregation derived from
     * {@code positions} to the first item you desire from the aggregation
     */
    public AggregatePos(Map<FeedId, FeedPos> positions, int aggregateOffset) {
        super(encodeAsAggregateCursor(positions), aggregateOffset);
    }

    /**
     * Creates an {@code AggregatePos} from an aggregate cursor string and an aggregate offset.
     *
     * @param aggregateCursor the encoded, aggregate combining the {@link FeedPos}s of each feed into one
     * @param aggregateOffset the distance from the first item of the aggregation derived from
     * {@code aggregateCursor} to the first item you desire from that aggregation
     *
     * @see #encodeAsAggregateCursor(Map)
     */
    public AggregatePos(String aggregateCursor, int aggregateOffset) {
        super(aggregateCursor, aggregateOffset);
    }

    /**
     * Generates an {@code AggregatePos} for the next piece of content in the provided list of
     * content.
     *
     * @param content the list of content to generate the position from
     * @return a position indicating how the next piece of content in the list can be obtained
     * from the source platforms
     */
    public static AggregatePos nextFor(List<? extends Content> content) {
        List<? extends Content> sortedContent = new ArrayList<>(content);
        sortedContent.sort(Content::compareTo);

        HashMap<FeedId, FeedPos> oldestFeedPositions = new HashMap<>();

        /* Because the content is in descending timestamp order, the last piece of content with a
           cursor for a feed is easy to get with this
           NOTE: this differs from the Feed's version of this algo bc it's more efficient to just
           pass through this whole list once as opposed to separating this into feed-specific
           lists of content and going from there
         */
        for (Content c : sortedContent) {
            // First piece of content in list for feed
            if (!oldestFeedPositions.containsKey(c.getFeedId())) {
                FeedPos initialCursor = new FeedPos(c.getNextPageCursor(), 0);
                oldestFeedPositions.put(c.getFeedId(), initialCursor);
                continue;
            }

            // Nth piece of content in feed
            // Piece of content has no next page cursor
            FeedPos currentCursor = oldestFeedPositions.get(c.getFeedId());
            if (c.getNextPageCursor() == null) {
                int currentOffset = currentCursor.getOffsetFromCursor();
                currentCursor.setOffsetFromCursor(currentOffset + 1);
            } else { // Piece of content HAS a next page cursor
                currentCursor.setOffsetFromCursor(0);
                currentCursor.setCursorOnPlatform(c.getNextPageCursor());
            }
        }

        return new AggregatePos(oldestFeedPositions);
    }

    /**
     * Decodes an {@code AggregatePos} from a string.
     * @param encodedPos the string to parse, of format
     * {@code <}{@link FeedPosEntry#encode}{@code >}{@link #POS_SEPARATOR}{@code ...}{@link #OFFSET_PREFIX}{@code offset}
     *
     * @return an {@code AggregatePos} object representing the given string
     * @throws MalformedEncodingException if the given string is not in the expected format
     */
    public static AggregatePos decode(String encodedPos) throws MalformedEncodingException{
        // Validate the primary structure
        FeedPos aggregatePos = FeedPos.decode(encodedPos);

        validateEncodedAggregateCursor(aggregatePos.getCursorOnPlatform());

        return new AggregatePos(aggregatePos.getCursorOnPlatform(), aggregatePos.getOffsetFromCursor());
    }

    /**
     * Validates that the given, encoded aggregate cursor is in the expected format.
     *
     * @param aggregateCursor the encoded aggregate cursor of format {@code <}{@link FeedPosEntry#encode}{@code >}{@link #POS_SEPARATOR}{@code ...}
     * @throws MalformedEncodingException if the given string is not in the expected format
     */
    private static void validateEncodedAggregateCursor(String aggregateCursor) throws MalformedEncodingException {
        String[] encodedFeedPosEntries = aggregateCursor.split(Pattern.quote(POS_SEPARATOR));
        for (String encodedFeedPosEntry : encodedFeedPosEntries) {
            FeedPosEntry.validateEncoded(encodedFeedPosEntry);
        }
    }

    /**
     * Encodes the mapping of {@link FeedId}s to {@link FeedPos}s into a single, encoded aggregate
     * cursor string.
     *
     * @param positions a map of {@link FeedId}s to their respective {@link FeedPos}s to build
     *                  an {@code AggregatePos} with
     * @return a string encoding the given map of {@link FeedId}s to {@link FeedPos}s as an
     * aggregate cursor
     */
    private static String encodeAsAggregateCursor(Map<FeedId, FeedPos> positions) {
        List<String> encodedEntries = new ArrayList<>(positions.size());
        for (Entry<FeedId, FeedPos> entry : positions.entrySet()) {
            String encodedEntry = entry.getKey().encode() + VALUE_PREFIX + entry.getValue().encode();
            encodedEntries.add(encodedEntry);
        }
        return String.join(POS_SEPARATOR, encodedEntries);
    }

    /**
     * Breaks {@code this} {@link AggregatePos} into a map of Feed IDs to their individual
     * {@link FeedPos}s.
     **
     * @return a mapping of {@link FeedId}s to {@link FeedPos}s
     */
    public Map<FeedId, FeedPos> separate() {
        String[] encodedCursors = cursorOnPlatform.split(POS_SEPARATOR);
        HashMap<FeedId, FeedPos> decodedCursors = new HashMap<>();

        for (String encodedCursor : encodedCursors) {
            String[] parts = encodedCursor.split(VALUE_PREFIX);
            FeedId feedId = FeedId.decode(parts[0]);
            FeedPos cursor = FeedPos.decode(parts[1]);
            decodedCursors.put(feedId, cursor);
        }

        return decodedCursors;
    }

    /**
     * An entry in an aggregate cursor, consisting of a {@link FeedId} and its corresponding
     * {@link FeedPos}.
     */
    private static final class FeedPosEntry {
        private FeedId feedId;
        private FeedPos cursor;

        private FeedPosEntry(FeedId feedId, FeedPos cursor) {
            this.feedId = feedId;
            this.cursor = cursor;
        }

        /**
         * Decodes the provided {@code encodedEntry} into a {@link FeedPosEntry}.
         * @param encodedEntry a string of format {@code <}{@link FeedId#encode}{@code >}{@link #VALUE_PREFIX}{@code <}{@link FeedPos#encode}{@code >}
         * @return a {@link FeedPosEntry} representing the given string
         * @throws MalformedEncodingException if the given string is not in the expected format
         */
        private static FeedPosEntry decode(String encodedEntry) throws MalformedEncodingException {
            String[] encodedParts = encodedEntry.split(VALUE_PREFIX);
            if (encodedParts.length != 2) {
                String reason = String.format("Expected 2 %s-delimited parts, got %d", VALUE_PREFIX, encodedParts.length);
                throw new MalformedEncodingException(encodedEntry, FeedPosEntry.class, reason);
            }
            FeedId feedId = FeedId.decode(encodedParts[0]);
            FeedPos cursor = FeedPos.decode(encodedParts[1]);
            return new FeedPosEntry(feedId, cursor);
        }

        /**
         * Validates that the given, encoded entry is in the expected format.
         * @param encodedEntry a string of format {@code <}{@link FeedId#encode}{@code >}{@link #VALUE_PREFIX}{@code <}{@link FeedPos#encode}{@code >}
         * @throws MalformedEncodingException if the given string is not in the expected format
         */
        private static void validateEncoded(String encodedEntry) throws MalformedEncodingException {
            String[] encodedParts = encodedEntry.split(VALUE_PREFIX);
            if (encodedParts.length != 2) {
                String reason = String.format("Expected 2 %s-delimited parts, got %d", VALUE_PREFIX, encodedParts.length);
                throw new MalformedEncodingException(encodedEntry, FeedPosEntry.class, reason);
            }

            FeedId.validateEncoded(encodedParts[0]);
            FeedPos.validateEncoded(encodedParts[1]);
        }
    }
}
