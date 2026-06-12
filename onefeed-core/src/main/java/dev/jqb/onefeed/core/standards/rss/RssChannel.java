package dev.jqb.onefeed.core.standards.rss;

import java.time.DayOfWeek;
import java.time.Instant;
import java.util.List;
import java.util.Locale;

/**
 * A channel of RSS items
 * @see <a href="https://www.rssboard.org/rss-specification#requiredChannelElements>RSS 2.0 Specification</a
 */
public interface RssChannel {
    /**
     * Gets the name of the channel. It's how people refer to the channel/service. If you have an
     * HTML website that contains the same info as the RSS file, the title of the channel should
     * match the title of that website.
     */
    String getRssChannelTitle();

    /**
     * Gets the URL to the HTML website corresponding to the channel.
     */
    String getRssChannelLink();

    /**
     * Gets a phrase or sentence describing the channel
     */
    String getRssChannelDescription();

    /**
     * Gets the language the channel is written in.
     */
    default Locale getRssChannelLanguage() {
        return null;
    }

    /**
     * Gets the copyright notice for content in the channel.
     */
    default String getRssChannelCopyright() {
        return null;
    }

    /**
     * Gets the email address for the person responsible for technical issues relating to the
     * channel.
     */
    default String getRssChannelWebMaster() {
        return null;
    }

    /**
     * Gets the publication date of the item.
     *
     * @see <a href="https://www.rssboard.org/rss-specification#ltpubdategtSubelementOfLtitemgt>RSS 2.0 Specification</a
     */
    default Instant getRssChannelPubDate() {
        return null;
    }

    /**
     * Gets the last time the content of the channel changed.
     */
    default Instant getRssChannelLastBuildDate() {
        return Instant.now();
    }

    /**
     * Gets the categories the channel is included in.
     */
    default List<RssCategory> getRssChannelCategories() {
        return List.of();
    }

    /**
     * Gets the string indicating the program used to generate the channel.
     */
    default String getRssChannelGenerator() {
        return "OneFeed";
    }

    /**
     * Gets the URL that points to the documentation for the format used in the RSS file.
     */
    default String getRssChannelDocs() {
        return "https://www.rssboard.org/rss-specification";
    }

    // TODO, consider implementing at some point
    // https://www.rssboard.org/rsscloud-interface#client
//    /**
//     * Gets the data necessary to register with a cloud and be notified of updates to the channel.
//     */
//    default RssCloud getRssChannelCloud() {
//        return null;
//    }

    /**
     * Gets the number of minutes that indicates how long the channel can be cached before
     * refreshing from the source.
     * @see <a href="https://www.rssboard.org/rss-specification#ltttlgtSubelementOfLtchannelgt>RSS 2.0 Specification</a
     */
    default String getRssChannelTtl() {
        return null;
    }

    /**
     * Gets the image that can be displayed with the channel.
     */
    default RssImage getRssChannelImage() {
        return null;
    }

    // PICS rating intentionally left out bc it's deprecated as a standard

    /**
     * Gets a text box that can be displayed with the channel.
     */
    default RssTextInput getRssChannelTextInput() {
        return null;
    }

    /**
     * Gets the hint for aggregators telling them which hours they can skip. This contains up to
     * 24 elements with values between 0 and 23, each representing a time in GMT when aggregators
     * may not read the channel.
     *
     * @see <a href="https://www.rssboard.org/rss-specification#requiredChannelElements>RSS 2.0 Specification</a
     */
    default List<Integer> getRssChannelSkipHours() {
        return List.of();
    }

    /**
     * Gets the hint for aggregators telling them which days they can skip. This contains up to
     * seven elements with day of week values each representing a day when aggregators may not read
     * the channel.
     *
     * @see <a href="https://www.rssboard.org/rss-specification#requiredChannelElements>RSS 2.0 Specification</a
     */
    default List<DayOfWeek> getRssChannelSkipDays() {
        return List.of();
    }
}
