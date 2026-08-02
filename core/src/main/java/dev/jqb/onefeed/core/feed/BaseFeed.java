package dev.jqb.onefeed.core.feed;

import dev.jqb.onefeed.core.content.Content;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A single feed of content from a single provider
 */
@Getter
@Setter
@ToString
public abstract class BaseFeed implements Feed {

    /**
     * The unique ID of the feed
     */
    protected final FeedId id;

    /**
     * The URL of the feed on its source platform
     */
    protected String url;

    /**
     * The Feed's current read/write permissions
     */
    protected FeedPermissions permissions;

    /**
     * Creates a new {@code Feed} with ID {@code id}.
     *
     * @param id the unique ID of the feed
     * @param url the URL of the feed on its source platform
     * @param permissions the operations currently permitted by the feed
     */
    public BaseFeed(FeedId id, String url, FeedPermissions permissions) {
        this.id = id;
        this.url = url;
        this.permissions = permissions;
    }

    @Override
    public String getProviderId() {
        return id.getProviderId();
    }

    /**
     * Gets the attribution for this feed
     */
    public FeedAttribution getAttribution() {
        return new FeedAttribution(id, url);
    }

    /**
     * Generates a {@link FeedCursor} from the given list of {@link Content}.
     * @param content the list of {@code Content} to generate the cursor from
     * @return a cursor indicating how to obtain the next piece of content in the list from the
     * platform
     */
    public static FeedCursor fromContent(List<? extends Content> content) {
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
}
