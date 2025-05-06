package com.justinquinnb.onefeed.content.details;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.justinquinnb.onefeed.utils.JsonToString;

/**
 * A basic implementation of information about an entity producing digital media.
 */
public class BasicProducer implements Producer {
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

    /**
     * The URL of the {@code BasicProducer}'s profile picture, avatar, or platform equivalent.
     */
    private final String profilePicUrl;

    /**
     * A direct link to the {@code BasicProducer}'s profile page on the {@link BasicPlatform} their digital media
     * is being hosted on.
     */
    private final String profilePageUrl;

    @JsonCreator
    public BasicProducer(
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("username") String username,
            @JsonProperty("profilePicUrl") String profilePicUrl,
            @JsonProperty("profilePageUrl") String profilePageUrl
    ) {
        this.profilePageUrl = profilePageUrl;
        this.profilePicUrl = profilePicUrl;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    /**
     * Gets the URL of {@code this} digital media {@link Producer}'s profile page on the platform where it was
     * published.
     *
     * @return the URL of {@code this} digital media {@link Producer}'s profile page on the platform where it was
     * published.
     */
    public String getProfilePageUrl() {
        return profilePageUrl;
    }

    /**
     * Gets the URL of {@code this} digital media {@link Producer}'s profile picture on the platform where it was
     * published.
     *
     * @return the URL of {@code this} digital media {@link Producer}'s profile picture on the platform where was
     * published.
     */
    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    /**
     * Gets the first name (or platform equivalent) of {@code this} {@link Producer}.
     *
     * @return the first name (or platform equivalent) of {@code this} {@link Producer}.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the last name (or platform equivalent) of {@code this} {@link Producer}.
     *
     * @return the last name (or platform equivalent) of {@code this} {@link Producer}.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the username (or platform equivalent) of {@code this} {@link Producer}.
     *
     * @return the username (or platform equivalent) of {@code this} {@link Producer}.
     */
    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return JsonToString.of(this);
    }
}