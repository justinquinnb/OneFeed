package dev.jqb.onefeed.core.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jspecify.annotations.Nullable;

/**
 * A piece of media attached to a piece of content
 */
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OneFeedMedia extends OneFeedAttachment {
    /**
     * The type of media being represented, adhering to <a href="https://datatracker.ietf.org/doc/html/rfc6838">RFC 6838</a>
     * as an official entry in the <a href="https://www.iana.org/assignments/media-types/media-types.xhtml">IANA Media Types registry</a>.
     */
    private String mimeType;

    /**
     * A URL to the media resource itself, for direct embedding
     */
    private String src;

    /**
     * Alt text for the piece of media
     */
    @Nullable
    private String altText;

    private OneFeedMedia(
        String href,
        @Nullable String thumbnailSrc,
        @Nullable String title,
        @Nullable String caption,
        String mimeType,
        String src,
        @Nullable String altText
    ) {
        super(href, thumbnailSrc, title, caption);
        this.mimeType = mimeType;
        this.src = src;
        this.altText = altText;
    }

    /**
     * Prepares a {@link OneFeedMediaBuilder} for constructing a {@link OneFeedMedia} object.
     *
     * @param href the link to the attachment on its host platform
     * @param mimeType the type of media being represented, adhering to
     *                 <a href="https://datatracker.ietf.org/doc/html/rfc6838">RFC 6838</a> as an
     *                 official entry in the <a href="https://www.iana.org/assignments/media-types/media-types.xhtml">IANA Media Types registry</a>.
     * @param src a URL to the media resource itself, for direct embedding
     * @param altText alt text for the piece of media
     *
     * @return a {@link OneFeedMediaBuilder} to construct a {@link OneFeedMedia} object with
     */
    public static OneFeedMediaBuilder builder(
        String href, String mimeType, String src, @Nullable String altText
    ) {
        return new OneFeedMediaBuilder(href, mimeType, src, altText);
    }

    /**
     * A builder for {@code OneFeedMedia} objects
     */
    public static class OneFeedMediaBuilder {
        private String href;
        private String thumbnailSrc;
        private String title;
        private String caption;
        private String mimeType;
        private String src;
        private String altText;

        private OneFeedMediaBuilder(String href, String mimeType, String src, @Nullable String altText) {
            this.href = href;
            this.mimeType = mimeType;
            this.src = src;
            this.altText = altText;
        }

        /**
         * Sets the source of the attachment's thumbnail.
         */
        public OneFeedMediaBuilder thumbnailSrc(String thumbnailSrc) {
            this.thumbnailSrc = thumbnailSrc;
            return this;
        }

        /**
         * The title or name of the attachment (such as the title of a link).
         */
        public OneFeedMediaBuilder title(String title) {
            this.title = title;
            return this;
        }

        /**
         * A caption for the attachment.
         */
        public OneFeedMediaBuilder caption(String caption) {
            this.caption = caption;
            return this;
        }

        /**
         * Builds the {@link OneFeedMedia} object using the data provided to {@code this} builder.
         * @return a {@code OneFeedMedia} object with the data provided to {@code this} builder,
         */
        public OneFeedMedia build() {
            return new OneFeedMedia(
                href, thumbnailSrc, title, caption, mimeType, src, altText);
        }
    }
}
