package dev.jqb.onefeed.core.standards.rss;

/**
 * A string that uniquely identifies an RSS item for RSS aggregators
 *
 * @see <a href="https://www.rssboard.org/rss-specification#ltguidgtSubelementOfLtitemgt">RSS 2.0 Specification</a>
 */
public interface RssGuid {

    /**
     * The uniquely identifying string
     */
    String getRssGuidValue();

    /**
     * Whether the {@link #getRssGuidValue} value is a permalink. If {@code true}, RSS readers may assume
     * that it is a permalink to the item, that is, a url that can be opened in a Web broswer, that
     * points to the full item described by the parent {@code item} element.
     */
    default boolean isRssGuidPermalink() {
        return true;
    }
}
