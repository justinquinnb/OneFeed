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
    private final String name;

    /**
     * Creates a new {@code Feed} for the given {@code provider} and feed {@code name}.
     *
     * @param provider the provider of content on the target platform
     * @param name the name of the feed
     */
    public Feed(Provider<Out> provider, String name) {
        this.provider = provider;
        this.name = name;
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
    public Mono<ProviderResponse<Out>> fetchRecentContent(
        int amount,
        List<ContentFilter<?>> filters,
        HashMap<String, String> config
    ) {
        return provider.fetchRecentContent(name, amount, filters, config);
    }
}
