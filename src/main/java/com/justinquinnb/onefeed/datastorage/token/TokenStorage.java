package com.justinquinnb.onefeed.datastorage.token;

import com.justinquinnb.onefeed.customization.addon.contentsource.AuthorizationCodeOAuth;
import com.justinquinnb.onefeed.customization.addon.contentsource.ContentSource;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

/**
 * Specifies a means of storing API access tokens, whether that be through a database, local file, or any other approach.
 * Specifically required by {@link AuthorizationCodeOAuth}-enabled {@link ContentSource}s.
 */
@Component
public interface TokenStorage {
    /**
     * Adds the {@code tokenEntry} to the storage medium.
     *
     * @param tokenEntry the {@link TokenEntry} to add to the storage medium
     */
    public void addTokenEntryFor(TokenEntry tokenEntry);

    /**
     * Removes the {@link TokenEntry} with Content Source ID {@code contentSourceId} from the storage medium.
     *
     * @param contentSourceId the ID of the {@link AuthorizationCodeOAuth}-enabled {@link ContentSource} whose access
     *                        token is being stored
     *
     * @throws TokenEntryNotFound if a {@code TokenEntry} does not exist with Content Source ID {@code contentSourceId}
     */
    public void removeTokenEntryFor(String contentSourceId) throws TokenEntryNotFound;

    /**
     * Updates the access token for the {@link TokenEntry} associated with Content Source ID {@code contentSourceId}.
     *
     * @param contentSourceId the ID of the {@link AuthorizationCodeOAuth}-enabled {@link ContentSource} whose access
     *                        token is being stored
     * @param newAccessToken the new access token to associate with the Content Source with ID {@code contentSourceId}
     *
     * @throws TokenEntryNotFound if a {@link TokenEntry} does not exist with Content Source ID {@code contentSourceId}
     */
    public void updateTokenEntryFor(String contentSourceId, String newAccessToken) throws TokenEntryNotFound;

    /**
     * Attempts to get the {@link TokenEntry} for the {@link AuthorizationCodeOAuth}-enabled {@link ContentSource}
     * instance with ID {@code contentSourceId}.
     *
     * @param contentSourceId the ID of the {@code AuthorizationCodeOAuth}-enabled {@code ContentSource} instance whose
     *                        {@code TokenEntry} to retrieve
     *
     * @return the {@code TokenEntry} for the {@code AuthorizationCodeOAuth}-enabled {@code ContentSource}
     * instance with ID {@code contentSourceId}
     * @throws TokenEntryNotFound if an entry with {@code contentSourceId} cannot be found in the {@link TokenStorage}
     */
    public TokenEntry getTokenEntryFor(String contentSourceId) throws TokenEntryNotFound;

    /**
     * Attempts to get the access token for the {@link AuthorizationCodeOAuth}-enabled {@link ContentSource}
     * instance with ID {@code contentSourceId}.
     *
     * @param contentSourceId the ID of the {@code AuthorizationCodeOAuth}-enabled {@code ContentSource} instance whose
     *                        access token to retrieve
     *
     * @return the access token for the {@code AuthorizationCodeOAuth}-enabled {@code ContentSource}
     * instance with ID {@code contentSourceId}
     * @throws TokenEntryNotFound if an entry with {@code contentSourceId} cannot be found in the {@link TokenStorage}
     */
    public String getAccessTokenFor(String contentSourceId) throws TokenEntryNotFound;

    /**
     * Attempts to get the {@link Instant} that the access code for the {@link AuthorizationCodeOAuth}-enabled
     * {@link ContentSource} instance with ID {@code contentSourceId} expires.
     *
     * @param contentSourceId the ID of the {@code AuthorizationCodeOAuth}-enabled {@code ContentSource} instance whose
     *                        expiration time to retrieve
     *
     * @return the {@code Instant} that the access code for the {@code AuthorizationCodeOAuth}-enabled
     * {@code ContentSource} instance with ID {@code contentSourceId} expires, if it does. Else,
     * {@code Optional.empty()}.
     * @throws TokenEntryNotFound if an entry with {@code contentSourceId} cannot be found in the {@link TokenStorage}
     */
    public Optional<Instant> getExpirationTimeFor(String contentSourceId) throws TokenEntryNotFound;

    /**
     * Attempts to get the {@link Instant} that the access code for the {@link AuthorizationCodeOAuth}-enabled
     * {@link ContentSource} instance with ID {@code contentSourceId} was last updated.
     *
     * @param contentSourceId the ID of the {@code AuthorizationCodeOAuth}-enabled {@code ContentSource} instance whose
     *                        {@code TokenEntry} to retrieve
     *
     * @return the {@code Instant} that the access code for the {@code AuthorizationCodeOAuth}-enabled
     * {@code ContentSource} instance with ID {@code contentSourceId} was last updated
     * @throws TokenEntryNotFound if an entry with {@code contentSourceId} cannot be found in the {@link TokenStorage}
     */
    public Instant getLastUpdatedTimeFor(String contentSourceId) throws TokenEntryNotFound;

    /**
     * Checks whether the access token has expired, if an entry exists for the desired {@link AuthorizationCodeOAuth}-enabled
     * {@code ContentSource} instance with ID {@code contentSourceId}.
     *
     * @param contentSourceId the ID of the {@code ContentSource} instance whose {@code TokenEntry} to retrieve
     *
     * @return {@code true} if an entry for the desired {@link AuthorizationCodeOAuth}-enabled {@code ContentSource}
     * instance with ID {@code contentSourceId} exists and is expired. Else, {@code false}.
     * @throws TokenEntryNotFound if an entry with {@code contentSourceId} cannot be found in the {@link TokenStorage}
     */
    public default boolean isAccessTokenExpiredFor(String contentSourceId) throws TokenEntryNotFound {
        if (!tokenEntryExistsFor(contentSourceId)) {
            throw new TokenEntryNotFound("Token Entry not found for Content Source ID \"" + contentSourceId + "\"");
        }

        Optional<Instant> expirationTime = getExpirationTimeFor(contentSourceId);
        return expirationTime.isPresent() && Instant.now().isAfter(expirationTime.get());
    }

    public boolean tokenEntryExistsFor(String contentSourceId);
}