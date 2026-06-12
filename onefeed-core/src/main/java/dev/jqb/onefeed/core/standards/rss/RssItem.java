package dev.jqb.onefeed.core.standards.rss;

import java.time.Instant;
import java.util.List;

/**
 * Content that can be represented as an RSS 2.0 item
 *
 * @see <a href="https://www.rssboard.org/rss-specification#hrelementsOfLtitemgt">RSS 2.0 Specification</a>
 */
public interface RssItem {

    /**
     * Gets the minimum RSS 2.0 item elements required to represent this content. Contains the
     * title, description, or both.
     *
     * @return the minimum RSS 2.0 item elements required to represent this content
     */
    MinimumRssItemElements getRssItemMinimumRssItemElements();

    /**
     * Gets the (source) URL of the item.
     */
    default String getRssItemLink() {
        return null;
    };

    /**
     * Gets the email address of the author of the item.
     *
     * @see <a href="https://www.rssboard.org/rss-specification#ltauthorgtSubelementOfLtitemgt">RSS 2.0 Specification</a>
     */
    default String getRssItemAuthor() {
        return null;
    }

    /**
     * Gets the categories the item is included in.
     */
    default List<RssCategory> getRssItemCategories() {
        return List.of();
    }

    /**
     * Gets the URL of a page for comments relating to the item.
     *
     * @see <a href="https://www.rssboard.org/rss-specification#ltcommentsgtSubelementOfLtitemgt">RSS 2.0 Specification</a>
     */
    default String getRssItemComments() {
        return null;
    }

    /**
     * Gets the media object that is attached to the item.
     */
    default RssEnclosure getRssItemEnclosure() {
        return null;
    }

    /**
     * Gets the string that uniquely identifies the item.
     *
     * @see <a href="https://www.rssboard.org/rss-specification#ltguidgtSubelementOfLtitemgt">RSS 2.0 Specification</a>
     */
    RssGuid getRssItemGuid();

    /**
     * Gets the publication date of the item.
     *
     * @see <a href="https://www.rssboard.org/rss-specification#ltpubdategtSubelementOfLtitemgt">RSS 2.0 Specification</a>
     */
    Instant getRssItemPubDate();

    /**
     * Gets the RSS channel that the item came from.
     */
    RssSource getRssItemChannelSource();
}
