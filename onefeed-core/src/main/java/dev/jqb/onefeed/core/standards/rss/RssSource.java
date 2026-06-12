package dev.jqb.onefeed.core.standards.rss;

/**
 * The RSS channel an item came from
 *
 * @see <a href="https://www.rssboard.org/rss-specification#ltsourcegtSubelementOfLtitemgt">RSS 2.0 Specification</a>
 */
public interface RssSource {

    /**
     * The name of the RSS channel (or feed) that the item came from, derived from its
     * {@code <title>}.
     */
    String getRssSourceValue();

    /**
     * The URL of the that feed as RSS 2.0 XML.
     */
    String getRssSourceUrl();
}
