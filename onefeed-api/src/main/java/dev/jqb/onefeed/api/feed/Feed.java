package dev.jqb.onefeed.api.feed;

import dev.jqb.onefeed.api.content.RawContent;
import dev.jqb.onefeed.api.pipeline.ContentFilter;
import dev.jqb.onefeed.api.pipeline.Provider;
import dev.jqb.onefeed.api.pipeline.ProviderResponse;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import reactor.core.publisher.Mono;

/**
 * A single feed of content
 *
 * @param <Out> the DTO type that the feed produces
 */
@Getter
public class Feed<Out extends RawContent> {

    /**
     * The provider that this feed is sourced from
     */
    private final Provider<Out> provider;

    /**
     * The author of the content in this feed
     */
    private final Author author;

    /**
     * This feed's metadata
     */
    private final FeedInfo<? extends Author> info;

    /**
     * Creates a new {@code Feed} for the given {@code provider} and {@code author}.
     *
     * @param provider the provider of content on the target platform
     * @param author the author of the feed on the platform
     */
    public Feed(Provider<Out> provider, Author author) {
        this.provider = provider;
        this.author = author;
        this.info = new FeedInfo<>(provider.getPlatformInfo(), author);
    }

    /**
     * Fetches the given {@code amount} of most recently published content from {@code this} feed.
     *
     * @param amount the target amount of content to retrieve
     * @param filters the filters to try applying if supported by the API or best performed on the
     *                {@link Out} content itself
     * @param config a map of configuration options for this specific request
     *
     * @return a {@link Mono} that emits a {@link ProviderResponse} containing the retrieved content
     */
    public Mono<ProviderResponse<Out>> getContent(
        int amount,
        List<ContentFilter<?>> filters,
        HashMap<String, String> config
    ) {
        return provider.fetchRecentContent(author.getUsername(), amount, filters, config);
    }
}
