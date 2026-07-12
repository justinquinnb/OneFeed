package dev.jqb.onefeed.core.compat.rss;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSerializeAs;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import tools.jackson.dataformat.xml.annotation.JacksonXmlText;

/**
 * A string that uniquely identifies an RSS item for RSS aggregators
 *
 * @see <a href="https://www.rssboard.org/rss-specification#ltguidgtSubelementOfLtitemgt">RSS 2.0 Specification</a>
 */
@JsonRootName( "guid")
@JsonSerializeAs(RssGuid.class)
public interface RssGuid {

    /**
     * The uniquely identifying string
     */
    @JacksonXmlText
    String getRssGuidValue();

    /**
     * Whether the {@link #getRssGuidValue} value is a permalink. If {@code true}, RSS readers may assume
     * that it is a permalink to the item, that is, a url that can be opened in a Web broswer, that
     * points to the full item described by the parent {@code item} element.
     */
    @JacksonXmlProperty(isAttribute = true, localName = "isPermaLink")
    default boolean isRssGuidPermalink() {
        return true;
    }
}
