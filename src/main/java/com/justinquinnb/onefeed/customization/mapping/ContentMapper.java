package com.justinquinnb.onefeed.customization.mapping;


import com.justinquinnb.onefeed.content.OneFeedContent;
import com.justinquinnb.onefeed.content.RawContent;

/**
 * Maps a piece of {@link RawContent} to a {@link OneFeedContent} type.
 * @param <T> some type of {@code RawContent} that {@code this} {@code ContentMapper} implementation can understand
 * @param <U> the type of {@link OneFeedContent} {@code this} will map to
 */
public interface ContentMapper<T extends RawContent, U extends OneFeedContent> {
    /**
     * Maps the provided {@code rawContent} into a {@link OneFeedContent} type.
     *
     * @param rawContent the {@link RawContent} type to map
     * @return the {@code RawContent} object mapped to a {@code OneFeedContent} type
     */
    public U mapContent(T rawContent);

    /**
     * Maps all the provided {@code rawContent} into objects of {@link OneFeedContent} type.
     *
     * @param rawContent the {@link RawContent}-type objects to map
     * @return the {@code RawContent} objects mapped to {@code OneFeedContent} objects
     */
    public U[] mapContent(T[] rawContent);
}