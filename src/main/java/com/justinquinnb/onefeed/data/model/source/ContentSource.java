package com.justinquinnb.onefeed.data.model.source;

import com.justinquinnb.onefeed.data.model.content.BasicContent;
import com.justinquinnb.onefeed.data.model.content.Content;
import com.justinquinnb.onefeed.data.model.content.details.ContentSourceId;
import com.justinquinnb.onefeed.data.model.content.details.Platform;

import java.time.Instant;

/**
 * Outlines the functionalities of a valid source of content.
 */
public abstract class ContentSource {
    /**
     * The unique {@code String} used to identify a specific {@code ContentSource} instance when interacting with the
     * OneFeed API. Facilitates the tracking of multiple {@code ContentSource} instances, a situation possible when
     * content is desired from multiple profiles on the same platform, for example.
     */
    public final ContentSourceId SOURCE_ID;

    /**
     * Information about the {@link Platform} the {@link ContentSource} is pulling data from.
     */
    protected Platform PLATFORM_INFO;

    public ContentSource(ContentSourceId sourceId, Platform platform) {
        SOURCE_ID = sourceId;
        PLATFORM_INFO = platform;
    }

    /**
     * Checks if content can be retrieved from the desired source.
     *
     * @return {@code true} if the content is accessible, else {@code false}.
     */
    public abstract boolean isAvailable();

    /**
     * Gets the {@code count} latest units of {@link BasicContent} from the source.
     *
     * @param count the amount of {@code Content} to retrieve
     *
     * @return at most {@code count}-many units of {@code Content} from the source. If less than {@code count}-many
     * units of {@code Content} can be retrieved, then all that could be retrieved is returned.
     */
    public abstract Content[] getLatestContent(int count);

    /**
     * Gets the {@code count} latest units of {@link BasicContent} from the source between the specified {@code betweenTimes}.
     *
     * @param count the amount of {@code Content} to retrieve
     * @param betweenTimes the {@link Instant}s that {@code Content} should fall between, element 0 indicating the start
     *                     and element 1 indicating the end, inclusive
     *
     * @return at most {@code count}-many units of {@code Content} between the specified {@code betweenTimes} from the
     * source. If less than {@code count}-many units of {@code Content} can be retrieved, then all that could be retrieved
     * is returned.
     */
    public abstract Content[] getLatestContent(int count, Instant[] betweenTimes);

    /**
     * Gets information about the {@link Platform} {@code this} {@link ContentSource} gets its data from.
     *
     * @return information about {@code this} {@code ContentSource}'s {@code Platform}
     */
    public Platform getPlatformInfo() {
        return PLATFORM_INFO;
    }

    /**
     * Gets the unique {@link ContentSourceId} identifying the {@code ContentSource} instance.
     *
     * @return the unique {@link ContentSourceId} identifying the {@code ContentSource} instance
     */
    public ContentSourceId getSourceId() {
        return SOURCE_ID;
    }

    /**
     * Checks if the {@code toCheck} {@link Instant} falls between {@code after} and {@code before}, inclusive.
     *
     * @param toCheck the {@code Instant} to check for inclusive belonging in the range ({@code after}, {@code before})
     * @param after the minimum time a "valid" {@code Instant} can be equal to
     * @param before the maximum time a "valid" {@code Instant} can be equal to
     *
     * @return {@code true} if {@code toCheck} exists in the range ({@code after}, {@code before}), else {@code false}.
     */
    public boolean isInclusiveBetween(Instant toCheck, Instant after, Instant before) {
        boolean isBefore = toCheck.isBefore(before) || toCheck.equals(before);
        boolean isAfter = toCheck.isAfter(after) || toCheck.equals(after);
        return isBefore && isAfter;
    }
}