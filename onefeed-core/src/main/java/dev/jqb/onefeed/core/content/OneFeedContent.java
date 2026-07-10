package dev.jqb.onefeed.core.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.jqb.onefeed.core.feed.FeedId;
import dev.jqb.onefeed.core.platform.ExternalRef;
import java.time.Instant;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The default implementation of {@link Content}
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class OneFeedContent extends Content {

    /**
     * The title of the content, using CommonMark-Flavored Markdown for any formatting.
     *
     * @see <a href="https://spec.commonmark.org/0.31.2/">CommonMark Spec</a>
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;

    /**
     * The primary textual content, using CommonMark-Flavored Markdown for any formatting
     *
     * @see <a href="https://spec.commonmark.org/0.31.2/">CommonMark Spec</a>
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String body;

    /**
     * Any attached data, such as links, videos, images, or files, in their desired order of
     * presentation or priority (high/first to low/last)
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<? extends OneFeedAttachment> attachments;

    /**
     * The quantity of whatever reaction type is primary on the source platform, the semantics of
     * which are discernable via interpretation of the content's source platform by the client
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int primaryReactionCount;

    /**
     * Creates a piece of {@code OneFeedContent} from the given builder.
     * @param builder the builder to construct the content with
     */
    protected OneFeedContent(OneFeedContentBuilder builder) {
        super(builder.getFeedId(), builder.getExternalRef(), builder.getNextPageCursor(),
            builder.getPublished(), builder.getAuthorIds());
        this.title = builder.getTitle();
        this.body = builder.getBody();
        this.attachments = builder.getAttachments();
        this.primaryReactionCount = builder.getPrimaryReactionCount();
    }

    /**
     * Prepares a new {@code OneFeedContentBuilder} with the given fields.
     *
     * @param feedId the unique ID of the feed the content is from
     * @param externalRef a means of accessing the resource on the source platform
     * @param published the time the {@code Content} was published on its {@code source}
     */
    public static OneFeedContentBuilder builder(FeedId feedId, ExternalRef externalRef,
        Instant published
    ) {
        return new OneFeedContentBuilder(feedId, externalRef, published);
    }

    /**
     * A builder for {@code OneFeedContent} objects
     */
    @Getter
    public static class OneFeedContentBuilder {
        private FeedId feedId;
        private ExternalRef externalRef;
        private String nextPageCursor;
        private Instant published;
        private int primaryReactionCount;
        private List<String> authorIds;

        private String title;
        private String body;
        private List<OneFeedAttachment> attachments;

        /**
         * Prepares a new {@code OneFeedContentBuilder} with the given fields.
         *
         * @param feedId the unique ID of the feed the content is from
         * @param externalRef a means of accessing the resource on the source platform
         * @param published the time the {@code Content} was published on its {@code source}
         */
        private OneFeedContentBuilder(FeedId feedId, ExternalRef externalRef, Instant published) {
            this.feedId = feedId;
            this.externalRef = externalRef;
            this.published = published;
        }

        /**
         * Sets the next page cursor for the content.
         * @param nextPageCursor the cursor pointing to the next page of content after {@code this} (or
         *                       some equivalent means), if known, on the originating platform's API
         * @return the updated builder
         */
        public OneFeedContentBuilder nextPageCursor(String nextPageCursor) {
            this.nextPageCursor = nextPageCursor;
            return this;
        }

        /**
         * Sets the primary reaction count for the content.
         * @param primaryReactionCount the quantity of whatever reaction type is primary on the
         *                             source platform, the semantics of which are discernable via
         *                             interpretation of the content's source platform by the client
         * @return the updated builder
         */
        public OneFeedContentBuilder primaryReactionCount(int primaryReactionCount) {
            this.primaryReactionCount = primaryReactionCount;
            return this;
        }

        /**
         * Sets the author IDs for the content.
         * @param authorIds the IDs of the authors of {@code this} content on the source platform
         * @return the updated builder
         */
        public OneFeedContentBuilder authorIds(List<String> authorIds) {
            this.authorIds = authorIds;
            return this;
        }

        /**
         * Sets the title of the content, using CommonMark-Flavored Markdown for any formatting.
         * @param title the title of the content, using CommonMark-Flavored Markdown for any
         *              formatting
         * @return the updated builder
         */
        public OneFeedContentBuilder title(String title) {
            this.title = title;
            return this;
        }

        /**
         * Sets the primary textual content, using CommonMark-Flavored Markdown for any formatting.
         * @param body the primary textual content, using CommonMark-Flavored Markdown for any
         *             formatting
         * @return the updated builder
         */
        public OneFeedContentBuilder body(String body) {
            this.body = body;
            return this;
        }

        /**
         * Sets any attached attachments, such as links, videos, images, or files, in their desired order
         * of presentation or priority (high/first to low/last)
         * @param attachments any attached attachments, such as links, videos, images, or files, in their
         *              desired order of presentation or priority (high/first to low/last)
         * @return the updated builder
         */
        public OneFeedContentBuilder attachments(List<OneFeedAttachment> attachments) {
            this.attachments = attachments;
            return this;
        }

        /**
         * Builds the {@code OneFeedContent} object using the data provided to {@code this} builder.
         * @return the built {@code OneFeedContent} object
         */
        public OneFeedContent build() {
            return new OneFeedContent(this);
        }
    }

    @Override
    public List<String> getAuthorIds() {
        return List.of();
    }
}
