package dev.jqb.onefeed.core.content;

import dev.jqb.onefeed.core.feed.Feed;
import dev.jqb.onefeed.core.feed.FeedAttribution;
import dev.jqb.onefeed.core.feed.FeedId;
import dev.jqb.onefeed.core.feed.FeedIdentifiable;
import dev.jqb.onefeed.core.platform.ExternalRef;
import java.time.Instant;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jspecify.annotations.Nullable;

/**
 * The minimum required data for of a piece of content.
 */
@ToString
@NoArgsConstructor
public abstract class Content implements FeedIdentifiable, Comparable<Content> {

    /**
     * Gets a unique key for {@code this} content on OneFeed.
     * @return a unique key for this content on OneFeed
     */
    public final ContentKey getKey() {
        return new ContentKey(getFeedId(), getExternalRef().id());
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
    public final int compareTo(Content other) {
        return other.getPublished().compareTo(this.getPublished());
    }

    @Override
    public final FeedId getFeedId() {
        return this.getSource().feedId();
    }

    @Override
    public final String getProviderId() {
        return this.getSource().feedId().getProviderId();
    }

    /**
     * Gets all information pertaining to the {@link Feed} {@code this} {@code Content} came from.
     */
    public abstract FeedAttribution getSource();

    /**
     * Sets the {@link FeedAttribution} that indicates where {@code this} {@code Content} came from.
     * @param source the {@code FeedAttribution} indicating this {@code Content}'s source
     */
    public abstract void setSource(FeedAttribution source);

    /**
     * Gets the means of accessing this {@code Content} on the source platform.
     */
    public abstract ExternalRef getExternalRef();

    /**
     * Sets the means of accessing {@code this} {@code Content} on the source platform.
     * @param externalRef the external reference to assign to {@code this} {@code Content}
     */
    public abstract void setExternalRef(ExternalRef externalRef);

    /**
     * Gets the time at which {@code this} {@code Content} was published on its source feed.
     */
    public abstract Instant getPublished();

    /**
     * Sets the time at which {@code this} {@code Content} was published on its source feed.
     * @param published the time at which {@code this} {@code Content} was published on its source
     * {@link Feed}
     */
    public abstract void setPublished(Instant published);

    /**
     * Gets the cursor pointing to the next page of content after {@code this} piece (or some
     * equivalent means), if known, on the originating platform's API.
     */
    public abstract String getNextPageCursor();

    /**
     * Sets the cursor pointing to the next page of content after {@code this} piece (or some
     * equivalent means), if known, on the originating platform's API.
     * @param nextPageCursor the cursor pointing to the next page of content after {@code this}
     *                       piece
     */
    public abstract void setNextPageCursor(@Nullable String nextPageCursor);

    /**
     * Gets the IDs of the authors of {@code this} content on the source platform.
     */
    public abstract List<String> getAuthorIds();

    /**
     * Sets the IDs of the authors of {@code this} content on the source platform.
     * @param authorIds the IDs of the authors of {@code this} content on the source platform
     */
    public abstract void setAuthorIds(List<String> authorIds);
}
