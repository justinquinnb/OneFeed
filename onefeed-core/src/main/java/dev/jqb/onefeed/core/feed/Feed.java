package dev.jqb.onefeed.core.feed;

import dev.jqb.onefeed.core.content.Content;
import dev.jqb.onefeed.core.provider.ProviderIdentifiable;
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
    private final FeedId id;

    /**
     * Creates a new {@code Feed} with ID {@code id}.
     *
     * @param id the unique ID of the feed
     */
    public Feed(FeedId id) {
        this.id = id;
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
    public abstract Flux<C> fetchRecentContent(int amount, PlatformCursor cursor);

    @Override
    public String getProviderId() {
        return id.getProviderId();
    }
}
