package dev.jqb.onefeed.api.feed;

import lombok.Getter;
import lombok.Setter;

/**
 * A single source/stream of content, defined by an {@link Author} of the content and the
 * {@link Platform} that content is hosted on/comes from/has been posted to.
 *
 * @param <T> the type of {@link Author} info contained here
 */
@Getter
@Setter
public class FeedInfo<T extends Author> {

    /**
     * The platform that the content is hosted on/comes from/has been posted to
     */
    private Platform platform;

    /**
     * The author of the content
     */
    private T author;

    /**
     * Creates a new {@link FeedInfo} object with the given {@code author} and {@code platform}.
     * @param platform the platform the content is hosted on/comes from/has been posted to
     * @param author the author of the content
     */
    public FeedInfo(Platform platform, T author) {
        this.platform = platform;
        this.author = author;
    }
}
