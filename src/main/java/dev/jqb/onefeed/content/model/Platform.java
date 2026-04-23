package dev.jqb.onefeed.content.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Information about a content source, like Instagram
 */
@Getter @Setter
public class Platform {

    /**
     * The name of the content source
     */
    private String name;

    /**
     * The main page of the content source
     */
    private String url;
}
