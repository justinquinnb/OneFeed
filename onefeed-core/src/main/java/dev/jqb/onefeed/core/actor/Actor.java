package dev.jqb.onefeed.core.actor;

import dev.jqb.onefeed.core.platform.ExternalRef;
import dev.jqb.onefeed.core.provider.ProviderIdentifiable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The minimum required data for of a feed's actor, whether that be an author (an entity who creates
 * a piece of content) or a publisher (an entity who publishes it to a platform)
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public abstract class Actor implements ProviderIdentifiable {

    /**
     * The unique identifier of the {@link dev.jqb.onefeed.core.provider.Provider} the actor is from
     */
    private String providerId;

    /**
     * A means of accessing the resource on the source platform
     */
    private ExternalRef externalRef;

    /**
     * The handle of the actor on the content's platform, devoid of any platform-specific prefixes
     * like {@code @}
     */
    private String handle;

    /**
     * Constructs an {@code Author} attributed to a {@code source} and represented by a
     * {@code handle}.
     *
     * @param providerId the unique identifier of the {@link dev.jqb.onefeed.core.provider.Provider}
     *                   the actor is from
     * @param externalRef a means of accessing the resource on the source platform
     * @param handle the username of the actor on the source's platform, devoid of any
     *                 platform-specific prefixes like {@code @}
     */
    public Actor(String providerId, ExternalRef externalRef, String handle) {
        this.providerId = providerId;
        this.externalRef = externalRef;
        this.handle = handle;
    }

    /**
     * Gets a unique key for {@code this} actor on OneFeed.
     * @return a unique key for this actor on OneFeed
     */
    public ActorKey getKey() {
        return new ActorKey(providerId, externalRef.id());
    }

    @Override
    public String getProviderId() {
        return providerId;
    }
}
