package dev.jqb.onefeed.api.model.data.content;

import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The minimum required data for of a piece of content. This is an interface so
 * DTOs from content providers do not have to implement specific fields when
 * deserializing API responses.
 */
@Getter
@Setter
@NoArgsConstructor
public abstract class Content {
    /**
     * Gets time at which the content was published.
     * @return the time at which the content was published
     */
    private Instant published;

    /**
     * Gets information about the source of the content.
     * @return info about the source of the content
     */
    private SourceInfo source;
}
