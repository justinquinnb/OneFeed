package dev.jqb.onefeed.core.actor;

/**
 * A means of transforming {@link NormalizedActor} into another type, or simply
 * manipulating it
 * @param <In> the type of {@link NormalizedActor} to transform
 * @param <Out> the type of {@link NormalizedActor} to produce
 */
public interface ActorTransformer<In extends NormalizedActor, Out extends NormalizedActor> {

    /**
     * Transforms the given {@link In} into {@link Out}
     * @param author the normalized author to transform
     * @return the provided {@code author}, transformed into {@link Out}
     */
    Out transform(In author);
}
