package dev.jqb.onefeed.core.compat.rss;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * A channel image in RSS.
 *
 * @see <a href="https://www.rssboard.org/rss-specification#ltimagegtSubelementOfLtchannelgt">RSS 2.0 Specification</a>
 */
@JsonRootName("image")
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface RssImage {

    /**
     * Gets the URL of a GIF, JPEG, or PNG image that represents the channel
     */
    @JsonProperty("url")
    String getRssImageUrl();

    /**
     * Gets a description of the image, used in the {@code alt} attribute of the HTML {@code <img>} tag
     * when the channel is rendered in HTML.
     */
    @JsonProperty("title")
    String getRssImageTitle();

    /**
     * Gets the URL of the channel site.
     * @see <a href="https://www.rssboard.org/rss-specification#ltimagegtSubelementOfLtchannelgt">RSS 2.0 Specification</a>
     */
    @JsonProperty("link")
    String getRssImageLink();

    /**
     * Gets the width of the image in pixels. For strict adherence to RSS 2.0, the maximum is 144.
     *
     * @see <a href="https://www.rssboard.org/rss-specification#ltimagegtSubelementOfLtchannelgt">RSS 2.0 Specification</a>
     */
    @JsonProperty("width")
    default Integer getRssImageWidth() {
        return null;
    }

    /**
     * Gets the height of the image in pixels. For strict adherence to RSS 2.0, the maximum is 400.
     *
     * @see <a href="https://www.rssboard.org/rss-specification#ltimagegtSubelementOfLtchannelgt">RSS 2.0 Specification</a>
     */
    @JsonProperty("height")
    default Integer getRssImageHeight() {
        return null;
    }

    /**
     * Gets the text that's included in the {@code title} attribute of the link formed around the
     * image when it's rendered in HTML.
     *
     * @see <a href="https://www.rssboard.org/rss-specification#ltimagegtSubelementOfLtchannelgt">RSS 2.0 Specification</a>
     */
    @JsonProperty("description")
    default String getRssImageDescription() {
        return null;
    }
}
