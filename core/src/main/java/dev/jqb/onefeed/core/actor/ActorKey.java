package dev.jqb.onefeed.core.actor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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
    @JsonValue
    public String toKeyString() {
        return String.format("%s:%s", providerId, idOnPlatform);
    }

    /**
     * Parses an {@code ActorKey} from a string.
     * @param keyString the string to parse, of format {@link #providerId}{@code :}{@link #idOnPlatform}
     * @return the {@code ActorKey} represented by {@code keyString}
     */
    @JsonCreator
    public static ActorKey fromKeyString(String keyString) {
        String[] parts = keyString.split(":");
        return new ActorKey(parts[0], parts[1]);
    }
}
