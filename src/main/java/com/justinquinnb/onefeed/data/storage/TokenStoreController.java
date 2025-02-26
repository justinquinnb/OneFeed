package com.justinquinnb.onefeed.data.storage;

import com.justinquinnb.onefeed.api.endpoints.oauth.OAuthService;
import com.justinquinnb.onefeed.data.model.source.AuthorizationCodeOAuth;
import com.justinquinnb.onefeed.data.model.source.ContentSource;
import com.justinquinnb.onefeed.data.model.token.TokenEntry;
import com.justinquinnb.onefeed.data.model.token.TokenStorage;
import com.justinquinnb.onefeed.exceptions.TokenEntryNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * Oversees the storage of API Access Token storage, as needed by {@link OAuthService}.
 */
@Service
public class TokenStoreController {
    private static final Logger logger = LoggerFactory.getLogger(TokenStoreController.class);

    // TODO implement this and TokenStorage interface's corresponding methods

    /**
     * The desired means of API Access Token storage.
     */
    private final TokenStorage tokenStore;

    @Autowired
    public TokenStoreController(SampleTokenStorage tokenStore) {
        this.tokenStore = tokenStore;
    }

    /**
     * Adds a {@link TokenEntry} to the {@link #tokenStore}.
     *
     * @param contentSourceId the ID of the {@link AuthorizationCodeOAuth}-enabled {@link ContentSource} whose access
     *                        token is being stored
     * @param accessToken the access token affiliated with the ID of the {@link AuthorizationCodeOAuth}-enabled
     * {@code ContentSource}
     */
    public void addTokenEntryFor(String contentSourceId, String accessToken) {
        tokenStore.addTokenEntryFor(new TokenEntry(contentSourceId, accessToken));
    }

    /**
     * Adds a {@link TokenEntry} with expiration time to the {@link #tokenStore}.
     *
     * @param contentSourceId the ID of the {@link AuthorizationCodeOAuth}-enabled {@link ContentSource} whose access
     *                        token is being stored
     * @param accessToken the access token affiliated with the ID of the {@link AuthorizationCodeOAuth}-enabled
     *                    {@code ContentSource}
     * @param validFor the amount of time until the provided {@code accessToken} is valid for (i.e. time until expiration)
     *
     * @throws IllegalArgumentException if {@code validFor}'s duration is negative
     */
    public void addTokenEntryFor(
            String contentSourceId, String accessToken, Duration validFor
    ) throws IllegalArgumentException {
        if (validFor.isNegative()) {
            throw new IllegalArgumentException("validFor duration cannot be negative: " + validFor.get(ChronoUnit.DAYS));
        }

        tokenStore.addTokenEntryFor(new TokenEntry(contentSourceId, accessToken, validFor));
    }

    /**
     * Removes the {@link TokenEntry} with Content Source ID {@code contentSourceId} from the {@link #tokenStore}.
     *
     * @param contentSourceId the ID of the {@link AuthorizationCodeOAuth}-enabled {@link ContentSource} whose access
     *                        token is being stored
     *
     * @throws TokenEntryNotFound if a {@code TokenEntry} does not exist with Content Source ID {@code contentSourceId}
     */
    public void removeTokenEntryFor(String contentSourceId) throws TokenEntryNotFound {
        if (!tokenStore.tokenEntryExistsFor(contentSourceId)) {
            throw new TokenEntryNotFound("Could not find a TokenEntry with a Content Source ID of \"" + contentSourceId
                    + "\"");
        }

        tokenStore.removeTokenEntryFor(contentSourceId);
    }

    /**
     * Updates the access token associated with Content Source ID {@code contentSourceId}.
     *
     * @param contentSourceId the ID of the {@link AuthorizationCodeOAuth}-enabled {@link ContentSource} whose access
     *                        token is being stored
     * @param newAccessToken the new access token to associate with the Content Source with ID {@code contentSourceId}
     *
     * @throws TokenEntryNotFound if a {@link TokenEntry} does not exist with Content Source ID {@code contentSourceId}
     */
    public void updateTokenEntryFor(String contentSourceId, String newAccessToken) throws TokenEntryNotFound {
        if (!tokenStore.tokenEntryExistsFor(contentSourceId)) {
            throw new TokenEntryNotFound("Could not find a TokenEntry with a Content Source ID of \"" + contentSourceId
                    + "\"");
        }

        tokenStore.updateTokenEntryFor(contentSourceId, newAccessToken);
    }

    /**
     * Gets the entire {@link TokenEntry} for the desired {@link =AuthorizationCodeOAuth}-enabled {@code ContentSource}
     * instance with ID {@code contentSourceId}.
     *
     * @param contentSourceId the ID of the {@code ContentSource} instance whose {@code TokenEntry} to retrieve
     *
     * @return the {@link TokenEntry} for the desired {@code AuthorizationCodeOAuth}-enabled {@code ContentSource}, if
     * one exists. Else, {@code null}.
     */
    public TokenEntry getTokenEntryFor(String contentSourceId) {
        try {
            logger.debug("Attempting to fetch token entry for Content Source with ID {}", contentSourceId);
            return tokenStore.getTokenEntryFor(contentSourceId);
        } catch (TokenEntryNotFound e) {
            logger.warn("Token Entry not found for Content Source ID {}", contentSourceId);
            return null;
        }
    }

    /**
     * Gets the access token for the desired {@link AuthorizationCodeOAuth}-enabled {@code ContentSource} instance with
     * ID {@code contentSourceId}.
     *
     * @param contentSourceId the ID of the {@code ContentSource} instance whose {@code TokenEntry} to retrieve
     *
     * @return the access token associated with the desired {@code AuthorizationCodeOAuth}-enabled {@code ContentSource},
     * if one exists. Else, {@code null}.
     */
    public String getAccessTokenFor(String contentSourceId) {
        try {
            logger.debug("Attempting to fetch access token for Content Source with ID {}", contentSourceId);
            return tokenStore.getAccessTokenFor(contentSourceId);
        } catch (TokenEntryNotFound e) {
            logger.warn("Token Entry not found for Content Source ID {}", contentSourceId);
            return null;
        }
    }

    /**
     * Gets the {@link Instant} of the access token's last update, if an entry exists for the desired
     * {@link AuthorizationCodeOAuth}-enabled {@code ContentSource} instance with ID {@code contentSourceId}.
     *
     * @param contentSourceId the ID of the {@code ContentSource} instance whose {@code TokenEntry} to retrieve
     *
     * @return the {@link Instant} of the access token associated with the desired {@code AuthorizationCodeOAuth}-enabled
     * {@code ContentSource}'s last update, if one exists. Else, {@code null}.
     */
    public Instant getLastUpdatedTimeFor(String contentSourceId) {
        try {
            logger.debug("Attempting to fetch last update time of access token for Content Source with ID {}",
                    contentSourceId);
            return tokenStore.getLastUpdatedTimeFor(contentSourceId);
        } catch (TokenEntryNotFound e) {
            logger.warn("Token Entry not found for Content Source ID {}", contentSourceId);
            return null;
        }
    }

    /**
     * Gets the {@link Instant} that the access token expires, if an entry exists for the desired
     * {@link AuthorizationCodeOAuth}-enabled {@code ContentSource} instance with ID {@code contentSourceId}.
     *
     * @param contentSourceId the ID of the {@code ContentSource} instance whose {@code TokenEntry} to retrieve
     *
     * @return the {@link Instant} that the access token associated with the desired {@code AuthorizationCodeOAuth}-enabled
     * {@code ContentSource} expires, if one exists. Else, {@code null}.
     */
    public Optional<Instant> getExpirationTimeFor(String contentSourceId) {
        try {
            logger.debug("Attempting to fetch expiration time of access token for Content Source with ID {}",
                    contentSourceId);
            return tokenStore.getExpirationTimeFor(contentSourceId);
        } catch (TokenEntryNotFound e) {
            logger.warn("Token Entry not found for Content Source ID {}", contentSourceId);
            return null;
        }
    }

    /**
     * Checks whether the access token has expired, if an entry exists for the desired {@link AuthorizationCodeOAuth}-enabled
     * {@code ContentSource} instance with ID {@code contentSourceId}.
     *
     * @param contentSourceId the ID of the {@code ContentSource} instance whose {@code TokenEntry} to retrieve
     * @return {@code true} if an entry for the desired {@link AuthorizationCodeOAuth}-enabled {@code ContentSource}
     * instance with ID {@code contentSourceId} exists and is expired. Else, {@code false}.
     */
    public boolean isAccessTokenExpiredFor(String contentSourceId) {
        try {
            logger.debug("Attempting to check expiration status of access token for Content Source with ID {}",
                    contentSourceId);
            return tokenStore.isAccessTokenExpiredFor(contentSourceId);
        } catch (TokenEntryNotFound e) {
            logger.warn("Token Entry not found for Content Source ID {}", contentSourceId);
            return false;
        }
    }
}