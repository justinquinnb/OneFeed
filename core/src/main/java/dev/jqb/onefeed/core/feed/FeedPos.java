package dev.jqb.onefeed.core.feed;

import dev.jqb.onefeed.core.content.Content;
import dev.jqb.onefeed.core.exception.MalformedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A combined cursor and offset used to identify the next piece of content desired in a feed
 * when the adjacent, prior content's cursor is possibly unknown
 */
@Getter
@Setter
@NoArgsConstructor
public class FeedPos {
    
    /**
     * The prefix used to separate the preceding {@link #cursorOnPlatform} from the
     * {@link #offsetFromCursor}
     */
    public static final String OFFSET_PREFIX = "+";

    /**
     * The cursor, as provided by the platform's API
     */
    protected String cursorOnPlatform;

    /**
     * The distance from the cursor's item to the first item you desire from the API. </br></br>
     * For example, if the cursor points to content piece 11, an offset of 3 indicates content piece
     * 14 should be the first piece to consider.
     */
    protected int offsetFromCursor;

    /**
     * Constructs a {@code FeedPos} with the given cursor and offset.
     *
     * @param cursorOnPlatform the cursor, as provided by the platform's API
     * @param offsetFromCursor the distance from the cursor's item to the first item you desire from
     *                         the API. For example, if the cursor points to content piece 11, an
     *                         offset of 3 indicates content piece 14 should be the first piece to
     *                         consider.
     */
    public FeedPos(String cursorOnPlatform, int offsetFromCursor) {
        this.cursorOnPlatform = cursorOnPlatform;
        this.offsetFromCursor = offsetFromCursor;
    }

    /**
     * Generates a {@link FeedPos} for the next piece of content in the provided list of
     * content.
     *
     * @param content the list of content to generate the position from
     * @return a position indicating how the next piece of content in the list can be obtained
     * from the platform
     */
    public static FeedPos nextFor(List<? extends Content> content) {
        List<? extends Content> sortedContent = new ArrayList<>(content);
        sortedContent.sort(Content::compareTo);
        Content initialContent = sortedContent.getFirst();
        FeedPos feedPos = new FeedPos(
            initialContent.getNextPageCursor(), 0);

        for (Content c : sortedContent.subList(1, sortedContent.size())) {
            // Nth piece of content in feed
            // Piece of content has no next page cursor
            if (c.getNextPageCursor() == null) {
                int currentOffset = feedPos.getOffsetFromCursor();
                feedPos.setOffsetFromCursor(currentOffset + 1);
            } else { // Piece of content HAS a next page cursor
                feedPos.setOffsetFromCursor(0);
                feedPos.setCursorOnPlatform(c.getNextPageCursor());
            }
        }

        return feedPos;
    }

    /**
     * Encodes this {@code FeedPos} into a string.
     * @return a string representation of this {@code FeedPos} in format 
     * {@code cursorOnPlatform}{@link #OFFSET_PREFIX}{@code offset}
     */
    public String encode() {
        return String.format("%s%s%d", cursorOnPlatform, OFFSET_PREFIX, offsetFromCursor);
    }

    /**
     * Parses a {@code FeedPos} from a string.
     * @param encodedPos the string to parse, of format
     * {@code cursorOnPlatform}{@link #OFFSET_PREFIX}{@code offset}
     *
     * @return a {@code FeedPos} object representing the given string
     * @throws MalformedEncodingException if the given string is not in the expected format
     */
    public static FeedPos decode(String encodedPos) throws MalformedEncodingException {
        String[] parts = encodedPos.split(Pattern.quote(OFFSET_PREFIX));

        if (parts.length != 2) {
            throw new MalformedEncodingException(encodedPos, FeedPos.class, String.format("Expected two %s-separated parts. Found %d.", OFFSET_PREFIX, parts.length));
        }

        String cursor = parts[0];

        try {
            int offset = Integer.parseInt(parts[1]);
            return new FeedPos(cursor, offset);
        } catch (NumberFormatException e) {
            throw new MalformedEncodingException(encodedPos, FeedPos.class, String.format("Expected a numeric offset. Found %s.", parts[1]));
        }
    }

    /**
     * Validates that the given, encoded position string is of format {@code cursor}{@link #OFFSET_PREFIX}{@code offset}.
     * @param encodedPos the encoded position string to validate
     * @throws MalformedEncodingException if the given string is not in the expected format
     */
    public static void validateEncoded(String encodedPos) throws MalformedEncodingException {
        String[] parts = encodedPos.split(Pattern.quote(OFFSET_PREFIX));

        if (parts.length != 2) {
            throw new MalformedEncodingException(encodedPos, FeedPos.class, String.format("Expected two %s-separated parts. Found %d.", OFFSET_PREFIX, parts.length));
        }

        String cursor = parts[0];

        try {
            Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new MalformedEncodingException(encodedPos, FeedPos.class, String.format("Expected a numeric offset. Found %s.", parts[1]));
        }
    }

    @Override
    public String toString() {
        return encode();
    }
}
