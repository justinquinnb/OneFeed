package com.justinquinnb.onefeed.api.security;

import com.justinquinnb.onefeed.api.OneFeedApiConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

/**
 * @author Lynne Munini
 * @see <a href="https://medium.com/@LynneMunini/securing-your-spring-boot-3-app-multi-factor-authentication-with-api-key-and-basic-auth-e6835a3687d1"></a></a>
 */
@Service
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    /**
     * The name of the HTTP header containing the API Key.
     */
    public static final String AUTH_TOKEN_HEADER_NAME = "API-KEY";

    private OneFeedApiConfig oneFeedApiConfig;

    @Autowired
    public AuthenticationService(OneFeedApiConfig apiConfig) {
        this.oneFeedApiConfig = apiConfig;
    }

    /**
     * Attempts to retrieve and validate the API Key from the request header.
     * If valid, it creates an Authentication object representing the API Key.
     *
     * @param request the HttpServletRequest object
     * @return an Authentication object containing the validated API Key, or throws an exception if
     * invalid
     * @throws BadCredentialsException if the API Key is missing or invalid
     */
    public Authentication getAuthentication(HttpServletRequest request) {
        String requestApiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        logger.debug("Checking API key in request header");
        if (requestApiKey == null || !requestApiKey.equals(oneFeedApiConfig.getKey())) {
            logger.warn("API request denied: Invalid OneFeed API key");
            throw new BadCredentialsException("Invalid OneFeed API key");
        }

        logger.debug("API request approved, key accepted");
        return new ApiKeyAuthentication(requestApiKey, AuthorityUtils.NO_AUTHORITIES);
    }
}