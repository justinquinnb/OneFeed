package dev.jqb.onefeed.core.content;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jspecify.annotations.Nullable;

/**
 * An attachment included in a piece of content
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class OneFeedAttachment {

    /**
     * The link to the attachment on its host platform
     */
    protected String href;

    /**
     * The source of the attachment's thumbnail
     */
    @Nullable
    protected String thumbnailSrc;

    /**
     * The title or name of the attachment (such as the title of a link)
     */
    @Nullable
    protected String title;

    /**
     * A caption for the attachment
     */
    @Nullable
    protected String caption;

    /**
     * Constructs a {@link OneFeedAttachment}.
     *
     * @param href the click-through link to view the media on its host platform
     * @param thumbnailSrc the source of the attachment's thumbnail
     * @param title the title or name of the attachment (such as the title of a link)
     * @param caption a caption for the attachment
     */
    public OneFeedAttachment(
        String href, @Nullable String thumbnailSrc, @Nullable String title, @Nullable String caption
    ) {
        this.href = href;
        this.thumbnailSrc = thumbnailSrc;
        this.title = title;
        this.caption = caption;
    }
}
