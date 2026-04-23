package dev.jqb.onefeed.content.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Information about a unit of content's source
 */
@Getter
public class SourceInfo extends FeedInfo {

    /**
     * The URL of the content on its feed's platform
     */
    private final String sourceUrl;

    public SourceInfo(Platform platform, Author author, String sourceUrl) {
        super(platform, author);
        this.sourceUrl = sourceUrl;
    }
}
