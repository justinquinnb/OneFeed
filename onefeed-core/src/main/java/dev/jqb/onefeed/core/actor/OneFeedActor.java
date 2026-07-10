package dev.jqb.onefeed.core.actor;

import dev.jqb.onefeed.core.platform.ExternalRef;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The default implementation of {@link Actor}s
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class OneFeedActor extends Actor {

    /**
     * The non-unique, human name or nickname of the author
     */
    private String name;

    /**
     * The URL of their profile picture's source on the platform
     */
    private String profilePicSrc;

    /**
     * Constructs a user {@code Profile} object, effectively a more personalized piece of
     * {@link Actor} info.
     *
     * @param providerId the unique identifier of the {@link dev.jqb.onefeed.core.provider.Provider}
     *                   the actor is from
     * @param externalRef a means of accessing the resource on the source platform
     * @param handle the username of the actor on the source's platform, devoid of any
     * @param name the non-unique, human name or nickname of the author
     * @param profilePicSrc a URL for their profile picture on the platform
     */
    public OneFeedActor(String providerId, ExternalRef externalRef, String handle, String name,
        String profilePicSrc
    ) {
        super(providerId, externalRef, handle);
        this.name = name;
        this.profilePicSrc = profilePicSrc;
    }
}
