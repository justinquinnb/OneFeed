package dev.jqb.onefeed.content.model;

import lombok.Getter;
import lombok.Setter;

/**
 * A single source/stream of content, defined by an {@link Author} of the content and the
 * {@link Platform} that content is hosted on/comes from/has been posted to.
 */
@Getter @Setter
public class ContentSource {

    /**
     * The author of the content
     */
    private Author author;

    /**
     * The platform that the content is hosted on/comes from/has been posted to
     */
    private Platform platform;
}
