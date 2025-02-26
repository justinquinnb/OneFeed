package com.justinquinnb.onefeed.data.model.source;

import com.justinquinnb.onefeed.data.model.content.Content;
import com.justinquinnb.onefeed.data.model.content.details.SourceInfo;

import java.time.Instant;

/**
 * Outlines the functionalities of a valid data source.
 */
public abstract class ContentSource {
    /**
     * The unique {@code String} used to identify a specific {@code ContentSource} when interacting with the OneFeed
     * API.
     */
    private final String SOURCE_ID;

    public ContentSource(String sourceId) {
        SOURCE_ID = sourceId;
    }

    /**
     * Gets {@code this} {@code ContentSource}'s {@link #SOURCE_ID}.
     *
     * @return {@code this} {@code ContentSource}'s {@link #SOURCE_ID}.
     */
    public String getId() {
        return SOURCE_ID;
    };

    /**
     * Checks if content can be retrieved from the desired source.
     *
     * @return {@code true} if the content is accessible, else {@code false}.
     */
    public abstract boolean isAvailable();

    /**
     * Gets the {@code count} latest units of {@link Content} from the source.
     *
     * @param count the amount of {@code Content} to retrieve
     *
     * @return at most {@code count}-many units of {@code Content} from the source. If less than {@code count}-many
     * units of {@code Content} can be retrieved, then all that could be retrieved is returned.
     */
    public abstract Content[] getLatestContent(int count);

    /**
     * Gets the {@code count} latest units of {@link Content} from the source between the specified {@code betweenTimes}.
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
     * Gets relevant source information as {@link com.justinquinnb.onefeed.data.model.content.details.SourceInfo}.
     *
     * @return information about {@code this} {@code ContentSource}.
     */
    public abstract SourceInfo getSourceInfo();

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