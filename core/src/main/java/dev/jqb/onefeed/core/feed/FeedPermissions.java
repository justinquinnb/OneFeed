package dev.jqb.onefeed.core.feed;

import dev.jqb.onefeed.core.content.Content;

/**
 * Describes the operations permitted by a specific {@link Feed} instance, contingent on the
 * permissions granted to the feed's provider by the platform's API. This is separate from the
 * technical capacity of a Feed to have {@link Content} written to or read from it, which is
 * contingent on the platform's supported operations.
 *
 * Importantly, these permissions may change over time as providers gain or lose access to certain
 * operations on a feed.
 */
public enum FeedPermissions {
    /**
     * The feed instance only permits reading its content.
     */
    READ_ONLY(true, false),

    /**
     * The feed instance only permits writing new content to it.
     */
    WRITE_ONLY(false, true),

    /**
     * The feed instance permits both reading its content and writing new content to it.
     */
    READ_WRITE(true, true),

    /**
     * The feed instance has no permissions whatsoever.
     */
    NONE(false, false);

    private final boolean canRead;
    private final boolean canWrite;

    FeedPermissions(boolean canRead, boolean canWrite) {
        this.canRead = canRead;
        this.canWrite = canWrite;
    }

    /**
     * Whether the feed's content can be read
     */
    public boolean canRead() {
        return canRead;
    }

    /**
     * Whether the feed can have new content written to it
     */
    public boolean canWrite() {
        return canWrite;
    }
}
