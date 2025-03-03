package com.justinquinnb.onefeed.data.model.content.details;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.justinquinnb.onefeed.data.model.content.BasicContent;

/**
 * A basic implementation of information about the entity producing the {@link BasicContent}.
 */
public class BasicProducer implements Producer {
    /**
     * A direct link to the {@code BasicProducer}'s profile page on the {@link BasicPlatform} their {@link BasicContent}
     * is being hosted on.
     */
    private final String profilePageUrl;

    /**
     * The URL of the {@code BasicProducer}'s profile picture, avatar, or platform equivalent.
     */
    private final String profilePicUrl;

    /**
     * The {@code BasicProducer}'s first (pen)name (or platform equivalent).
     */
    private final String firstName;

    /**
     * The {@code BasicProducer}'s last (pen)name / sur-(pen)name (or platform equivalent).
     */
    private final String lastName;

    /**
     * The {@code BasicProducer}'s unique identifier on the platform.
     */
    private final String username;

    @JsonCreator
    public BasicProducer(
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
     * Gets the URL of {@code this} {@code Content} {@code BasicProducer}'s profile page on the platform where its parent
     * content was published.
     *
     * @return the URL of {@code this} {@code Content} {@code BasicProducer}'s profile page on the platform where its parent
     * content was published.
     */
    public String getProfilePageUrl() {
        return profilePageUrl;
    }

    /**
     * Gets the URL of {@code this} {@code Content} {@code BasicProducer}'s profile picture on the platform where its parent
     * content was published.
     *
     * @return the URL of {@code this} {@code Content} {@code BasicProducer}'s profile picture on the platform where its
     * parent content was published.
     */
    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    /**
     * Gets the first name (or platform equivalent) of {@code this} {@code Content}'s {@code BasicProducer}.
     *
     * @return the first name (or platform equivalent) of {@code this} {@code Content}'s {@code BasicProducer}.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the last name (or platform equivalent) of {@code this} {@code Content}'s {@code BasicProducer}.
     *
     * @return the last name (or platform equivalent) of {@code this} {@code Content}'s {@code BasicProducer}.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the username (or platform equivalent) of {@code this} {@code Content}'s {@code BasicProducer}.
     *
     * @return the username (or platform equivalent) of {@code this} {@code Content}'s {@code BasicProducer}.
     */
    public String getUsername() {
        return username;
    }

    public String toString() {
        return "BasicProducer@" + this.hashCode() +
                "{firstName=\"" + this.firstName +
                "\", lastName=\"" + this.lastName +
                "\", username=\"" + this.username +
                "\", profilePageUrl=\"" + this.profilePageUrl +
                "\", profilePicUrl=\"" + this.profilePicUrl + "\"}";
    }
}
