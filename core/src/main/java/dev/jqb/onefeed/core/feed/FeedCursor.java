package dev.jqb.onefeed.core.feed;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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
     * The cursor, as provided by the platform's API
     */
    private String cursorOnPlatform;

    /**
     * The distance from the cursor's item to the first item we desire from the API. </br></br>
     * For example, if the cursor points to content piece 11, an offset of 3 indicates content piece
     * 14 should be the first piece to consider.
     */
    private int offsetFromCursor;

    /**
     * Constructs a {@code PlatformCursor} with the given cursor and offset.
     *
     * @param cursorOnPlatform the cursor, as provided by the platform's API
     * @param offsetFromCursor the distance from the cursor's item to the first item we desire from
     *                         the API. For example, if the cursor points to content piece 11, an
     *                         offset of 3 indicates content piece 14 should be the first piece to
     *                         consider.
     */
    public FeedCursor(String cursorOnPlatform, int offsetFromCursor) {
        this.cursorOnPlatform = cursorOnPlatform;
        this.offsetFromCursor = offsetFromCursor;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.format("%s+%d", cursorOnPlatform, offsetFromCursor);
    }

    /**
     * Parses a {@code PlatformCursor} from a string.
     * @param cursorString the string to parse, of format {@code cursor}{@code +}{@code offset}
     * @return a {@code PlatformCursor} object representing the given string
     */
    @JsonCreator
    public static FeedCursor fromString(String cursorString) {
        logger.debug("Parsing cursor string: {}", cursorString);
        int splitAt = cursorString.lastIndexOf('+');
        String cursor = cursorString.substring(0, splitAt);
        int offset = Integer.parseInt(cursorString.substring(splitAt + 1));
        FeedCursor fc = new FeedCursor(cursor, offset);
        logger.debug("Parsed into: {}", fc);
        return fc;
    }
}
