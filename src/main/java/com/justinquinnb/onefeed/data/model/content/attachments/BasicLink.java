package com.justinquinnb.onefeed.data.model.content.attachments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.justinquinnb.onefeed.data.model.content.BasicContent;
import org.springframework.lang.Nullable;

/**
 * A basic implementation for a link that accompanies a piece of {@link BasicContent}.
 */
public class BasicLink implements Link {
    /**
     * The URL of {@code this} {@code BasicLink}.
     */
    String url;

    /**
     * Optional tooltip for the {@link #url}.
     */
    @Nullable
    String tooltip;

    /**
     * Instantiates a {@link BasicLink} with just the desired URL for it.
     *
     * @param url the desired URL of the instantiated {@link BasicLink}
     */
    public BasicLink(String url) {
        this.url = url;
    }

    /**
     * Instantiates a {@link BasicLink} with just the desired URL for it.
     *
     * @param url the desired URL of the instantiated {@code BasicLink}
     * @param tooltip tooltip text to accompany the instantiated {@code BasicLink}'s URL
     */
    @JsonCreator
    public BasicLink(
            @JsonProperty("url") String url,
            @JsonProperty("tooltip") String tooltip) {
        this.url = url;
        this.tooltip = tooltip;
    }

    /**
     * Gets the URL of {@code this} {@code BasicLink}.
     *
     * @return the URL of {@code this} {@code BasicLink}
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets the tooltip for the {@link #url}, if one was provided.
     *
     * @return the tooltip for the {@link #url}, if one was provided
     */
    public String getTooltip() {
        return tooltip;
    }

    public String toString() {
        return "BasicLink@" + this.hashCode() +
                "{url=\"" + this.url + ", " +
                "\"tooltip=" + (this.tooltip == null ? "null" : "\"" + this.tooltip + "\"") +
                "}";
    }
}