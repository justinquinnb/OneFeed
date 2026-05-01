package dev.jqb.onefeed.api.content;

import dev.jqb.onefeed.api.feed.Author;
import dev.jqb.onefeed.api.feed.SourceInfo;
import java.time.Instant;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public abstract class Content {

    /**
     * Gets information about the source of the content.
     * @return info about the source of the content
     */
    protected SourceInfo<? extends Author> source;

    /**
     * Gets time at which the content was published.
     * @return the time at which the content was published
     */
    protected Instant published;
}
