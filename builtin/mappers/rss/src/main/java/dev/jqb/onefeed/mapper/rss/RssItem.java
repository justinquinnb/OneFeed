package dev.jqb.onefeed.mapper.rss;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.time.Instant;
import java.util.List;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

/**
 * Content that can be represented as an RSS 2.0 item
 *
 * @see <a href="https://www.rssboard.org/rss-specification#hrelementsOfLtitemgt">RSS 2.0 Specification</a>
 */
@JsonRootName("item")
public interface RssItem {

    /**
     * Gets the minimum RSS 2.0 item elements required to represent this content. Contains the
     * title, description, or both.
     *
     * @return the minimum RSS 2.0 item elements required to represent this content
     */
    @JsonUnwrapped
    MinimumRssItemElements getRssItemMinimumRssItemElements();

    /**
     * Gets the (source) URL of the item.
     */
    @JsonProperty("link")
    default String getRssItemLink() {
        return null;
    };

    /**
     * Gets the email address of the author of the item.
     *
     * @see <a href="https://www.rssboard.org/rss-specification#ltauthorgtSubelementOfLtitemgt">RSS 2.0 Specification</a>
     */
    @JsonProperty("author")
    default String getRssItemAuthor() {
        return null;
    }

    /**
     * Gets the categories the item is included in.
     */
    @JacksonXmlElementWrapper(useWrapping = false)
    default List<RssCategory> getRssItemCategories() {
        return List.of();
    }

    /**
     * Gets the URL of a page for comments relating to the item.
     *
     * @see <a href="https://www.rssboard.org/rss-specification#ltcommentsgtSubelementOfLtitemgt">RSS 2.0 Specification</a>
     */
    @JsonProperty("comments")
    default String getRssItemComments() {
        return null;
    }

    /**
     * Gets the media object that is attached to the item.
     */
    @JsonProperty("enclosure")
    @JsonSerialize(as = RssEnclosure.class)
    default RssEnclosure getRssItemEnclosure() {
        return null;
    }

    /**
     * Gets the string that uniquely identifies the item.
     *
     * @see <a href="https://www.rssboard.org/rss-specification#ltguidgtSubelementOfLtitemgt">RSS 2.0 Specification</a>
     */
    @JsonProperty("guid")
    @JsonSerialize(as = RssGuid.class)
    RssGuid getRssItemGuid();

    /**
     * Gets the publication date of the item.
     *
     * @see <a href="https://www.rssboard.org/rss-specification#ltpubdategtSubelementOfLtitemgt">RSS 2.0 Specification</a>
     */
    @JsonProperty("pubDate")
    @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "EEE, dd MMM yyyy HH:mm:ss z",
        locale = "en",
        timezone = "GMT"
    )
    Instant getRssItemPubDate();

    /**
     * Gets the RSS channel that the item came from.
     */
    @JsonProperty("source")
    @JsonSerialize(as = RssSource.class)
    RssSource getRssItemChannelSource();
}
