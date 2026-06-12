package dev.jqb.onefeed.core.standards.rss;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A text input box that can be displayed with the channel
 *
 * @see <a href="https://www.rssboard.org/rss-specification#lttextinputgtSubelementOfLtchannelgt">RSS 2.0 Specification</a>
 */
@Getter
@Setter
@ToString
public class RssTextInput {

    /**
     * The label of the submit button in the text input area
     */
    private String title;

    /**
     * An explanation of the text input area
     */
    private String description;

    /**
     * The name of the text object in the text input area
     */
    private String name;

    /**
     * The URL of the CGI script that processes text input requests
     */
    private String link;

    /**
     * Constructs a new {@code RssTextInput} with the given values.
     * @param title the label of the submit button in the text input area
     * @param description an explanation of the text input area
     * @param name the name of the text object in the text input area
     * @param link the URL of the CGI script that processes text input requests
     */
    public RssTextInput(String title, String description, String name, String link) {
        this.title = title;
        this.description = description;
        this.name = name;
        this.link = link;
    }
}
