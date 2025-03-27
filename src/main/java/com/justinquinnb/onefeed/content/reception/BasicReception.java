package com.justinquinnb.onefeed.content.reception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.justinquinnb.onefeed.JsonToString;
import org.springframework.lang.Nullable;

import java.util.Arrays;

/**
 * A piece of digital media's public reception, whether that be performance metrics, views, comments, or the like.
 */
public class BasicReception implements Reception{
    /**
     * Labeled, numeric values about a piece of digital media.
     */
    @Nullable
    private final Statistic[] stats;

    /**
     * Comments on a piece of digital media.
     */
    @Nullable
    private final Comment[] comments;

    /**
     * Instantiates a {@link BasicReception} with both {@link Statistic}s and {@link Comment}s.
     *
     * @param stats labeled, numeric values about the piece of media
     * @param comments comments on the piece of media
     */
    @JsonCreator
    public BasicReception(
            @Nullable @JsonProperty("stats") Statistic[] stats,
            @Nullable @JsonProperty("comments") Comment[] comments) {
        this.stats = stats;
        this.comments = comments;
    }

    /**
     * Instantiates a {@link BasicReception} with just {@link Statistic}s.
     *
     * @param stats labeled, numeric values about the piece of media
     */
    public BasicReception(Statistic[] stats) {
        this.stats = stats;
        this.comments = new Comment[0];
    }

    /**
     * Instantiates a {@link BasicReception} with just {@link Comment}s.
     *
     * @param comments comments on the piece of media
     */
    public BasicReception(Comment[] comments) {
        this.stats = new Statistic[0];
        this.comments = comments;
    }

    /**
     * Gets the labeled statistics associated with {@code this} instance of audience {@link Reception}.
     *
     * @return {@code this} {@link BasicReception}'s {@link #stats}
     */
    public Statistic[] getStats() {
        return Arrays.copyOf(stats, stats.length);
    }

    /**
     * Gets the comments associated with {@code this} instance of audience {@link Reception}.
     *
     * @return {@code this} {@link BasicReception}'s {@link #comments}
     */
    public Comment[] getComments() {
        return Arrays.copyOf(comments, comments.length);
    }

    @Override
    public String toString() {
        return JsonToString.of(this);
    }
}