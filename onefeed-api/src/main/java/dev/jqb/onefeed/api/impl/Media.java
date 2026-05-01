package dev.jqb.onefeed.api.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * A piece of media included in a piece of content
 */
@Getter
@Setter
public class Media {

    /**
     * The type of media being represented
     */
    @NonNull
    private MediaType type;

    /**
     * The title or name of the media (such as the title of a link)
     */
    @NonNull
    private String title;

    /**
     * The link to the media on its host site
     */
    @NonNull
    private String href;

    /**
     * The source of the resource to display, whether that be a link preview, video, image, etc.
     * Semantically dependent on the {@link #type} of media being represented.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String displaySrc;

    /**
     * A caption for the piece of media
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String caption;

    /**
     * Alt text for the piece of media
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String alt;


    public Media(@NonNull MediaType type, @NonNull String title, @NonNull String href) {
        this.type = type;
        this.title = title;
        this.href = href;
    }

    public enum MediaType {
        LINK, IMAGE, VIDEO, DOCUMENT
    }
}
