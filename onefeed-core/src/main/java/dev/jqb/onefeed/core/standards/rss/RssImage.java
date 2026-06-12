package dev.jqb.onefeed.core.standards.rss;

import java.util.Optional;

/**
 * A channel image in RSS.
 *
 * @see <a href="https://www.rssboard.org/rss-specification#ltimagegtSubelementOfLtchannelgt">RSS 2.0 Specification</a
 */
public interface RssImage {

    /**
     * Gets the URL of a GIF, JPEG, or PNG image that represents the channel
     */
    String getRssImageUrl();

    /**
     * Gets a description of the image, used in the {@code alt} attribute of the HTML {@code <img>} tag
     * when the channel is rendered in HTML.
     */
    String getRssImageTitle();

    /**
     * Gets the URL of the channel site.
     * @see <a href="https://www.rssboard.org/rss-specification#ltimagegtSubelementOfLtchannelgt">RSS 2.0 Specification</a
     */
    String getRssImageLink();

    /**
     * Gets the width of the image in pixels. For strict adherence to RSS 2.0, the maximum is 144.
     *
     * @see <a href="https://www.rssboard.org/rss-specification#ltimagegtSubelementOfLtchannelgt">RSS 2.0 Specification</a
     */
    default Integer getRssImageWidth() {
        return null;
    }

    /**
     * Gets the height of the image in pixels. For strict adherence to RSS 2.0, the maximum is 400.
     *
     * @see <a href="https://www.rssboard.org/rss-specification#ltimagegtSubelementOfLtchannelgt">RSS 2.0 Specification</a
     */
    default Integer getRssImageHeight() {
        return null;
    }

    /**
     * Gets the text that's included in the {@code title} attribute of the link formed around the
     * image when it's rendered in HTML.
     *
     * @see <a href="https://www.rssboard.org/rss-specification#ltimagegtSubelementOfLtchannelgt">RSS 2.0 Specification</a
     */
    default String getRssImageDescription() {
        return null;
    }
}
