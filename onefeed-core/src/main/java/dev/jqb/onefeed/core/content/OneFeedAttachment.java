package dev.jqb.onefeed.core.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSerializeAs;
import dev.jqb.onefeed.core.compat.rss.RssEnclosure;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jspecify.annotations.Nullable;

/**
 * An attachment included in a piece of content
 */
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OneFeedAttachment implements RssEnclosure {

    /**
     * The link to the attachment on its host platform
     */
    @Getter
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

    /**
     * Gets the source of the attachment's thumbnail.
     */
    public Optional<String> getThumbnailSrc() {
        return Optional.ofNullable(this.thumbnailSrc);
    }

    /**
     * Gets the caption of the attachment.
     */
    public Optional<String> getCaption() {
        return Optional.ofNullable(this.caption);
    }

    /**
     * Gets the title or name of the attachment (such as the title of a link).
     */
    public Optional<String> getTitle() {
        return Optional.ofNullable(this.title);
    }

    @Override
    public String getRssEnclosureUrl() {
        return this.href;
    }

    @Override
    public int getRssEnclosureLength() {
        return 0;
    }

    @Override
    public String getRssEnclosureType() {
        return "text/plain";
    }
}
