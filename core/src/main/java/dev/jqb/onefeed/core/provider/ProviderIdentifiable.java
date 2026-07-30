package dev.jqb.onefeed.core.provider;

/**
 * A type whose source {@link Provider} can be identified
 */
public interface ProviderIdentifiable {

    /**
     * Gets the ID of the {@link Provider} the object is from
     * @return the ID of the {@link Provider} the object is from
     */
    String getProviderId();
}
