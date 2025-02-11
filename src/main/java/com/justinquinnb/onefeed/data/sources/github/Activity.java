package com.justinquinnb.onefeed.data.sources.github;

import com.justinquinnb.onefeed.data.model.content.Content;
import com.justinquinnb.onefeed.data.model.content.details.SourceInfo;
import com.justinquinnb.onefeed.data.model.content.details.Producer;

import java.time.Instant;
import java.util.Arrays;

/**
 * A type of content that describes something an actor has done.
 * Effectively, a collection of activity would look like an event log.
 */
public class Activity extends Content {
    private String[] actionUrls;
    private String summary;

    public Activity(
            Instant timestamp,
            Producer actor,
            SourceInfo source,
            String[] actionUrls,
            String description
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