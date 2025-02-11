package com.justinquinnb.onefeed.data.model.source;

import com.justinquinnb.onefeed.data.model.content.Content;
import com.justinquinnb.onefeed.data.model.content.details.SourceInfo;

import java.time.Instant;

/**
 * Outlines the functionalities of a valid data source.
 */
public interface ContentSource {
    String getId();

    /**
     * Checks if content can be retrieved from the desired source.
     *
     * @return {@code true} if the content is accessible, else {@code false}.
     */
    boolean isAvailable();

    /**
     * Gets the {@code count} latest units of {@link Content} from the source.
     *
     * @param count the amount of {@code Content} to retrieve
     *
     * @return at most {@code count}-many units of {@code Content} from the source. If less than {@code count}-many
     * units of {@code Content} can be retrieved, then all that could be retrieved is returned.
     */
    Content[] getLatestContent(int count);

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
    Content[] getLatestContent(int count, Instant[] betweenTimes);

    /**
     * Gets relevant source information as {@link com.justinquinnb.onefeed.data.model.content.details.SourceInfo}.
     *
     * @return information about {@code this} {@code ContentSource}.
     */
    SourceInfo getSourceInfo();

    /**
     * Checks if the {@code toCheck} {@link Instant} falls between {@code after} and {@code before}, inclusive.
     *
     * @param toCheck the {@code Instant} to check for inclusive belonging in the range ({@code after}, {@code before})
     * @param after the minimum time a "valid" {@code Instant} can be equal to
     * @param before the maximum time a "valid" {@code Instant} can be equal to
     *
     * @return {@code true} if {@code toCheck} exists in the range ({@code after}, {@code before}), else {@code false}.
     */
    default boolean isInclusiveBetween(Instant toCheck, Instant after, Instant before) {
        boolean isBefore = toCheck.isBefore(before) || toCheck.equals(before);
        boolean isAfter = toCheck.isAfter(after) || toCheck.equals(after);
        return isBefore && isAfter;
    }
}