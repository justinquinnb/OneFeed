package dev.jqb.onefeed.model.data;

import dev.jqb.onefeed.model.pipeline.ContentFilter;
import dev.jqb.onefeed.model.pipeline.Provider;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import reactor.core.publisher.Mono;

/**
 * A single feed of content
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
    private final FeedInfo info;

    /**
     * Creates a new {@link Feed} for the given {@code provider} and {@code author}.
     * @param provider the provider of content on the target platform
     * @param author the author of the feed on the platform
     */
    public Feed(Provider<Out> provider, Author author) {
        this.provider = provider;
        this.author = author;
        this.info = new FeedInfo(provider.getPlatformInfo(), author);
    }

    /**
     * Attempts to get the desired {@code amount} of content from the {@link #author} on
     * {@link #provider} with the given {@code filters} applied.
     *
     * @param amount the target amount of content to retrieve
     * @param filters the filters to try applying if supported by the API or best performed on the
     *                {@link Out} content itself
     * @param config a map of configuration options for the provider to use, containing info like
     *               API keys
     *
     * @return a {@link Mono} for the {@link ProviderResponse} containing the retrieved content
     */
    public Mono<ProviderResponse<Out>> getContent(
        int amount,
        List<ContentFilter<?>> filters,
        HashMap<String, String> config
    ) {
        return provider.getContent(author.username(), amount, filters, config);
    }
}
