package com.justinquinnb.onefeed.data.model.source;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * A basic API Endpoint-type data source to optionally extend.
 */
public abstract class APIEndpoint implements ContentSource {
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    private static final Charset CHARSET = StandardCharsets.UTF_8;

    /**
     * Gets the base URL of the desired endpoint.
     *
     * @return the base URL of the desired endpoint.
     */
    protected abstract String getBaseUrl();

    /**
     * Makes a request to the specified {@code #baseUrl} with the provided {@code args}, returning its response.
     *
     * @param args the arguments to send with the query
     *
     * @return a response to the request made.
     * @throws IOException if an I/ O error occurs when sending or receiving, or the client has shut down.
     * @throws InterruptedException  if the operation is interrupted.
     */
    public HttpResponse<String> request(String args) throws IOException, InterruptedException {
        String requestUrl = buildEndpointUrl(getBaseUrl(), args);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .build();

        return HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Builds the final API endpoint URL provided a base and arguments list, {@code args}.
     *
     * @param baseUrl the base URL of the API endpoint
     * @param args the arguments to append to the base
     * @return a valid encoding of the API endpoint URL derived from the {@code baseUrl} and {@code args}.
     */
    private String buildEndpointUrl(String baseUrl, String args) {
        String encodedArgs = URLEncoder.encode(args, CHARSET);
        return getBaseUrl() + "?" + encodedArgs;
    }
}
