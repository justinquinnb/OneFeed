package dev.jqb.onefeed.core.content;

/**
 * A means of transforming input {@link Content} into another type, or simply manipulating it
 * @param <In> the type of {@link Content} to transform
 * @param <Out> the type of {@link Content} to produce
 */
public interface ContentTransformer<In extends Content, Out extends Content> {

    /**
     * Transforms the given {@link In} into {@link Out}
     * @param content the piece of {@link In} to transform
     * @return the provided {@code content}, transformed into {@link Out}
     */
    Out transform(In content);
}
