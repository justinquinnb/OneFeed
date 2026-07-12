package dev.jqb.onefeed.core.compat.rss;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jspecify.annotations.Nullable;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import tools.jackson.dataformat.xml.annotation.JacksonXmlText;

/**
 * A category that an RSS item or channel may belong to
 *
 * @see <a href="https://www.rssboard.org/rss-specification#ltcategorygtSubelementOfLtitemgt">RSS 2.0 Specification</a>
 */
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName("category")
public class RssCategory {

    /**
     * The category's name
     */
    @Getter
    @JacksonXmlText
    private String value;

    /**
     * The domain of the category, if any.
     */
    @Nullable
    @JacksonXmlProperty(isAttribute = true)
    private String domain;

    /**
     * Constructs a new {@code RssCategory} with the given value and domain.
     * @param value the category's name
     * @param domain the category's domain, if any
     */
    public RssCategory(String value, @Nullable String domain) {
        this.value = value;
        this.domain = domain;
    }

    /**
     * Constructs a new {@code RssCategory} with the given value.
     * @param value the category's name
     */
    public RssCategory(String value) {
        this(value, null);
    }

    /**
     * Gets the domain of the category, if any.
     * @return the domain of the category, if any
     */
    public Optional<String> getDomain() {
        return Optional.ofNullable(domain);
    }
}
