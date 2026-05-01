package dev.jqb.onefeed.api.feed;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A feed author's profile
 */
@Getter
@Setter
@NoArgsConstructor
public class Profile extends Author {

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
     * {@link Author} info.
     *
     * @param username the username of the author on the source's platform, devoid of any
     *                 platform-specific prefixes like {@code @}
     * @param feedUrl the URL of the author's feed on the source's platform
     * @param name the non-unique, human name or nickname of the author
     * @param profilePicSrc a URL for their profile picture on the platform
     */
    public Profile(String username, String feedUrl, String name, String profilePicSrc) {
        super(username, feedUrl);
    }
}
