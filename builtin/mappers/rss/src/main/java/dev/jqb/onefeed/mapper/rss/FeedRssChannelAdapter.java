package dev.jqb.onefeed.mapper.rss;

import dev.jqb.onefeed.core.content.Content;
import dev.jqb.onefeed.core.feed.Feed;
import dev.jqb.onefeed.core.feed.ReadableFeed;
import java.util.List;
import java.util.function.Function;
import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Flux;

/**
 * Adapter for {@link Feed}s to {@link RssChannel}s via a mapper {@link Function} that provides
 * the necessary {@link RssItem}-compatible content
 */
@Getter
@Setter
public class FeedRssChannelAdapter<C extends Content> implements RssChannel {

    /**
     * The feed to adapt
     */
    private ReadableFeed<C> feed;

    /**
     * A mapper {@link Function} capable of converting the {@link #feed}'s output into an
     * {@link RssItem}-compatible type
     */
    private Function<C, ? extends RssItem> mapper;

    /**
     * The title of the feed
     */
    private String title;

    /**
     * A description of the feed
     */
    private String description;

    /**
     * A link to where {@code this} RSS feed can be found
     */
    private String href;

    /**
     * The maximum number of items to include in the feed
     */
    private int maxItemCount;

    /**
     * Constructs a new {@code FeedRssChannelAdapter} for the given feed.
     * @param feed the feed to adapt
     * @param mapper a mapper {@link Function} capable of converting the {@code feed}'s output
     *                     into an {@link RssItem}-compatible type
     * @param title the title of the feed
     * @param description a description of the feed
     * @param href a link to where {@code this} RSS feed can be found
     * @param maxItemCount the maximum number of items to include in the feed
     */
    public FeedRssChannelAdapter(
        ReadableFeed<C> feed,
        Function<C, ? extends RssItem> mapper,
        String title,
        String description,
        String href,
        int maxItemCount
    ) {
        this.feed = feed;
        this.mapper = mapper;
        this.title = title;
        this.description = description;
        this.href = href;
        this.maxItemCount = maxItemCount;
    }

    @Override
    public String getRssChannelTitle() {
        return title;
    }

    @Override
    public String getRssChannelLink() {
        return feed.getUrl();
    }

    @Override
    public String getRssChannelDescription() {
        return description;
    }

    @Override
    public RssAtomLink getRssChannelAtomLink() {
        return (this.href != null && !this.href.isEmpty()) ? new RssAtomLink(this.href) : null;
    }

    @Override
    public List<RssItem> getRssChannelItems() {
        Flux<RssItem> contentStream = feed.fetchRecentContent(maxItemCount).map(mapper);
        return contentStream.collectList().block();
    }
}
