package com.justinquinnb.onefeed.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security configuration class that defines security filters and other security related
 * settings.
 *
 * @EnableWebSecurity enables Spring Security features
 * @Configuration indicates this class provides Spring bean definitions
 *
 * @author Lynne Munini
 * @see <a href="https://medium.com/@LynneMunini/securing-your-spring-boot-3-app-multi-factor-authentication-with-api-key-and-basic-auth-e6835a3687d1"></a></a>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private ApiKeyAuthenticationFilter apiKeyAuthenticationFilter;

    @Autowired
    public SecurityConfig(ApiKeyAuthenticationFilter apiKeyAuthenticationFilter) {
        this.apiKeyAuthenticationFilter = apiKeyAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChainServers(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**")
                .addFilterBefore(apiKeyAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}