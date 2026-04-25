package dev.jqb.onefeed.api.model.data;

import lombok.Getter;

/**
 * A single source/stream of content, defined by an {@link Author} of the content and the
 * {@link Platform} that content is hosted on/comes from/has been posted to.
 */
@Getter
public class FeedInfo {

    /**
     * The platform that the content is hosted on/comes from/has been posted to
     */
    private final Platform platform;

    /**
     * The author of the content
     */
    private final Author author;

    /**
     * Creates a new {@link FeedInfo} object with the given {@code author} and {@code platform}.
     * @param platform the platform the content is hosted on/comes from/has been posted to
     * @param author the author of the content
     */
    public FeedInfo(Platform platform, Author author) {
        this.platform = platform;
        this.author = author;
    }
}
