package com.justinquinnb.onefeed.data.model.content.details;

/**
 * Information about the platform the {@link com.justinquinnb.onefeed.data.model.content.Content} is derived from.
 */
public class Platform {
    private final String homepageUrl;
    private final String name;
    private final String usernamePrefix;

    public Platform(String homepageUrl, String name, String usernamePrefix) {
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
}
