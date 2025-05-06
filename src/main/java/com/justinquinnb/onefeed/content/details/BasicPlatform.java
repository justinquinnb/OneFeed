package com.justinquinnb.onefeed.content.details;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.justinquinnb.onefeed.utils.JsonToString;

/**
 * A basic implementation of information about the place that a piece of digital media is hosted on or published
 * to.
 */
public class BasicPlatform implements Platform {
    /**
     * The common name of the platform, such as "Instagram."
     */
    private final String name;

    /**
     * The URL of the platform's homepage, such as {@code https://instagram.com} for Instagram.
     */
    private final String homepageUrl;

    /**
     * The prefix used to mark a username on the platform, such as "@" for Instagram or "u/" for Reddit.
     */
    private final String usernamePrefix;

    @JsonCreator
    public BasicPlatform(
            @JsonProperty("name") String name,
            @JsonProperty("homepageUrl") String homepageUrl,
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

    @Override
    public String toString() {
        return JsonToString.of(this);
    }
}