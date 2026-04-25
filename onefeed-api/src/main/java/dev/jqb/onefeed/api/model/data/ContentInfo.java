package dev.jqb.onefeed.api.model.data;

import java.time.Instant;

/**
 * The minimum required data for of a piece of content. This is an interface so
 * DTOs from content providers do not have to implement specific fields when
 * deserializing API responses.
 */
public interface ContentInfo {

    /**
     * Gets time at which the content was published.
     * @return the time at which the content was published
     */
    Instant getPublished();

    /**
     * Gets information about the source of the content.
     * @return info about the source of the content
     */
    SourceInfo getSource();
}
