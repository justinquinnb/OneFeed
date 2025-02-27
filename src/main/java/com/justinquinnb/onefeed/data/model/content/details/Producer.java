package com.justinquinnb.onefeed.data.model.content.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.justinquinnb.onefeed.data.model.content.Content;

/**
 * Information about the entity producing the {@link Content}.
 */
public class Producer {
    /**
     * A direct link to the {@code Producer}'s profile page on the {@link Platform} their {@link Content} is being
     * hosted on.
     */
    private final String profilePageUrl;

    /**
     * The URL of the {@code Producer}'s profile picture, avatar, or platform equivalent.
     */
    private final String profilePicUrl;

    /**
     * The {@code Producer}'s first (pen)name (or platform equivalent).
     */
    private final String firstName;

    /**
     * The {@code Producer}'s last (pen)name / sur-(pen)name (or platform equivalent).
     */
    private final String lastName;

    /**
     * The {@code Producer}'s unique identifier on the platform.
     */
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

    public String toString() {
        return "Producer@" + this.hashCode() +
                "{firstName=" + this.firstName +
                ", lastName=" + this.lastName +
                ", username=" + this.username +
                ", profilePageUrl=" + this.profilePageUrl +
                ", profilePicUrl=" + this.profilePicUrl + "}";
    }
}
