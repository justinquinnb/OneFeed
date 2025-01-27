package com.justinquinnb.onefeed.api.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
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
    /**
     * The name of the HTTP header containing the API Key.
     */
    public static final String AUTH_TOKEN_HEADER_NAME = "API-KEY";

    private static final String ONEFEED_API_KEY = "test";

    /**
     * Attempts to retrieve and validate the API Key from the request header.
     * If valid, it creates an Authentication object representing the API Key.
     *
     * @param request the HttpServletRequest object
     * @return an Authentication object containing the validated API Key, or throws an exception if invalid
     * @throws BadCredentialsException if the API Key is missing or invalid
     */
    public static Authentication getAuthentication(HttpServletRequest request) {
        String requestApiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        System.out.println("Provided key: " + requestApiKey);
        System.out.println("Desired key: " + ONEFEED_API_KEY);
        if (requestApiKey == null || !requestApiKey.equals(ONEFEED_API_KEY)) {
            throw new BadCredentialsException("Invalid OneFeed API Key");
        }

        return new ApiKeyAuthentication(requestApiKey, AuthorityUtils.NO_AUTHORITIES);
    }
}