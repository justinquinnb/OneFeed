package dev.jqb.onefeed.core.actor;

import dev.jqb.onefeed.core.provider.ProviderIdentifiable;

/**
 * A unique identifier for an {@link dev.jqb.onefeed.core.actor.Actor} in OneFeed
 * @param providerId the unique identifier of the provider plugin exposing the actor
 * @param idOnPlatform the unique identifier of the actor on its source
 * {@link dev.jqb.onefeed.core.platform.Platform}
 */
public record ActorKey(String providerId, String idOnPlatform) implements ProviderIdentifiable {

    @Override
    public String getProviderId() {
        return providerId;
    }

    /**
     * Gets a string representation of {@code this} actor key.
     * @return a string representation of this actor key in format
     * {@link #providerId}{@code :}{@link #idOnPlatform}
     */
    public String toKeyString() {
        return String.format("%s:%s", providerId, idOnPlatform);
    }
}
