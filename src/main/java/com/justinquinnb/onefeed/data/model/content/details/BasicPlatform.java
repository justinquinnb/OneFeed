package com.justinquinnb.onefeed.data.model.content.details;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A basic implementation of information about the place that a piece of digital media is hosted on or published
 * to.
 */
public class BasicPlatform implements Platform {
    /**
     * The URL of the platform's homepage, such as {@code https://instagram.com} for Instagram.
     */
    private final String homepageUrl;

    /**
     * The common name of the platform, such as "Instagram."
     */
    private final String name;

    /**
     * The prefix used to mark a username on the platform, such as "@" for Instagram or "u/" for Reddit.
     */
    private final String usernamePrefix;

    @JsonCreator
    public BasicPlatform(
            @JsonProperty("homepageUrl") String homepageUrl,
            @JsonProperty("name") String name,
            @JsonProperty("usernamePrefix") String usernamePrefix
    ) {
        this.homepageUrl = homepageUrl;
        this.name = name;
        this.usernamePrefix = usernamePrefix;
    }

    /**
     * Gets the homepage URL of the {@code BasicPlatform} where the parent digital media was published.
     *
     * @return the homepage URL of the {@code BasicPlatform} where the parent digital media was published.
     */
    public String getHomepageUrl() {
        return homepageUrl;
    }

    /**
     * Gets the name of the {@code BasicPlatform} where the digital media was published.
     *
     * @return the name of the {@code BasicPlatform} where the parent digital media was published.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the username prefix (like Reddit's "u/" or Insta's "@") of the {@code BasicPlatform} where the parent
     * digital media was published.
     *
     * @return the username prefix (like Reddit's "u/" or Insta's "@") of the {@code BasicPlatform} where the parent
     * digital media was published.
     */
    public String getUsernamePrefix() {
        return usernamePrefix;
    }

    public String toString() {
        return "BasicPlatform@" + this.hashCode() +
                "{name=\"" + this.name +
                "\", homepageUrl=\"" + this.homepageUrl +
                "\", usernamePrefix=\"" + this.usernamePrefix + "\"}";
    }
}
