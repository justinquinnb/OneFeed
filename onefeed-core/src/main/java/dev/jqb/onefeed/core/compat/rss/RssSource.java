package dev.jqb.onefeed.core.compat.rss;

import com.fasterxml.jackson.annotation.JsonRootName;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import tools.jackson.dataformat.xml.annotation.JacksonXmlText;

/**
 * The RSS channel an item came from
 *
 * @see <a href="https://www.rssboard.org/rss-specification#ltsourcegtSubelementOfLtitemgt">RSS 2.0 Specification</a>
 */
@JsonRootName("source")
public interface RssSource {

    /**
     * The name of the RSS channel (or feed) that the item came from, derived from its
     * {@code <title>}.
     */
    @JacksonXmlText
    String getRssSourceValue();

    /**
     * The URL of the that feed as RSS 2.0 XML.
     */
    @JacksonXmlProperty(isAttribute = true, localName = "url")
    String getRssSourceUrl();
}
