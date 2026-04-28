package dev.jqb.onefeed.api.model.data.content;

/**
 * Information about a content source, like Instagram
 *
 * @param name The name of the content source
 * @param url  The main page of the content source
 */
public record Platform(String name, String url) {
    // Intentionally empty...
}
