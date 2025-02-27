package com.justinquinnb.onefeed.data.model.token;

import com.justinquinnb.onefeed.data.model.source.ContentSource;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

/**
 * Specifies a Token entry on a database or other storage structure.
 */
public class TokenEntry {
    /**
     * The {@link ContentSource#SOURCE_ID} of the {@link ContentSource} using {@code this} {@code TokenEntry}'s
     * {@link #accessToken}.
     */
    private final String contentSourceId;

    /**
     * The access token that the {@link ContentSource} with ID {@link #contentSourceId} uses to retrieve its content.
     */
    private final String accessToken;

    /**
     * The {@link Instant} that {@code this} {@code TokenEntry}'s {@link #accessToken} was last updated.
     */
    private final Instant lastUpdated;

    /**
     * The {@link Instant} that {@code this} {@code TokenEntry}'s {@link #accessToken} expires, if it does. If it
     * doesn't, this will be an empty {@link Optional}.
     */
    private final Optional<Instant> expires;

    /**
     * Creates a {@code accessTokenEntry} containing the access token for the Content Source with ID {@code contentSourceId}.
     * 
     * @param contentSourceId the ID of the Content Source instance using the access token
     * @param accessToken the access token itself
     */
    public TokenEntry(String contentSourceId, String accessToken) {
        this.contentSourceId = contentSourceId;
        this.accessToken = accessToken;
        this.lastUpdated = Instant.now();
        this.expires = Optional.empty();
    }

    /**
     * Creates a {@code accessTokenEntry} containing the access token for the Content Source with ID {@code contentSourceId}.
     *
     * @param contentSourceId the ID of the Content Source instance using the access token
     * @param accessToken the access token itself
     * @param expires the {@link Instant} the access token expires
     */
    public TokenEntry(String contentSourceId, String accessToken, Instant expires) {
        this.contentSourceId = contentSourceId;
        this.accessToken = accessToken;
        this.lastUpdated = Instant.now();
        this.expires = Optional.ofNullable(expires);
    }

    /**
     * Creates a {@code TokenEntry} containing the access token for the Content Source with ID {@code contentSourceId}.
     *
     * @param contentSourceId the ID of the Content Source instance using the accessToken
     * @param accessToken the access token itself
     * @param validFor the amount of time until the provided {@code accessToken} is valid for (i.e. time until
     *                 expiration)
     */
    public TokenEntry(String contentSourceId, String accessToken, Duration validFor) {
        this(contentSourceId, accessToken, Instant.now().plus(validFor));
    }

    /**
     * Gets the ID of the Content Source instance using the access token contained in {@code this} entry.
     *
     * @return the ID of the Content Source instance affiliated with {@code this} entry's {@code accessToken}
     */
    public String getContentSourceId() {
        return contentSourceId;
    }

    /**
     * Gets the access token contained in {@code this} entry.
     *
     * @return the access token contained in {@code this} entry
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Gets the {@code Instant} that the {@code accessToken} contained in {@code this} entry was lastUpdated.
     *
     * @return the {@code Instant} that the {@code accessToken} contained in {@code this} entry was lastUpdated
     */
    public Instant getCreationTime() {
        return lastUpdated;
    }

    /**
     * Gets the {@code Instant} that the {@code accessToken} contained in {@code this} entry will expire, if it does
     * expire.
     *
     * @return the {@code Instant} that the {@code accessToken} contained in {@code this} entry will expire, if it does
     * expire, otherwise {@code Optional.empty()}
     */
    public Optional<Instant> getExpirationTime() {
        return expires;
    }

    /**
     * Checks whether the {@code accessToken} contained in {@code this} entry has expired, if it does expire.
     *
     * @return {@code true} if the current {@code Instant} ({@code Instant.now()} exceeds the {@code this} entry's
     * {@code expires} {@code Instant}, if one exists, else {@code false}
     */
    public boolean isExpired() {
        return expires.isPresent() && Instant.now().isAfter(expires.get());
    }

    /**
     * Returns a string representation of {@code this} {@code TokenEntry}, excluding its {@link #accessToken}.
     *
     * @return a string representation of {@code this} {@code TokenEntry}, excluding its {@link #accessToken}
     */
    public String toString() {
        return "TokenEntry@" + this.hashCode() +
                "{contentSourceId=" + this.contentSourceId +
                ", accessToken=HIDDEN" +
                ", lastUpdated=" + this.lastUpdated +
                ", expires=" + this.expires +"}";
    }
}