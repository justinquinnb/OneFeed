package dev.jqb.onefeed.core.feed;

import dev.jqb.onefeed.core.actor.Actor;
import dev.jqb.onefeed.core.content.Content;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * A collection of changes to a feed's content and/or profile
 */
@Getter
@Setter
@Builder
public class FeedUpdate<C extends Content, A extends Actor> {

    /**
     * Any new content that was added to the feed
     */
    @Builder.Default
    private List<C> newContent = List.of();

    /**
     * Any content that was updated in the feed
     */
    @Builder.Default
    private List<C> updatedContent = List.of();

    /**
     * Any content that was removed from the feed
     */
    @Builder.Default
    private List<C> removedContent = List.of();

    /**
     * The updated profile of the feed's author
     */
    @Builder.Default
    private List<A> updatedAuthors = List.of();
}
