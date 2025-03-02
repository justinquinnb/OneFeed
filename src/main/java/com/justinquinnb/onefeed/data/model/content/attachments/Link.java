package com.justinquinnb.onefeed.data.model.content.attachments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.justinquinnb.onefeed.data.model.content.Content;
import org.springframework.lang.Nullable;

/**
 * A link to accompany a piece of {@link Content}.
 */
public class Link {
    /**
     * The URL of {@code this} {@code Link}.
     */
    String url;

    /**
     * Optional tooltip for the {@link #url}.
     */
    @Nullable
    String tooltip;

    /**
     * Instantiates a {@link Link} with just the desired URL for it.
     *
     * @param url the desired URL of the instantiated {@link Link}
     */
    public Link(String url) {
        this.url = url;
    }

    /**
     * Instantiates a {@link Link} with just the desired URL for it.
     *
     * @param url the desired URL of the instantiated {@code Link}
     * @param tooltip tooltip text to accompany the instantiated {@code Link}'s URL
     */
    @JsonCreator
    public Link(
            @JsonProperty("url") String url,
            @JsonProperty("tooltip") String tooltip) {
        this.url = url;
        this.tooltip = tooltip;
    }

    /**
     * Gets the URL of {@code this} {@code Link}.
     *
     * @return the URL of {@code this} {@code Link}
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
        return "Link@" + this.hashCode() +
                "{url=\"" + this.url + ", " +
                "\"tooltip=" + (this.tooltip == null ? "null" : "\"" + this.tooltip + "\"") +
                "}";
    }
}