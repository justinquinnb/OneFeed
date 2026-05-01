package dev.jqb.onefeed.api.feed;

/**
 * A feed author's profile
 */
public class Profile extends Author {

    /**
     * The non-unique, human name or nickname of the person
     */
    private String name;

    /**
     * The URL of their profile picture's source
     */
    private String profilePicSrc;

    public Profile(String username, String feedUrl, String name, String profilePicSrc) {
        super(username, feedUrl);
    }
}
