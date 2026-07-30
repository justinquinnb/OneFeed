package dev.jqb.onefeed.core.actor;

/**
 * A means of transforming {@link Actor} into another type, or simply manipulating it
 * @param <In> the type of {@link Actor} to transform
 * @param <Out> the type of {@link Actor} to produce
 */
public interface ActorTransformer<In extends Actor, Out extends Actor> {

    /**
     * Transforms the given {@link In} into {@link Out}
     * @param actor the actor to transform
     * @return the provided {@code actor}, transformed into {@link Out}
     */
    Out transform(In actor);
}
