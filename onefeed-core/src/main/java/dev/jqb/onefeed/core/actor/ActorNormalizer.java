package dev.jqb.onefeed.core.actor;

/**
 * A means of normalizing {@link PlatformActor}s post-retrieval
 * @param <In> the type of {@link PlatformActor} to normalize
 * @param <Out> the type of {@link NormalizedActor} to produce
 */
public interface ActorNormalizer<In extends PlatformActor, Out extends NormalizedActor>{
    /**
     * Normalizes the given {@link In}.
     * @param author the piece of {@link In} to normalize
     * @return the {@code author}, normalized as {@link Out}
     */
    Out normalize(In author);
}
