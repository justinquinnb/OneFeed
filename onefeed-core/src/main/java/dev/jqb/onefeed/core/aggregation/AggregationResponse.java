package dev.jqb.onefeed.core.aggregation;

import dev.jqb.onefeed.core.actor.Actor;
import dev.jqb.onefeed.core.actor.ActorKey;
import dev.jqb.onefeed.core.content.Content;
import dev.jqb.onefeed.core.feed.FeedCursor;
import dev.jqb.onefeed.core.platform.Platform;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jspecify.annotations.Nullable;

/**
 * A complete aggregation of content, their authors, and (optionally) the platforms that they came
 * from
 */
@Getter
@Setter
@ToString
public class AggregationResponse {

    /**
     * The authors of the aggregated content, indexed by their {@link ActorKey}
     */
    private Map<ActorKey, Actor> authors;

    /**
     * The platforms that the aggregated content came from, indexed by the ID of the provider
     * exposing them
     */
    private Map<String, Platform> platforms;

    /**
     * The aggregated content, in descending chronological order
     */
    private List<? extends Content> content;

    /**
     * The cursor that can be used to retrieve the next batch of aggregated content
     */
    private FeedCursor aggregateCursor;

    /**
     * Constructs a new {@code Aggregation} with the given content and aggregate cursor to the next
     * batch/page.
     *
     * @param authors the authors of the aggregated content, indexed by their {@link ActorKey}
     * @param platforms the platforms that the aggregated content came from, indexed by the ID of
     *                  the provider exposing them
     * @param content the aggregated content
     * @param aggregateCursor the cursor to the next batch/page of aggregated content
     */
    public AggregationResponse(
        @Nullable Map<ActorKey, Actor> authors,
        @Nullable Map<String, Platform> platforms,
        List<? extends Content> content,
        @Nullable FeedCursor aggregateCursor
    ) {
        this.authors = authors;
        this.platforms = platforms;
        ArrayList<? extends Content> sortedContent = new ArrayList<>(content);
        sortedContent.sort(Content::compareTo);
        this.content = sortedContent;
        this.aggregateCursor = aggregateCursor;
    }
}
