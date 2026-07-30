package dev.jqb.onefeed.compat.rss.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * A media object that is attached to an RSS item
 *
 * @see <a href="https://www.rssboard.org/rss-specification#ltenclosuregtSubelementOfLtitemgt">RSS 2.0 Specification</a>
 */
@JsonRootName("enclosure")
public interface RssEnclosure {

    /**
     * Gets the URL where the enclosure is located
     */
    @JacksonXmlProperty(isAttribute = true, localName = "url")
    String getRssEnclosureUrl();

    /**
     * Gets the size of the enclosure is in bytes
     */
    @JacksonXmlProperty(isAttribute = true, localName = "length")
    int getRssEnclosureLength();

    /**
     * Gets the MIME type of the enclosure
     */
    @JacksonXmlProperty(isAttribute = true, localName = "type")
    String getRssEnclosureType();
}
