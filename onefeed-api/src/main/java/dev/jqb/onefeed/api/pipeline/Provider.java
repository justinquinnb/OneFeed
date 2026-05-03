package dev.jqb.onefeed.api.pipeline;

import dev.jqb.onefeed.api.content.RawContent;
import dev.jqb.onefeed.api.feed.Platform;
import dev.jqb.onefeed.api.impl.OneFeedContent;
import java.util.HashMap;
import java.util.List;
import org.pf4j.ExtensionPoint;
import reactor.core.publisher.Mono;

/**
 * A provider of feed content via APIs
 *
 * @param <Out> the type of DTO that the provider produces
 */
public interface Provider<Out extends RawContent> extends ExtensionPoint {

    /**
     * Fetches the given {@code amount} of most recently published content from {@code this}
     * provider's content source for the given {@code author}.
     * 
     * @param author the author of the content to retrieve
     * @param amount the target amount of content to retrieve
     * @param filters the filters to try applying if supported by the API or best performed on the 
     *                {@link Out} content itself
     * @param config a map of configuration options for this specific request
     *               
     * @return a {@link Mono} that emits a {@link ProviderResponse} containing the retrieved content
     */
    Mono<ProviderResponse<Out>> fetchRecentContent(
        String author,
        int amount,
        List<ContentFilter<?>> filters,
        HashMap<String, String> config
    );

    /**
     * Gets the {@link Normalizer} capable of transforming this provider's
     * {@link RawContent} DTO into normalized {@link OneFeedContent}
     *
     * @return a {@link Normalizer} capable of transforming this provider's
     * {@link RawContent} DTO into normalized {@link OneFeedContent}
     */
    Normalizer<Out, OneFeedContent> getNormalizer();

    /**
     * Gets info about this provider's source platform.
     * @return info about the source platform of this provider's content
     */
    Platform getPlatformInfo();
}
