package dev.jqb.onefeed.core.compat.rss;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * The root element of an RSS file
 *
 * @see <a href="https://www.rssboard.org/rss-specification#whatIsRss">RSS 2.0 Specification</a>
 */
@Getter
@Setter
@NoArgsConstructor
@JsonRootName("rss")
public class Rss2File {
    @JacksonXmlProperty(isAttribute = true, localName = "version")
    private String version = "2.0";

    @JacksonXmlProperty(isAttribute = true, localName = "xmlns:atom")
    private String atomNamespace = "http://www.w3.org/2005/Atom";

    /**
     * The RSS channel the file contains
     */
    @JsonSerialize(as = RssChannel.class)
    private RssChannel channel;

    /**
     * Constructs a new {@code Rss2File} with the given channel.
     * @param channel the channel of the RSS file
     */
    public Rss2File(RssChannel channel) {
        this.version = version;
        this.channel = channel;
    }
}
