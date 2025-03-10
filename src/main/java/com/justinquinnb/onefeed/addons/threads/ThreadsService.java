package com.justinquinnb.onefeed.addons.threads;

import com.justinquinnb.onefeed.content.BasicContent;
import com.justinquinnb.onefeed.content.details.BasicPlatform;
import com.justinquinnb.onefeed.content.details.ContentSourceId;
import com.justinquinnb.onefeed.content.details.Platform;
import com.justinquinnb.onefeed.customization.contentsource.ContentSource;

import java.time.Instant;

/**
 * Provides a means of accessing Threads posts through Meta's Threads API.
 *
 * @see <a href="https://developers.facebook.com/docs/threads">Meta's Threads API Documentation</a>
 */
public class ThreadsService extends ContentSource {
    private static final Platform PLATFORM_INFO = new BasicPlatform("https://threads.com", "Threads", "@");

    public ThreadsService(ContentSourceId id) {
        super(id, PLATFORM_INFO);
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public BasicContent[] getLatestContent(int count) {
        return new BasicContent[0];
    }

    @Override
    public BasicContent[] getLatestContent(int count, Instant[] betweenTimes) {
        return new BasicContent[0];
    }
}