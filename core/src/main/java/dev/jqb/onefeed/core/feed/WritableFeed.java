package dev.jqb.onefeed.core.feed;

import dev.jqb.onefeed.core.content.Content;
import dev.jqb.onefeed.core.content.OneFeedContent;

/**
 * A feed that supports writing content to it
 * @param <C> the type of content that the feed natively consumes
 */
public interface WritableFeed<C extends Content> extends Feed {

    // TODO finalize these signatures... these are temporary for now
    /*
    Consider a List<A extends Actor> or List<OneFeedActor> parameter "as" to indicate who to post as?
    This would only work on some platforms though. Challenge is that every feed's content will require
    different information prior to posting. Perhaps the strategy is to only accept the ingestable DTOs
     */

    /**
     * Writes the given {@code content} to {@code this} feed.
     * @param content the content to write to the feed
     * @throws UnpermittedOperationException if the feed does not currently permit writing
     */
    default void writeContent(C content) throws UnpermittedOperationException {
        if (!getPermissions().canRead()) {
            throw new UnpermittedOperationException("The feed does not permit writing at this time");
        }
        publishContent(content);
    }

    /**
     * Publishes the given {@code content} to {@code this} feed.
     * @param content the content to write to the feed
     */
    void publishContent(C content);

    /**
     * Publishes the given {@code content} to {@code this} feed.
     * @param content the content to write to the feed
     */
    void publishContent(OneFeedContent content);
}
