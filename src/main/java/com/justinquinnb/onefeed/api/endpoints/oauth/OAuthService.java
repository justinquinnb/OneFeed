package com.justinquinnb.onefeed.api.endpoints.oauth;


import com.justinquinnb.onefeed.OneFeedApplication;
import com.justinquinnb.onefeed.customization.source.AuthorizationCodeOAuth;
import com.justinquinnb.onefeed.customization.source.ContentSource;
import com.justinquinnb.onefeed.datastorage.token.TokenEntry;
import com.justinquinnb.onefeed.datastorage.token.TokenStorage;
import com.justinquinnb.onefeed.datastorage.token.TokenStoreController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides the data for OneFeed's {@code /auth} API endpoints.
 * @see OAuthController
 */
@Service
public class OAuthService {
    private TokenStoreController tokenStoreController;
    private static final Logger logger = LoggerFactory.getLogger(OAuthService.class);

    @Autowired
    public OAuthService(TokenStoreController tokenStoreController) {
        this.tokenStoreController = tokenStoreController;
    }

    /**
     * Retrieves the URL necessary to authorize OneFeed's consumption of content data for Content Sources using OAuth 2.
     *
     * @param contentSourceId the Content Source ID of the desired {@link AuthorizationCodeOAuth}-enabled
     * {@code ContentSource}
     *
     * @return a URL to the authorization window for the {@link ContentSource}'s platform where content access can be
     * granted
     */
    public String getConsentUrlFor(String contentSourceId) {
        logger.debug("Getting Consent URL for ContentSource with ID \"{}\"", contentSourceId);

        AuthorizationCodeOAuth contentSource = (AuthorizationCodeOAuth) OneFeedApplication.CONTENT_SOURCES.get(contentSourceId);
        logger.trace("Instance of ContentSource with ID \"{}\" retrieved", contentSourceId);

        String consentUrl = contentSource.getConsentUrl();
        logger.debug("ConsentUrl retrieved from ContentSource with ID \"{}\": {}", contentSourceId, consentUrl);
        return consentUrl;
    }

    /**
     * Exchanges the authorization code of an {@link AuthorizationCodeOAuth}-enabled {@link ContentSource} instance with
     * Content Source ID {@code contentSourceId} for an access token.
     *
     * @param contentSourceId the Content Source ID of an {@code AuthorizationCodeOAuth}-enabled {@code ContentSource}
     *                        instance whose platform's content access token is trying to be secured
     * @param authorizationCode the authorization code provided to the {@code ContentSource} instance after consent has
     *                          been granted by the user
     *
     * @return the {@link TokenEntry} stored in the active {@link TokenStorage} after a successful exchange
     */
    public TokenEntry exchangeAuthCode(String contentSourceId, String authorizationCode) {
        logger.debug("Exchanging ContentSource with ID \"{}\"'s authorization code for access token", contentSourceId);

        // Get an instance of the desired ContentSource
        AuthorizationCodeOAuth contentSource = (AuthorizationCodeOAuth) OneFeedApplication.CONTENT_SOURCES.get(contentSourceId);
        logger.trace("Instance of ContentSource with ID \"{}\" retrieved", contentSourceId);

        // Exchange the provided authorization code for an access code using the source's specified method
        String accessToken = contentSource.exchangeAuthCode(authorizationCode);
        logger.debug("Access Token retrieved for ContentSource with ID \"{}\"", contentSourceId);

        // Save the acquired access token to the Token Store
        TokenEntry newTokenEntry = new TokenEntry(contentSourceId, accessToken, contentSource.getValidForDuration());
        logger.trace("TokenEntry created for ContentSource with ID \"{}\": {}", contentSourceId, newTokenEntry);

        if (tokenStoreController.save(newTokenEntry)) {
            logger.debug("TokenEntry in TokenStorage updated for ContentSource with ID \"{}\"", contentSourceId);
        } else {
            logger.debug("TokenEntry for ContentSource with ID \"{}\" added to TokenStorage", contentSourceId);
        }

        return tokenStoreController.getTokenEntryFor(contentSourceId);
    }
}