package com.justinquinnb.onefeed.customization.addon.contentsource;

import java.time.Duration;

/**
 * Utilizes the Authorization Code grant type for OAuth 2.0.
 */
public interface AuthorizationCodeOAuth {
    /**
     * Gets the duration of time in which {@code this} {@link AuthorizationCodeOAuth}-enabled {@link ContentSource}'s
     * access token is valid for.
     *
     * @return the duration of time in which {@code this} {@link AuthorizationCodeOAuth}-enabled {@link ContentSource}'s
     * access token is valid for
     */
    public Duration getValidForDuration();

    /**
     * Constructs a URL for the end user to visit in order to grant OneFeed access to the {@link ContentSource}'s
     * content.
     *
     * @return a URL for the end user to visit in order to grant OneFeed access to the {@code ContentSource}'s content
     */
    public String getConsentUrl();

    /**
     * Exchanges an authorization code for an access token.
     *
     * @param authorizationCode the authorization code to exchange for an access token
     *
     * @return a token to access the {@code ContentSource} platform's content
     */
    public String exchangeAuthCode(String authorizationCode);
}