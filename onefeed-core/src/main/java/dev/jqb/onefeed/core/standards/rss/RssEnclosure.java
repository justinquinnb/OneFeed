package dev.jqb.onefeed.core.standards.rss;

import jakarta.activation.MimeType;

/**
 * A media object that is attached to an RSS item
 *
 * @see <a href="https://www.rssboard.org/rss-specification#ltenclosuregtSubelementOfLtitemgt">RSS 2.0 Specification</a>
 */
public interface RssEnclosure {

    /**
     * Gets the URL where the enclosure is located
     */
    String getRssEnclosureUrl();

    /**
     * Gets the size of the enclosure is in bytes
     */
    int getRssEnclosureLength();

    /**
     * Gets the MIME type of the enclosure
     */
    MimeType getRssEnclosureType();

}
