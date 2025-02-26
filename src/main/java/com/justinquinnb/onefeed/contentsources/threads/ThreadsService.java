package com.justinquinnb.onefeed.contentsources.threads;

import com.justinquinnb.onefeed.data.model.content.Content;
import com.justinquinnb.onefeed.data.model.content.details.Platform;
import com.justinquinnb.onefeed.data.model.source.ContentSource;

import java.time.Instant;

/**
 * Provides a means of accessing Threads posts through Meta's Threads API.
 *
 * @see <a href="https://developers.facebook.com/docs/threads">Meta's Threads API Documentation</a>
 */
public class ThreadsService extends ContentSource {
    private static final Platform INFO = new Platform("https://threads.com", "Threads", "@");

    public ThreadsService(String id) {
        super(id);
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public Content[] getLatestContent(int count) {
        return new Content[0];
    }

    @Override
    public Content[] getLatestContent(int count, Instant[] betweenTimes) {
        return new Content[0];
    }

    @Override
    public Platform getSourceInfo() {
        return INFO;
    }
}