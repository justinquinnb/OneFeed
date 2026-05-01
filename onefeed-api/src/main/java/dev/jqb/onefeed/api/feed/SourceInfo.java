package dev.jqb.onefeed.api.feed;

import dev.jqb.onefeed.api.content.Content;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Information about a unit of {@link Content}'s source
 *
 * @param <T> the type of {@link Author} info contained here
 */
@Getter
@Setter
@NoArgsConstructor
public class SourceInfo<T extends Author> extends FeedInfo<T> {

    /**
     * The URL of the content resource on its feed's platform
     */
    private String sourceUrl;

    /**
     * Creates a new {@code SourceInfo} instance, indicating the source of a piece of
     * {@link Content}.
     *
     * @param platform the platform the content is hosted on/comes from/has been posted to
     * @param author the author of the content
     * @param sourceUrl the URL for the specific piece of content
     */
    public SourceInfo(Platform platform, T author, String sourceUrl) {
        super(platform, author);
        this.sourceUrl = sourceUrl;
    }
}
