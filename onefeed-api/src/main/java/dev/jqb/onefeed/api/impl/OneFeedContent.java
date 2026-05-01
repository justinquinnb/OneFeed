package dev.jqb.onefeed.api.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.jqb.onefeed.api.content.NormalizedContent;
import dev.jqb.onefeed.api.feed.Profile;
import dev.jqb.onefeed.api.feed.SourceInfo;
import java.time.Instant;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The default implementation of {@link NormalizedContent}
 */
@NoArgsConstructor
@Getter
@Setter
public class OneFeedContent extends NormalizedContent {

    /**
     * The title of the content, using CommonMark-Flavored Markdown for any light formatting.
     *
     * @see <a href="https://spec.commonmark.org/0.31.2/">CommonMark Spec</a>
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;

    /**
     * The primary, textual content, using CommonMark-Flavored Markdown for any light formatting.
     *
     * @see <a href="https://spec.commonmark.org/0.31.2/">CommonMark Spec</a>
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String body;

    /**
     * Any attached media, such as links, videos, images, or files, in their desired order of
     * presentation or priority (high/first to low/last)
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Media> media;

    /**
     * The quantity of whatever reaction type is primary on the source platform, the semantics of
     * which are discernable via interpretation of the content's {@link #source} by the client
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int primaryReactionCount;

    public OneFeedContent(String text, List<Media> media, int primaryReactionCount,
        SourceInfo<Profile> source, Instant published) {
        super(source, published);
    }
}
