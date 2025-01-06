package content.types;

import content.details.Platform;
import content.details.Profile;
import content.types.Content;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * A type of content that contains text and optional attachments,
 * posted by a user on some feed.
 */
public class Post extends Content {
    private String postUrl;
    private String caption;
    private String[] attachmentUrls;

    public Post(
        LocalDateTime timestamp,
        Profile actor,
        Platform source,
        String postUrl,
        String caption,
        String[] attachmentUrls
    ) {
        super(timestamp, actor, source);
        this.postUrl = postUrl;
        this.caption = caption;
        this.attachmentUrls = attachmentUrls;
    }

    public String getPostUrl() {
        return this.postUrl;
    }

    public String getText() {
        return caption;
    }

    public String[] getAttachments() {
        return Arrays.copyOf(attachmentUrls, attachmentUrls.length);
    }
}