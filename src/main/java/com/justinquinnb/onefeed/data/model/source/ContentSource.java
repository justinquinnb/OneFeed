package com.justinquinnb.onefeed.data.model.source;

import com.justinquinnb.onefeed.data.model.content.Content;

/**
 * Outlines the functionalities of a valid data source.
 */
public interface ContentSource {
    /**
     * Checks if content can be retrieved from the desired source.
     *
     * @return {@code true} if the content is accessible, else {@code false}.
     */
    boolean isAvailable();

    /**
     * Gets the {@code count} latest units of content from the source.
     *
     * @param count the amount of content to retrieve
     *
     * @return {@code count}-many units of content from the source.
     */
    Content[] getLatestContent(int count);
}