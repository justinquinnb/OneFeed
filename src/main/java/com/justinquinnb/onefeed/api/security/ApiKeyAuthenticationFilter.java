package com.justinquinnb.onefeed.api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * A custom Spring Security filter responsible for retrieving the HTTP API Key header from the request
 * and validating it against the configured secret.
 *
 * @author Lynne Munini
 * @see <a href="https://medium.com/@LynneMunini/securing-your-spring-boot-3-app-multi-factor-authentication-with-api-key-and-basic-auth-e6835a3687d1"></a></a>
 */
@Component
public class ApiKeyAuthenticationFilter extends GenericFilterBean {
    /**
     * Extracts the API Key header from the request, validates it against the configured secret,
     * and sets the resulting Authentication object into the SecurityContext.
     * If validation fails, an unauthorized status code (401) is sent in the response.
     *
     * @param request     the incoming ServletRequest
     * @param response    the outgoing ServletResponse
     * @param filterChain the next filter in the chain
     * @throws IOException      if an I/O error occurs during processing
     * @throws ServletException if an error occurs that interferes with the filter chain
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            Authentication authentication = AuthenticationService.getAuthentication((HttpServletRequest) request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception exp) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter writer = httpResponse.getWriter();
            writer.print(exp.getMessage());
            writer.flush();
            writer.close();
            return; // Don't continue processing the filter chain if authentication fails
        }

        filterChain.doFilter(request, response);
    }
}