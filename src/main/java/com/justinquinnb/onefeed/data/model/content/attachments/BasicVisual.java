package com.justinquinnb.onefeed.data.model.content.attachments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.justinquinnb.onefeed.data.model.content.BasicContent;
import org.springframework.lang.Nullable;

/**
 * A basic implementation for a photo that accompanies a piece of {@link BasicContent}.
 */
public class BasicVisual implements Visual {
    /**
     * The URL of the visual.
     */
    private String url = null;

    /**
     * Optional alt text for the visual provided by {@code this} {@code BasicVisual}'s {@link #url}.
     */
    @Nullable
    private String altText = null;

    /**
     * Instantiates a {@link BasicVisual} with just a URL to the visual itself.
     *
     * @param url a URL to the {@code BasicVisual} for the end user to display
     */
    public BasicVisual(String url) {
        this.url = url;
    }

    /**
     * Instantiates a {@link BasicVisual} with a URL to the visual and alt text.
     *
     * @param url a URL to the {@code BasicVisual} for the end user to display
     * @param altText alt text to accompany the visual
     */
    @JsonCreator
    public BasicVisual(
            @JsonProperty("url") String url,
            @JsonProperty("altText") String altText) {
        this.url = url;
        this.altText = altText;
    }

    /**
     * Gets the URL of {@code this} {@link BasicVisual}.
     *
     * @return the URL of {@code this} {@code BasicVisual}
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets the alt text of {@code this} {@link BasicVisual}.
     *
     * @return the alt text of {@code this} {@code BasicVisual}
     */
    public String getAltText() {
        return altText;
    }

    public String toString() {
        return "BasicVisual@" + this.hashCode() +
                "{url=\"" + this.url + ", " +
                "altText=" + (this.altText == null ? "null" : "\"" + this.altText + "\"") +
                "}";
    }
}