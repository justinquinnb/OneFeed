package dev.jqb.onefeed.core.standards.rss;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jspecify.annotations.Nullable;

/**
 * A "container" of the minimum required RSS "item" elements
 *
 * @see <a href="https://www.rssboard.org/rss-specification#hrelementsOfLtitemgt">RSS 2.0 Specification</a>
 */
@Setter
@Getter
@ToString
public class MinimumRssItemElements {

    /**
     * The title of the item
     */
    @Nullable
    private String title;

    /**
     * The item synopsis
     */
    @Nullable
    private String description;

    /**
     * Constructs a new {@code MinimumRssItemElements} with the given title and/or description. At
     * least one of the two must be non-null.
     *
     * @param title the title of the item
     * @param description the description of the item
     */
    public MinimumRssItemElements(@Nullable String title, @Nullable String description) {
        if (title == null && description == null) {
            throw new IllegalArgumentException("Both title and description cannot be null");
        }

        this.title = title;
        this.description = description;
    }
}
