package dev.jqb.onefeed.core.provider;

import dev.jqb.onefeed.core.actor.ActorNormalizer;
import dev.jqb.onefeed.core.actor.OneFeedActor;
import dev.jqb.onefeed.core.actor.PlatformActor;
import dev.jqb.onefeed.core.content.ContentNormalizer;
import dev.jqb.onefeed.core.content.OneFeedContent;
import dev.jqb.onefeed.core.content.PlatformContent;
import dev.jqb.onefeed.core.feed.Feed;
import dev.jqb.onefeed.core.platform.Platform;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import reactor.core.publisher.Mono;

/**
 * A provider of feed content via APIs
 *
 * @param <C> the type of {@link PlatformContent} DTO that the provider produces
 * @param <A> the type of {@link PlatformActor} DTO that the provider produces
 */
@Getter
@ToString
public abstract class Provider<C extends PlatformContent, A extends PlatformActor> {

    /**
     * The unique identifier of this provider
     */
    private final String id;

    /**
     * Constructs a new {@code Provider} with the given ID
     * @param id the unique identifier of this provider
     */
    public Provider(String id) {
        this.id = id;
    }

    /**
     * Gets the feeds exposed by {@code this} provider.
     * @return a list of feeds exposed by {@code this} provider
     */
    public abstract List<Feed<C>> getFeeds();

    /**
     * Gets the {@link ContentNormalizer} capable of transforming this provider's
     * {@link PlatformContent} DTOs into normalized {@link OneFeedContent}
     *
     * @return a {@link ContentNormalizer} capable of transforming this provider's
     * {@link PlatformContent} DTO into normalized {@link OneFeedContent}
     */
    public abstract ContentNormalizer<C, OneFeedContent> getContentNormalizer();

    /**
     * Gets the {@link ActorNormalizer} capable of transforming this provider's
     * {@link PlatformActor} DTOs into normalized {@link OneFeedActor}s
     *
     * @return a {@link ActorNormalizer} capable of transforming this provider's
     * {@link PlatformActor} DTO into normalized {@link OneFeedActor}
     */
    public abstract ActorNormalizer<A, OneFeedActor> getActorNormalizer();

    /**
     * Gets info about this provider's source platform.
     * @return info about the source platform of this provider's content
     */
    public abstract Platform getPlatform();
}
