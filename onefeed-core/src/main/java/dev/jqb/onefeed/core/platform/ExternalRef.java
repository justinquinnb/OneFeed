package dev.jqb.onefeed.core.platform;

/**
 * A reference to a resource on an external {@link Platform}
 *
 * @param url a direct URL to the resource
 * @param id the unique ID of the resource on its platform
 */
public record ExternalRef(String url, String id) {}
