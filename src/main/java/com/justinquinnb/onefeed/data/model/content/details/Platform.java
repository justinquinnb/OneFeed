package com.justinquinnb.onefeed.data.model.content.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.justinquinnb.onefeed.data.model.content.Content;

/**
 * Information about the place that a piece of {@link Content} is hosted on or published to.
 */
public class Platform {
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

    public Platform(
            @JsonProperty("homepageUrl") String homepageUrl,
            @JsonProperty("name") String name,
            @JsonProperty("usernamePrefix") String usernamePrefix
    ) {
        this.homepageUrl = homepageUrl;
        this.name = name;
        this.usernamePrefix = usernamePrefix;
    }

    /**
     * Gets the homepage URL of the {@code Platform} where the parent {@code Content} was published.
     *
     * @return the homepage URL of the {@code Platform} where the parent {@code Content} was published.
     */
    public String getHomepageUrl() {
        return homepageUrl;
    }

    /**
     * Gets the name of the {@code Platform} where the parent {@code Content} was published.
     *
     * @return the name of the {@code Platform} where the parent {@code Content} was published.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the username prefix (like Reddit's "u/" or Insta's "@") of the {@code Platform} where the parent
     * {@code Content} was published.
     *
     * @return the username prefix (like Reddit's "u/" or Insta's "@") of the {@code Platform} where the parent
     * {@code Content} was published.
     */
    public String getUsernamePrefix() {
        return usernamePrefix;
    }

    public String toString() {
        return "Platform@" + this.hashCode() +
                "{name=" + this.name +
                ", homepageUrl=" + this.homepageUrl +
                ", usernamePrefix=" + this.usernamePrefix + "}";
    }
}
