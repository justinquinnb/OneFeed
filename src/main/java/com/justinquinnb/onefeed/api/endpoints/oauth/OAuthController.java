package com.justinquinnb.onefeed.api.endpoints.oauth;

import com.justinquinnb.onefeed.OneFeedApplication;
import com.justinquinnb.onefeed.data.exceptions.InvalidSourceIdException;
import com.justinquinnb.onefeed.data.exceptions.InvalidSourceTypeException;
import com.justinquinnb.onefeed.data.model.source.AuthorizationCodeOAuth;
import com.justinquinnb.onefeed.data.model.source.ContentSource;
import com.justinquinnb.onefeed.data.model.token.TokenEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Manages hits to OneFeed's {@code /auth} API endpoints. OneFeed's {@code /auth} endpoints expose OneFeed's
 * authentication. Served by {@link OAuthService}.
 */
@RestController
@RequestMapping("/auth/")
public class OAuthController {
    private OAuthService oAuthService;
    private static final Logger logger = LoggerFactory.getLogger(OAuthController.class);

    @Autowired
    public OAuthController(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
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
    @GetMapping("/consentUrl")
    public ResponseEntity<String> getConsentUrl(
            @RequestParam(name = "for") String contentSourceId
    ) {
        logger.info("GET Consent URL request received with argument: contentSourceId={}", contentSourceId);

        // Require a Content Source ID that exists and is affiliated with a ContentSource using OAuth 2 Authorization
        // Code-type grants
        if (OneFeedApplication.CONTENT_SOURCES.containsKey(contentSourceId)) {
            // Content Source with provided ID DOES exist, but does not support OAuth 2 Authorization Code grants
            if (!(OneFeedApplication.CONTENT_SOURCES.get(contentSourceId) instanceof AuthorizationCodeOAuth)) {
                logger.warn("Could not get Consent URL: Content Source \"{}\" does not support OAuth 2 Authorization Code" +
                        " grants", contentSourceId);
                throw new InvalidSourceTypeException("Content Source \"" + contentSourceId + "\" does not support " +
                        "OAuth 2 Authorization Code grants");
            }
        // Content Source with provided ID DOES NOT exist
        } else {
            logger.warn("Could not get Consent URL: invalid Content Source ID \"{}\"", contentSourceId);
            throw new InvalidSourceIdException("Content Source with ID " + contentSourceId + " does not exist");
        }
        logger.trace("Content Source \"{}\" found and retrieved", contentSourceId);

        return new ResponseEntity<>(oAuthService.getConsentUrlFor(contentSourceId), HttpStatus.OK);

        // TODO logger.info("Fulfilled Consent URL request");
    }

    /**
     * Stores the provided OAuth 2 Authorization Code grant authorization code.
     *
     * @param contentSourceId the ID of the {@link ContentSource} instance that the {@code authorizationCode} is for
     * @param authorizationCode the authorization code to be exchanged by the {@code ContentSource} instance with ID
     *                          {@code contentSourceId} for an access token
     *
     * @return a copy of the {@link TokenEntry} created from exchanging the {@code authorizationCode} for an access
     * token
     */
    @PostMapping("/postAuthCode")
    public ResponseEntity<TokenEntry> postAuthorizationCode(
            @RequestParam(name = "for") String contentSourceId,
            @RequestParam(name = "code") String authorizationCode
    ) {
        logger.info("POST Authorization Code request received with argument: contentSourceId={}, authorizationCode={}",
                contentSourceId, authorizationCode);

        // Require a Content Source ID that exists and is affiliated with a ContentSource using OAuth 2 Authorization
        // Code-type grants
        if (OneFeedApplication.CONTENT_SOURCES.containsKey(contentSourceId)) {
            // Content Source with provided ID DOES exist, but does not support OAuth 2 Authorization Code grants
            if (!(OneFeedApplication.CONTENT_SOURCES.get(contentSourceId) instanceof AuthorizationCodeOAuth)) {
                logger.warn("Could not post Authorization Code: Content Source \"{}\" does not support OAuth 2 Authorization Code" +
                        " grants", contentSourceId);
                throw new InvalidSourceTypeException("Content Source \"" + contentSourceId + "\" does not support " +
                        "OAuth 2 Authorization Code grants");
            }
            // Content Source with provided ID DOES NOT exist
        } else {
            logger.warn("Could not post Authorization Code: invalid Content Source ID \"{}\"", contentSourceId);
            throw new InvalidSourceIdException("Content Source with ID " + contentSourceId + " does not exist");
        }
        logger.trace("Content Source \"{}\" found and retrieved", contentSourceId);

        TokenEntry createdAccessTokenEntry = oAuthService.exchangeAuthCode(contentSourceId, authorizationCode);
        return new ResponseEntity<>(createdAccessTokenEntry, HttpStatus.CREATED);
    }
}