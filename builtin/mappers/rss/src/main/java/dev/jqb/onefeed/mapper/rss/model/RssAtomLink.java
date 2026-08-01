package dev.jqb.onefeed.mapper.rss.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * The W3-advised {@code <atom:link>} element contained in an RSS channel.
 *
 * @see <a href="https://www.rssboard.org/rss-profile#namespace-elements-atom-link">RSS 2.0 Specification</a>
 */
@Getter
@SuppressWarnings("unused")
@JsonRootName("atom:link")
public class RssAtomLink {
    @JacksonXmlProperty(isAttribute = true)
    private String href;

    @JacksonXmlProperty(isAttribute = true)
    private String rel = "self";

    @JacksonXmlProperty(isAttribute = true)
    private String type = "application/rss+xml";

    public RssAtomLink(String href) {
        this.href = href;
    }
}
