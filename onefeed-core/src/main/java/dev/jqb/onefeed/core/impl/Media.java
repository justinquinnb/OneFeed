package dev.jqb.onefeed.core.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.jqb.onefeed.core.standards.rss.RssEnclosure;
import dev.jqb.onefeed.core.standards.rss.RssImage;
import jakarta.activation.MimeType;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jspecify.annotations.Nullable;

/**
 * A piece of media included in a piece of content
 */
@Setter
@NoArgsConstructor
@ToString
public class Media {

    /**
     * The type of media being represented
     */
    @Getter
    private MimeType type;

    /**
     * The link to view the media on its host site
     */
    @Getter
    private String href;

    /**
     * The title or name of the media (such as the title of a link)
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Nullable
    private String title;

    /**
     * The media resource itself, for direct embedding
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String src;

    /**
     * The source of the resource to display, whether that be a link preview, video, image, etc.
     * Semantically dependent on the {@link #type} of media being represented.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Nullable
    private String thumbnailSrc;

    /**
     * A caption for the piece of media
     */
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String caption;

    /**
     * Alt text for the piece of media
     */
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String altText;

    /**
     * Constructs a piece of {@link Media}.
     *
     * @param type the type of media the constructed object represents, guiding its fields' semantic
     *             interpretation or presentation by the client
     * @param href the click-through link to view the media on its host platform
     */
    public Media(MimeType type, String href) {
        this.type = type;
        this.href = href;
    }

    /**
     * Gets the media resource itself, for direct embedding
     */
    public Optional<String> getSrc() {
        return Optional.ofNullable(this.src);
    }

    /**
     * Gets the source of the resource to display, whether that be a link preview, video, image, etc.
     * Semantically dependent on the {@link #type} of media being represented.
     */
    public Optional<String> getThumbnailSrc() {
        return Optional.ofNullable(this.thumbnailSrc);
    }

    /**
     * Gets the caption for the piece of media.
     */
    public Optional<String> getCaption() {
        return Optional.ofNullable(this.caption);
    }

    /**
     * Gets the alt text for the piece of media.
     */
    public Optional<String> getAltText() {
        return Optional.ofNullable(this.altText);
    }
}
