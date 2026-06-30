package dev.jqb.onefeed.core.feed;

import dev.jqb.onefeed.core.actor.Actor;
import dev.jqb.onefeed.core.actor.ActorKey;
import dev.jqb.onefeed.core.content.Content;
import dev.jqb.onefeed.core.platform.Platform;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jspecify.annotations.Nullable;

/**
 * A complete collection of content, their authors, and (optionally) the platform that it came from
 */
@Getter
@Setter
@ToString
public class FeedResponse {

    /**
     * The authors of the content, indexed by their {@link ActorKey}
     */
    private Map<ActorKey, Actor> authors;

    /**
     * The platform that the content came from
     */
    private Platform platform;

    /**
     * The content, in descending chronological order
     */
    private List<? extends Content> content;

    /**
     * The cursor that can be used to retrieve the next batch of content
     */
    private FeedCursor cursor;

    /**
     * Constructs a new {@code Aggregation} with the given content and aggregate cursor to the next
     * batch/page.
     *
     * @param authors the authors of the aggregated content, indexed by their {@link ActorKey}
     * @param platform the platform that the aggregated content came from
     * @param content the aggregated content
     * @param cursor the cursor to the next batch/page of feed content
     */
    public FeedResponse(
        Map<ActorKey, Actor> authors,
        @Nullable Platform platform,
        List<? extends Content> content,
        @Nullable FeedCursor cursor
    ) {
        this.authors = authors;
        this.platform = platform;
        ArrayList<? extends Content> sortedContent = new ArrayList<>(content);
        sortedContent.sort(Content::compareTo);
        this.content = sortedContent;
        this.cursor = cursor;
    }
}
