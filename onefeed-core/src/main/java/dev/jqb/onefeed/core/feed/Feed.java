package dev.jqb.onefeed.core.feed;

import dev.jqb.onefeed.core.content.Content;
import dev.jqb.onefeed.core.provider.ProviderIdentifiable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import reactor.core.publisher.Flux;

/**
 * A single feed of content from a single provider
 *
 * @param <C> the type of {@link Content} that the feed produces
 */
@Getter
@Setter
@ToString
public abstract class Feed<C extends Content> implements ProviderIdentifiable {

    /**
     * The unique ID of the feed
     */
    protected final FeedId id;

    /**
     * A means of accessing the resource on the source platform
     */
    @Getter
    protected String url;

    /**
     * Creates a new {@code Feed} with ID {@code id}.
     *
     * @param id the unique ID of the feed
     */
    public Feed(FeedId id, String url) {
        this.id = id;
        this.url = url;
    }

    /**
     * Fetches the given {@code amount} of most recently published content from {@code this} feed.
     *
     * @param amount the target amount of content to retrieve
     * @return a {@link Flux} that emits a stream of {@link C} containing at most the desired
     * {@code amount} of retrieved content
     */
    public abstract Flux<C> fetchRecentContent(int amount);

    /**
     * Fetches the given {@code amount} of most recently published content after the {@code cursor}
     * from {@code this} feed.
     *
     * @param amount the target amount of content to retrieve
     * @param cursor the reference point to start retrieving content from, inclusive
     * @return a {@link Flux} that emits a stream of {@link C} containing at most the desired
     * {@code amount} of retrieved content
     */
    public abstract Flux<C> fetchRecentContent(int amount, FeedCursor cursor);

    @Override
    public String getProviderId() {
        return id.getProviderId();
    }

    public FeedAttribution getAttribution() {
        return new FeedAttribution(id, url);
    }

    /**
     * Generates a {@link FeedCursor} from the given list of {@link Content}.
     * @param content the list of {@code Content} to generate the cursor from
     * @return a cursor indicating how to obtain the next piece of content in the list from the
     * platform
     */
    public static FeedCursor generateCursor(List<? extends Content> content) {
        List<? extends Content> sortedContent = new ArrayList<>(content);
        sortedContent.sort(Content::compareTo);
        Content initialContent = sortedContent.getFirst();
        FeedCursor feedCursor = new FeedCursor(
            initialContent.getNextPageCursor(), 0);

        for (Content c : sortedContent.subList(1, sortedContent.size())) {
            // Nth piece of content in feed
            // Piece of content has no next page cursor
            if (c.getNextPageCursor() == null || c.getNextPageCursor().isEmpty()) {
                int currentOffset = feedCursor.getOffsetFromCursor();
                feedCursor.setOffsetFromCursor(currentOffset + 1);
            } else { // Piece of content HAS a next page cursor
                feedCursor.setOffsetFromCursor(0);
                feedCursor.setCursorOnPlatform(c.getNextPageCursor());
            }
        }

        return  feedCursor;
    }
}
