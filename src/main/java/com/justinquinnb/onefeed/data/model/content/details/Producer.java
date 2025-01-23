package com.justinquinnb.onefeed.data.model.content.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.justinquinnb.onefeed.data.model.content.Content;

/**
 * Information about the entity producing the {@link Content}.
 */
public class Producer {
    private final String profilePageUrl;
    private final String profilePicUrl;
    private final String firstName;
    private final String lastName;
    private final String username;

    public Producer(
            @JsonProperty("profilePageUrl") String profilePageUrl,
            @JsonProperty("profilePicUrl") String profilePicUrl,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("username") String username) {
        this.profilePageUrl = profilePageUrl;
        this.profilePicUrl = profilePicUrl;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    /**
     * Gets the URL of {@code this} {@code Content} {@code Producer}'s profile page on the platform where its parent
     * content was published.
     *
     * @return the URL of {@code this} {@code Content} {@code Producer}'s profile page on the platform where its parent
     * content was published.
     */
    public String getProfilePageUrl() {
        return profilePageUrl;
    }

    /**
     * Gets the URL of {@code this} {@code Content} {@code Producer}'s profile picture on the platform where its parent
     * content was published.
     *
     * @return the URL of {@code this} {@code Content} {@code Producer}'s profile picture on the platform where its
     * parent content was published.
     */
    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    /**
     * Gets the first name (or platform equivalent) of {@code this} {@code Content}'s {@code Producer}.
     *
     * @return the first name (or platform equivalent) of {@code this} {@code Content}'s {@code Producer}.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the last name (or platform equivalent) of {@code this} {@code Content}'s {@code Producer}.
     *
     * @return the last name (or platform equivalent) of {@code this} {@code Content}'s {@code Producer}.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the username (or platform equivalent) of {@code this} {@code Content}'s {@code Producer}.
     *
     * @return the username (or platform equivalent) of {@code this} {@code Content}'s {@code Producer}.
     */
    public String getUsername() {
        return username;
    }
}
