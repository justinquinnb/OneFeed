package dev.jqb.onefeed.core.feed;

import com.fasterxml.jackson.annotation.JsonCreator;
import dev.jqb.onefeed.core.content.Content;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A platform-specific cursor and offset to identify the next piece of content desired from the API
 * when the adjacent, prior content's cursor is possibly unknown
 */
@Getter
@Setter
@NoArgsConstructor
public class FeedCursor {

    private static final Logger logger = LoggerFactory.getLogger(FeedCursor.class);

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
     * Constructs a {@code FeedCursor} with the given cursor and offset.
     *
     * @param cursorOnPlatform the cursor, as provided by the platform's API
     * @param offsetFromCursor the distance from the cursor's item to the first item you desire from
     *                         the API. For example, if the cursor points to content piece 11, an
     *                         offset of 3 indicates content piece 14 should be the first piece to
     *                         consider.
     */
    public FeedCursor(String cursorOnPlatform, int offsetFromCursor) {
        this.cursorOnPlatform = cursorOnPlatform;
        this.offsetFromCursor = offsetFromCursor;
    }

    /**
     * Generates a {@link FeedCursor} for the next piece of content in the provided list of
     * content.
     *
     * @param content the list of content to generate the cursor from
     * @return a cursor indicating how to the next piece of content in the list can be obtained
     * from the platform
     */
    public static FeedCursor from(List<? extends Content> content) {
        List<? extends Content> sortedContent = new ArrayList<>(content);
        sortedContent.sort(Content::compareTo);
        Content initialContent = sortedContent.getFirst();
        FeedCursor feedCursor = new FeedCursor(
            initialContent.getNextPageCursor(), 0);

        for (Content c : sortedContent.subList(1, sortedContent.size())) {
            // Nth piece of content in feed
            // Piece of content has no next page cursor
            if (c.getNextPageCursor() == null) {
                int currentOffset = feedCursor.getOffsetFromCursor();
                feedCursor.setOffsetFromCursor(currentOffset + 1);
            } else { // Piece of content HAS a next page cursor
                feedCursor.setOffsetFromCursor(0);
                feedCursor.setCursorOnPlatform(c.getNextPageCursor());
            }
        }

        return feedCursor;
    }

    /**
     * Encodes this {@code FeedCursor} into a string.
     * @return a string representation of this {@code FeedCursor} in format
     * {@code cursorOnPlatform}{@link #OFFSET_PREFIX}{@code offset}. Offset prefix and offset are only
     * included if {@link #offsetFromCursor} is non-zero.
     */
    public String encode() {
        if (offsetFromCursor == 0) {
            return cursorOnPlatform;
        } else {
            return String.format("%s%s%d", cursorOnPlatform, OFFSET_PREFIX, offsetFromCursor);
        }
    }

    /**
     * Parses a {@code FeedCursor} from a string.
     * @param encodedCursor the string to parse, of format
     * {@code cursorOnPlatform}{@link #OFFSET_PREFIX}{@code offset}. Offset prefix and offset are 
     *                     only required if {@link #offsetFromCursor} is non-zero.
     * @return a {@code FeedCursor} object representing the given string
     */
    @JsonCreator
    public static FeedCursor decode(String encodedCursor) {
        logger.debug("Parsing cursor string: {}", encodedCursor);
        int splitAt = encodedCursor.lastIndexOf(OFFSET_PREFIX);
        
        // Default vals
        String cursor = encodedCursor;
        int offset = 0;
        
        // If an offset is present, parse it out and separate it from the cursor value
        if (splitAt != -1) {
            cursor = encodedCursor.substring(0, splitAt);
            offset = Integer.parseInt(encodedCursor.substring(splitAt + 1));
        }
        
        FeedCursor fc = new FeedCursor(cursor, offset);
        logger.debug("Parsed into: {}", fc);
        return fc;
    }

    @Override
    public String toString() {
        return encode();
    }
}
