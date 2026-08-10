package dev.jqb.onefeed.core.actor;

import dev.jqb.onefeed.core.platform.ExternalRef;
import dev.jqb.onefeed.core.provider.Provider;
import dev.jqb.onefeed.core.provider.ProviderIdentifiable;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The minimum required data for of a feed's actor, whether that be an author (an entity who creates
 * a piece of content) or a publisher (an entity who publishes it to a platform)
 */
@NoArgsConstructor
@ToString
public abstract class Actor implements ProviderIdentifiable {

    /**
     * Gets a unique key for {@code this} actor on OneFeed.
     * @return a unique key for this actor on OneFeed
     */
    public final ActorKey getKey() {
        return new ActorKey(getProviderId(), getExternalRef().id());
    }

    /**
     * Gets the means of accessing {@code this} {@code Actor} on the source platform.
     */
    public abstract ExternalRef getExternalRef();

    /**
     * Sets the means of accessing {@code this} {@code Actor} on the source platform.
     * @param externalRef the external reference to assign to {@code this} {@code Actor}
     */
    public abstract void setExternalRef(ExternalRef externalRef);

    /**
     * Gets the handle of the actor on the content's platform, devoid of any platform-specific
     * prefixes like {@code @}.
     */
    public abstract String getHandle();

    /**
     * Sets the handle of the actor on the content's platform, devoid of any platform-specific
     * prefixes like {@code @}.
     * @param handle the handle to assign to {@code this} {@code Actor}
     */
    public abstract void setHandle(String handle);

    /**
     * Associates {@code this} {@code Actor} with the unique identifier of the
     * {@link Provider} it's from.
     *
     * @param providerId the unique identifier of the {@link Provider}
     *                   {@code this} {@code Actor} came from
     */
    public abstract void setProviderId(String providerId);
}
