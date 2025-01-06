package com.justinquinnb.onefeed.data.sources.base.github;

import com.justinquinnb.onefeed.data.model.content.Content;
import com.justinquinnb.onefeed.data.model.content.details.Platform;
import com.justinquinnb.onefeed.data.model.content.details.Producer;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * A type of content that describes something an actor has done.
 * Effectively, a collection of activity would look like an event log.
 */
public class Activity extends Content {
    private String[] actionUrls;
    private String summary;

    public Activity(
            LocalDateTime timestamp,
            Producer actor,
            Platform source,
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
}