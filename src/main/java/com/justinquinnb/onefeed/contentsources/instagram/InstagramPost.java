package com.justinquinnb.onefeed.contentsources.instagram;

import com.justinquinnb.onefeed.data.model.content.Content;
import com.justinquinnb.onefeed.data.model.content.details.Producer;
import com.justinquinnb.onefeed.data.model.content.details.Platform;

import java.time.Instant;
import java.util.Arrays;

/**
 * An Instagram post containing a caption, post URL, and attachments.
 */
public class InstagramPost extends Content {
    private String postUrl;
    private String caption;
    private String[] attachmentUrls;

    public InstagramPost(
        Instant timestamp,
        Producer actor,
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