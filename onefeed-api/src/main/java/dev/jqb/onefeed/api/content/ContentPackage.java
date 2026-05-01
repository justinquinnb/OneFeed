package dev.jqb.onefeed.api.content;

import java.time.Instant;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * A package of content associated with the timestamp it was generated at.
 *
 * @param <Out> the type of {@link RawContent} contained in this response
 */
@Getter @Setter
public class ContentPackage<Out extends RawContent> {
    /**
     * The requested content, normalized and ready for distribution
     */
    private List<Out> content;

    /**
     * The time at which {@code this}  was generated
     */
    private Instant generated;
}
