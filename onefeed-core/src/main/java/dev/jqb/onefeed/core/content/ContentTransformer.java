package dev.jqb.onefeed.core.content;

import dev.jqb.onefeed.core.actor.Actor;

/**
 * A means of transforming input {@link Content} into another type, or simply manipulating it
 * @param <In> the type of {@link Content} to transform
 * @param <Out> the type of {@link Content} to produce
 */
public interface ContentTransformer<In extends Content<? extends Actor>,
    Out extends Content<? extends Actor>>
{

    /**
     * Transforms the given {@link In} into {@link Out}
     * @param content the piece of {@link In} to transform
     * @return the provided {@code content}, transformed into {@link Out}
     */
    Out transform(In content);
}
