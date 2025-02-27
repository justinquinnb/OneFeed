package com.justinquinnb.onefeed.api.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * A structure indicating the authentication status of an inbound OneFeed API request after it's been caught and checked
 * by the {@link ApiKeyAuthenticationFilter} and {@link AuthenticationService}.
 *
 * @author Lynne Munini
 * @see <a href="https://medium.com/@LynneMunini/securing-your-spring-boot-3-app-multi-factor-authentication-with-api-key-and-basic-auth-e6835a3687d1"></a>
 */
public class ApiKeyAuthentication extends AbstractAuthenticationToken {
    /**
     * The API obtained from the request header.
     */
    private final String apiKey;

    /**
     * Creates a new ApiKeyAuthentication object with the provided API Key and authorities.
     *
     * @param apiKey      the API Key value obtained from the request header
     * @param authorities the collection of GrantedAuthority objects associated with the API Key,
     *                    typically an empty collection (e.g., AuthorityUtils.NO_AUTHORITIES)
     */
    public ApiKeyAuthentication(String apiKey, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.apiKey = apiKey;
        setAuthenticated(true);
    }

    /**
     * Returns null as credentials are not applicable for API Key authentication.
     *
     * @return null
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * Returns the API Key value as the principal object.
     *
     * @return the API Key string
     */
    @Override
    public Object getPrincipal() {
        return apiKey;
    }
}