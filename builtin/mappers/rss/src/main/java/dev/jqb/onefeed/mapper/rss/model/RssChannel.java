package dev.jqb.onefeed.mapper.rss.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSerializeAs;
import java.time.DayOfWeek;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * A channel of RSS items
 * @see <a href="https://www.rssboard.org/rss-specification#requiredChannelElements>RSS 2.0 Specification</a
 */
@JsonRootName("channel")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"title", "link", "description", "language", "copyright", "managingEditor",
    "webMaster", "pubDate", "lastBuildDate", "category", "generator", "docs", "ttl", "image",
    "textInput", "skipHours", "skipDays", "atom:link", "item"}) // To ensure items are always last, as suggested by W3, which Jackson isn't doing by default
public interface RssChannel {
    /**
     * Gets the name of the channel. It's how people refer to the channel/service. If you have an
     * HTML website that contains the same info as the RSS file, the title of the channel should
     * match the title of that website.
     */
    @JsonProperty("title")
    String getRssChannelTitle();

    /**
     * Gets the URL to the HTML website corresponding to the channel.
     */
    @JsonProperty("link")
    String getRssChannelLink();

    /**
     * Gets a phrase or sentence describing the channel
     */
    @JsonProperty("description")
    String getRssChannelDescription();

    /**
     * Gets the language the channel is written in.
     */
    @JsonProperty("language")
    default Locale getRssChannelLanguage() {
        return null;
    }

    /**
     * Gets the copyright notice for content in the channel.
     */
    @JsonProperty("copyright")
    default String getRssChannelCopyright() {
        return null;
    }

    /**
     * Gets the email address for the person responsible for technical issues relating to the
     * channel.
     */
    @JsonProperty("managingEditor")
    default String getRssChannelWebMaster() {
        return null;
    }

    /**
     * Gets the publication date of the item.
     *
     * @see <a href="https://www.rssboard.org/rss-specification#ltpubdategtSubelementOfLtitemgt>RSS 2.0 Specification</a
     */
    @JsonProperty("pubDate")
    @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "EEE, dd MMM yyyy HH:mm:ss z",
        locale = "en",
        timezone = "GMT"
    )
    default Instant getRssChannelPubDate() {
        return null;
    }

    /**
     * Gets the last time the content of the channel changed.
     */
    @JsonProperty("lastBuildDate")
    @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "EEE, dd MMM yyyy HH:mm:ss z",
        locale = "en",
        timezone = "GMT"
    )
    default Instant getRssChannelLastBuildDate() {
        return Instant.now();
    }

    /**
     * Gets the categories the channel is included in.
     */
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "category")
    default List<RssCategory> getRssChannelCategories() {
        return List.of();
    }

    /**
     * Gets the string indicating the program used to generate the channel.
     */
    @JsonProperty("generator")
    default String getRssChannelGenerator() {
        return "OneFeed";
    }

    /**
     * Gets the URL that points to the documentation for the format used in the RSS file.
     */
    @JsonProperty("docs")
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
    @JsonProperty("ttl")
    default String getRssChannelTtl() {
        return null;
    }

    /**
     * Gets the image that can be displayed with the channel.
     */
    @JsonProperty("image")
    @JsonSerializeAs(RssImage.class)
    default RssImage getRssChannelImage() {
        return null;
    }

    // PICS rating intentionally left out bc it's deprecated as a standard

    /**
     * Gets a text box that can be displayed with the channel.
     */
    @JsonProperty("textInput")
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
    @JsonProperty("skipHours")
    @JacksonXmlProperty(localName = "hour")
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

    /**
     * Produces a title-cased list of the days from {@link #getRssChannelSkipDays()} for exact
     * adherence to the RSS spec.
     */
    @JsonProperty("skipDays")
    @JacksonXmlProperty(localName = "day")
    private List<String> getRssChannelSkipDaysAsString() {
        List<String> days = new ArrayList<>(getRssChannelSkipDays().size());
        for (DayOfWeek day : getRssChannelSkipDays()) {
            String dayName = day.toString();
            dayName = dayName.replace(dayName.charAt(0), Character.toUpperCase(dayName.charAt(0)));
            days.add(dayName);
        }
        return days;
    }

    @JsonProperty("atom:link")
    RssAtomLink getRssChannelAtomLink();

    /**
     * Gets the items in the channel.
     */
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "item")
    @JsonSerialize(contentAs = RssItem.class)
    List<RssItem> getRssChannelItems();
}
