package dev.jqb.onefeed.core.platform;

import dev.jqb.onefeed.core.provider.ProviderIdentifiable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Information about a content source, like Instagram
 */
@Getter
@Setter
@ToString
public class Platform implements ProviderIdentifiable {

    /**
     * The unique identifier of the provider exposing this platform
     */
    private final String providerId;

    /**
     * The name of the platform, like Instagram
     */
    private String name;

    /**
     * The URL of the platform's homepage
     */
    private String homepageUrl;

    /**
     * Constructs a piece of {@code Platform} info.
     *
     * @param name the name of the platform, like Instagram
     * @param homepageUrl a URL to the platform's homepage
     */
    public Platform(String providerId, String name, String homepageUrl) {
        this.providerId = providerId;
        this.name = name;
        this.homepageUrl = homepageUrl;
    }

    @Override
    public String getProviderId() {
        return providerId;
    }
}
