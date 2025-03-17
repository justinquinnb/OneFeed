package com.justinquinnb.onefeed.datastorage.token;

import com.justinquinnb.onefeed.api.endpoints.oauth.OAuthService;
import com.justinquinnb.onefeed.content.details.ContentSourceId;
import com.justinquinnb.onefeed.customization.source.AuthorizationCodeOAuth;
import com.justinquinnb.onefeed.customization.source.ContentSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
     * Adds a {@link TokenEntry} to the active token storage medium, {@link #tokenStore}.
     *
     * @param tokenEntry the {@code TokenEntry} to add to the {@code tokenStore}
     */
    public void add(TokenEntry tokenEntry) {
        tokenStore.addTokenEntryFor(tokenEntry);
    }


    /**
     * Saves an {@link AuthorizationCodeOAuth}-enabled {@link ContentSource}'s {@link TokenEntry} to the active token
     * storage medium, {@link #tokenStore}. If an entry already exists, it is updated to reflect the fields of
     * {@code tokenEntry}. Else, the entirety of {@code tokenEntry} is added whole.
     *
     * @param tokenEntry the {@link TokenEntry} to add to or update in the {@code tokenStore}
     *
     * @return {@code true} if an existing entry was found and updated with the {@code tokenEntry}'s {@link ContentSourceId},
     * else {@code false}
     */
    public boolean save(TokenEntry tokenEntry) {
        String contentSourceId = tokenEntry.getContentSourceId();

        if (tokenStore.tokenEntryExistsFor(contentSourceId)) {
            tokenStore.updateTokenEntryFor(contentSourceId, tokenEntry.getAccessToken());
            return true;
        } else {
            tokenStore.addTokenEntryFor(tokenEntry);
            return false;
        }
    }

    /**
     * Removes the {@link TokenEntry} with {@link ContentSourceId} {@code contentSourceId} from the active token
     * storage medium, {@link #tokenStore}.
     *
     * @param contentSourceId the ID of the {@link AuthorizationCodeOAuth}-enabled {@link ContentSource} whose access
     *                        token is being stored
     *
     * @throws TokenEntryNotFound if a {@code TokenEntry} does not exist with {@code ContentSourceId} {@code contentSourceId}
     * in the {@link #tokenStore}
     */
    public void removeTokenEntryFor(String contentSourceId) throws TokenEntryNotFound {
        if (!tokenStore.tokenEntryExistsFor(contentSourceId)) {
            throw new TokenEntryNotFound("Could not find a TokenEntry with a ContentSourceId of \"" + contentSourceId
                    + "\"");
        }

        tokenStore.removeTokenEntryFor(contentSourceId);
    }

    /**
     * Updates the access token associated with {@link ContentSourceId} {@code contentSourceId} in the active token storage
     * medium, {@link #tokenStore}.
     *
     * @param contentSourceId the ID of the {@link AuthorizationCodeOAuth}-enabled {@link ContentSource} whose access
     *                        token is to be stored in the {@code tokenStore}
     * @param newAccessToken the new access token to associate with the Content Source with ID {@code contentSourceId}
     *
     * @throws TokenEntryNotFound if a {@link TokenEntry} does not exist with {@code ContentSourceId} {@code contentSourceId}
     * in the {@code #tokenStore}
     */
    public void updateTokenEntryFor(String contentSourceId, String newAccessToken) throws TokenEntryNotFound {
        if (!tokenStore.tokenEntryExistsFor(contentSourceId)) {
            throw new TokenEntryNotFound("Could not find a TokenEntry with a ContentSourceId of \"" + contentSourceId
                    + "\"");
        }

        tokenStore.updateTokenEntryFor(contentSourceId, newAccessToken);
    }

    /**
     * Gets the entire {@link TokenEntry} for the desired {@link AuthorizationCodeOAuth}-enabled {@code ContentSource}
     * instance with ID {@code contentSourceId} from the active token storage medium, {@link #tokenStore}.
     *
     * @param contentSourceId the ID of the {@code ContentSource} instance whose {@code TokenEntry} to retrieve
     *
     * @return the {@link TokenEntry} for the desired {@code AuthorizationCodeOAuth}-enabled {@code ContentSource}, if
     * one exists. Else, {@code null}.
     */
    public TokenEntry getTokenEntryFor(String contentSourceId) {
        try {
            logger.debug("Attempting to fetch token entry for ContentSourceID {}", contentSourceId);
            return tokenStore.getTokenEntryFor(contentSourceId);
        } catch (TokenEntryNotFound e) {
            logger.warn("Token Entry not found for ContentSourceID {}", contentSourceId);
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
            logger.warn("Token Entry not found for ContentSourceId {}", contentSourceId);
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
            logger.warn("Token Entry not found for ContentSourceId {}", contentSourceId);
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
            logger.warn("Token Entry not found for ContentSourceId {}", contentSourceId);
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
            logger.warn("Token Entry not found for ContentSourceId {}", contentSourceId);
            return false;
        }
    }
}