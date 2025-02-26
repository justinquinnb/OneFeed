package com.justinquinnb.onefeed.api.endpoints.oauth;


import com.justinquinnb.onefeed.OneFeedApplication;
import com.justinquinnb.onefeed.data.model.source.AuthorizationCodeOAuth;
import com.justinquinnb.onefeed.data.model.source.ContentSource;
import com.justinquinnb.onefeed.data.model.token.TokenEntry;
import com.justinquinnb.onefeed.data.model.token.TokenStorage;
import com.justinquinnb.onefeed.data.storage.TokenStoreController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Provides the data for OneFeed's {@code /auth} API endpoints.
 * @see OAuthController
 */
@Service
public class OAuthService {
    private TokenStoreController tokenStoreController;

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
        AuthorizationCodeOAuth contentSource = (AuthorizationCodeOAuth) OneFeedApplication.CONTENT_SOURCES.get(contentSourceId);
        String consentUrl = contentSource.getConsentUrl();

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
    public TokenEntry exchangeAccessToken(String contentSourceId, String authorizationCode) {
        AuthorizationCodeOAuth contentSource = (AuthorizationCodeOAuth) OneFeedApplication.CONTENT_SOURCES.get(contentSourceId);
        String authCode = contentSource.exchangeAuthCode(authorizationCode);

        tokenStoreController.addTokenEntryFor(contentSourceId, authCode, contentSource.getValidForDuration());

        return tokenStoreController.getTokenEntryFor(contentSourceId);
    }
}