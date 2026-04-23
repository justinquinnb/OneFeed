package dev.jqb.onefeed.content.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Information about a piece of content's author
 */
@Getter @Setter
public class Author {

    /**
     * The username of the author on the content's platform, devoid of any platform-specific
     * prefixes like {@code @}
     */
    private String username;

    /**
     * The URL of the author's feed on the content's platform
     */
    private String feedUrl;
}
