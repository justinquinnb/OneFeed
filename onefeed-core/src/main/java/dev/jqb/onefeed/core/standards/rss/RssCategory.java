package dev.jqb.onefeed.core.standards.rss;

import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jspecify.annotations.Nullable;

/**
 * A category that an RSS item or channel may belong to
 *
 * @see <a href="https://www.rssboard.org/rss-specification#ltcategorygtSubelementOfLtitemgt">RSS 2.0 Specification</a>
 */
@Setter
@ToString
public class RssCategory {

    /**
     * The category's name
     */
    @Getter
    private String value;

    /**
     * The domain of the category, if any.
     */
    @Nullable
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
