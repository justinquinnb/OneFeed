package com.justinquinnb.onefeed.data.contentsources.threads;

import com.justinquinnb.onefeed.data.model.content.Content;
import com.justinquinnb.onefeed.data.model.content.details.SourceInfo;
import com.justinquinnb.onefeed.data.model.source.ContentSource;

import java.time.Instant;

public class ThreadsService extends ContentSource {
    private static final SourceInfo INFO = new SourceInfo("https://threads.com", "Threads", "@");

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
    public SourceInfo getSourceInfo() {
        return INFO;
    }
}