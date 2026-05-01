package dev.jqb.onefeed.api.feed;

import lombok.Getter;
import lombok.Setter;

/**
 * Information about a unit of content's source
 *
 * @param <T> the type of {@link Author} info contained here
 */
@Getter
@Setter
public class SourceInfo<T extends Author> extends FeedInfo<T> {

    /**
     * The URL of the content resource on its feed's platform
     */
    private String sourceUrl;

    public SourceInfo(Platform platform, T author, String sourceUrl) {
        super(platform, author);
        this.sourceUrl = sourceUrl;
    }
}
