package com.justinquinnb.onefeed.data.model.content.attachments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.justinquinnb.onefeed.data.model.content.Content;
import org.springframework.lang.Nullable;

/**
 * A photo to accompany a piece of {@link Content}.
 */
public class Visual {
    /**
     * The URL of the visual.
     */
    private String url = null;

    /**
     * Optional alt text for the visual provided by {@code this} {@code Visual}'s {@link #url}.
     */
    @Nullable
    private String altText = null;

    /**
     * Instantiates a {@link Visual} with just a URL to the visual itself.
     *
     * @param url a URL to the {@code Visual} for the end user to display
     */
    public Visual(String url) {
        this.url = url;
    }

    /**
     * Instantiates a {@link Visual} with a URL to the visual and alt text.
     *
     * @param url a URL to the {@code Visual} for the end user to display
     * @param altText alt text to accompany the visual
     */
    @JsonCreator
    public Visual(
            @JsonProperty("url") String url,
            @JsonProperty("altText") String altText) {
        this.url = url;
        this.altText = altText;
    }

    /**
     * Gets the URL of {@code this} {@link Visual}.
     *
     * @return the URL of {@code this} {@code Visual}
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets the alt text of {@code this} {@link Visual}.
     *
     * @return the alt text of {@code this} {@code Visual}
     */
    public String getAltText() {
        return altText;
    }

    public String toString() {
        return "Visual@" + this.hashCode() +
                "{url=\"" + this.url + ", " +
                "altText=" + (this.altText == null ? "null" : "\"" + this.altText + "\"") +
                "}";
    }
}