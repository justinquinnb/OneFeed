package dev.jqb.onefeed.core.feed;

import dev.jqb.onefeed.core.content.Content;
import reactor.core.publisher.Flux;

/**
 * A feed that supports reading content from it
 * @param <C> the type of content that the feed produces
 */
public interface ReadableFeed<C extends Content> extends Feed {

    /**
     * Reads the given {@code amount} of most recently published content from {@code this} feed.
     *
     * @param amount the target amount of content to retrieve
     *
     * @return a {@link Flux} that emits a stream of {@link C} containing at most the desired
     * {@code amount} of retrieved content
     *
     * @throws UnpermittedOperationException if the feed does not currently permit reading
     */
    default Flux<C> readRecentContent(int amount) throws UnpermittedOperationException {
        if (!getPermissions().canRead()) {
            throw new UnpermittedOperationException("The feed does not permit reading at this time");
        }
        return fetchRecentContent(amount);
    }

    /**
     * Reads the given {@code amount} of most recently published content after the {@code start}
     * from {@code this} feed.
     *
     * @param amount the target amount of content to retrieve
     * @param start the reference point to start retrieving content from, inclusive
     *
     * @return a {@link Flux} that emits a stream of {@link C} containing at most the desired
     * {@code amount} of retrieved content
     * @throws UnpermittedOperationException if the feed does not currently permit reading
     */
    default Flux<C> readRecentContent(int amount, FeedPos start)
        throws UnpermittedOperationException
    {
        if (!getPermissions().canRead()) {
            throw new UnpermittedOperationException("The feed does not permit reading at this time");
        }
        return fetchRecentContent(amount, start);
    }

    /**
     * Fetches the given {@code amount} of most recently published content from {@code this} feed.
     *
     * @param amount the target amount of content to retrieve
     * @return a {@link Flux} that emits a stream of {@link C} containing at most the desired
     * {@code amount} of retrieved content
     */
    Flux<C> fetchRecentContent(int amount);

    /**
     * Fetches the given {@code amount} of most recently published content after the {@code start}
     * from {@code this} feed.
     *
     * @param amount the target amount of content to retrieve
     * @param start the reference point to start retrieving content from, inclusive
     * @return a {@link Flux} that emits a stream of {@link C} containing at most the desired
     * {@code amount} of retrieved content
     */
    Flux<C> fetchRecentContent(int amount, FeedPos start);
}
