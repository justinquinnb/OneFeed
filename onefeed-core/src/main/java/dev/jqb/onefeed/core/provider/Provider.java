package dev.jqb.onefeed.core.provider;

import dev.jqb.onefeed.core.actor.Actor;
import dev.jqb.onefeed.core.actor.ActorTransformer;
import dev.jqb.onefeed.core.actor.OneFeedActor;
import dev.jqb.onefeed.core.content.Content;
import dev.jqb.onefeed.core.content.ContentTransformer;
import dev.jqb.onefeed.core.content.OneFeedContent;
import dev.jqb.onefeed.core.feed.Feed;
import dev.jqb.onefeed.core.platform.Platform;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * A provider of feed content via APIs
 *
 * @param <C> the type of {@link Content} that the provider produces
 * @param <A> the type of {@link Actor} that the provider produces
 */
@Getter
@ToString
public abstract class Provider<C extends Content, A extends Actor> {

    /**
     * The unique identifier of this provider
     */
    protected final String id;

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
    public abstract List<? extends Feed<C>> getFeeds();

    /**
     * Gets the {@link ContentTransformer} capable of transforming this provider's
     * {@link Content} DTOs into normalized {@link OneFeedContent}
     *
     * @return a {@link ContentTransformer} capable of transforming this provider's
     * {@link Content} DTO into normalized {@link OneFeedContent}
     */
    public abstract ContentTransformer<C, OneFeedContent> getContentNormalizer();

    /**
     * Gets the {@link ActorTransformer} capable of transforming this provider's
     * {@link Actor} DTOs into normalized {@link OneFeedActor}s
     *
     * @return a {@link ActorTransformer} capable of transforming this provider's
     * {@link Actor} DTO into normalized {@link OneFeedActor}
     */
    public abstract ActorTransformer<A, OneFeedActor> getActorNormalizer();

    /**
     * Fetches the authors of {@code this} content.
     * @return a {@link Flux} that emits the authors of {@code this} content
     */
    public abstract Mono<A> fetchAuthor(String authorId);

    /**
     * Gets info about this provider's source platform.
     * @return info about the source platform of this provider's content
     */
    public abstract Platform getPlatform();
}
