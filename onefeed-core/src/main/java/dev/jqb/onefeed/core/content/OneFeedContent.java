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
import org.jspecify.annotations.Nullable;

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
     * Any attached media, such as links, videos, images, or files, in their desired order of
     * presentation or priority (high/first to low/last)
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Media> media;

    /**
     * The quantity of whatever reaction type is primary on the source platform, the semantics of
     * which are discernable via interpretation of the content's source platform by the client
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int primaryReactionCount;

    /**
     * Constructs a piece of {@code OneFeedContent} attributed to a {@code source} and
     * created/published at the given time.
     *
     * @param feedId the unique ID of the feed the content is from
     * @param externalRef a means of accessing the resource on the source platform
     * @param nextPageCursor the cursor pointing to the next page of content after {@code this} (or
     *                       some equivalent means), if known, on the originating platform's API
     * @param published the time the {@code Content} was published on its {@code source}
     * @param body the primary textual content, using CommonMark-Flavored Markdown for any
     *             formatting
     */
    public OneFeedContent(FeedId feedId, ExternalRef externalRef, @Nullable String nextPageCursor,
        Instant published, String body, List<String> authorIds
    ) {
        super(feedId, externalRef, nextPageCursor, published, authorIds);
        this.body = body;
    }

    /**
     * Constructs a piece of {@code OneFeedContent}, containing just media. All other fields may be
     * set with setters.
     *
     * @param feedId the unique ID of the feed the content is from
     * @param externalRef a means of accessing the resource on the source platform
     * @param nextPageCursor the cursor pointing to the next page of content after {@code this} (or
     *                       some equivalent means), if known, on the originating platform's API
     * @param published the time the {@code Content} was published on its {@code source}
     * @param media any attached media, such as links, videos, images, or files, in their desired
     *              order of presentation or priority (high/first to low/last)
     */
    public OneFeedContent(FeedId feedId, ExternalRef externalRef, @Nullable String nextPageCursor,
        Instant published, List<Media> media, List<String> authorIds
    ) {
        super(feedId, externalRef, nextPageCursor, published, authorIds);
        this.media = media;
    }

    /**
     * Constructs a piece of {@code OneFeedContent}, containing both body text and media. All other
     * fields may be set with setters.
     *
     * @param feedId the unique ID of the feed the content is from
     * @param externalRef a means of accessing the resource on the source platform
     * @param nextPageCursor the cursor pointing to the next page of content after {@code this} (or
     *                       some equivalent means), if known, on the originating platform's API
     * @param published the time the {@code Content} was published on its {@code source}
     * @param body the primary textual content, using CommonMark-Flavored Markdown for any
     *             formatting
     * @param media any attached media, such as links, videos, images, or files, in their desired
     *              order of presentation or priority (high/first to low/last)
     */
    public OneFeedContent(FeedId feedId, ExternalRef externalRef, @Nullable String nextPageCursor,
        Instant published, String body, List<Media> media, List<String> authorIds
    ) {
        super(feedId, externalRef, nextPageCursor, published, authorIds);
        this.body = body;
        this.media = media;
    }

    @Override
    public List<String> getAuthorIds() {
        return List.of();
    }
}
