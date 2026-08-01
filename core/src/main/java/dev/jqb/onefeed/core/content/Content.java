package dev.jqb.onefeed.core.content;

import dev.jqb.onefeed.core.feed.FeedAttribution;
import dev.jqb.onefeed.core.feed.FeedId;
import dev.jqb.onefeed.core.feed.FeedIdentifiable;
import dev.jqb.onefeed.core.platform.ExternalRef;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jspecify.annotations.Nullable;

/**
 * The minimum required data for of a piece of content.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class Content implements FeedIdentifiable, Comparable<Content> {

    /**
     * The unique ID of the feed the content is from
     */
    protected FeedAttribution source;

    /**
     * A means of accessing the resource on the source platform
     */
    protected ExternalRef externalRef;

    /**
     * Gets time at which the content was published
     */
    protected Instant published;

    /**
     * The cursor pointing to the next page of content after {@code this} (or some equivalent means),
     * if known, on the originating platform's API
     */
    @Nullable
    protected String nextPageCursor;

    /**
     * The IDs of the authors of {@code this} content on the source platform
     */
    protected List<String> authorIds;

    /**
     * Constructs a piece of {@code Content} attributed to a {@code source} and created/published
     * at the given time.
     *
     * @param source the source feed the content is from
     * @param externalRef a means of accessing the resource on the source platform
     * @param nextPageCursor the cursor pointing to the next page of content after {@code this} (or
     *                       some equivalent means), if known, on the originating platform's API
     * @param published the time the {@code Content} was published on its {@code source}
     * @param authorIds the IDs of the authors of {@code this} content on the source platform
     */
    public Content(FeedAttribution source, ExternalRef externalRef, @Nullable String nextPageCursor,
        Instant published, List<String> authorIds
    ) {
        this.source = source;
        this.externalRef = externalRef;
        this.nextPageCursor = nextPageCursor;
        this.published = published;
        this.authorIds = authorIds;
    }

    /**
     * Gets a unique key for {@code this} content on OneFeed.
     * @return a unique key for this content on OneFeed
     */
    public ContentKey getKey() {
        return new ContentKey(source.feedId(), externalRef.id());
    }

    /**
     * Compares this {@code Content} to another {@code Content} by their published time, producing
     * a descending, chronological order appropriate for feeds.
     *
     * @param other the other piece of content to compare to
     * @return the comparator value, that is less than zero if {@code other.published} time is
     * before this {@code published} time, zero if they are equal, or greater than zero if
     * {@code other.published} is after  this {@code published} time
     */
    @Override
    public int compareTo(Content other) {
        return other.published.compareTo(published);
    }

    @Override
    public FeedId getFeedId() {
        return source.feedId();
    }

    @Override
    public String getProviderId() {
        return source.feedId().getProviderId();
    }
}
