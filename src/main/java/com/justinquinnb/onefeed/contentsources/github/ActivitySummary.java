package com.justinquinnb.onefeed.contentsources.github;

import com.justinquinnb.onefeed.data.model.content.Content;
import com.justinquinnb.onefeed.data.model.content.details.Producer;
import com.justinquinnb.onefeed.data.model.content.details.Platform;

import java.time.Instant;
import java.util.Arrays;

/**
 * A type of content that describes something(s) the producer has done on GitHub. In cases with multiple recent actions,
 * this effectively becomes a summary of them.
 */
public class ActivitySummary extends Content {
    private String[] actionUrls;
    private String summary;

    public ActivitySummary(
            Instant timestamp,
            Producer actor,
            Platform source,
            String[] actionUrls,
            String summary
    ) {
        super(timestamp, actor, source);
        this.actionUrls = actionUrls;
        this.summary = summary;
    }

    public String[] getActionUrls() {
        return Arrays.copyOf(actionUrls, actionUrls.length);
    }

    public String getText() {
        return summary;
    }

    public String[] getAttachments() {
        return null;
    }
}