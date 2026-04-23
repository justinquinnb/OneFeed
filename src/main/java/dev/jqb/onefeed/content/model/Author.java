package dev.jqb.onefeed.content.model;

/**
 * Information about a piece of content's author
 *
 * @param username The username of the author on the content's platform, devoid of any
 *                 platform-specific prefixes like {@code @}
 * @param feedUrl  The URL of the author's feed on the content's platform
 */
public record Author(String username, String feedUrl) {
    // Intentionally empty...
}
