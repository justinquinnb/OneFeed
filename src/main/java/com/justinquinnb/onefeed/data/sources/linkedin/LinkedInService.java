package com.justinquinnb.onefeed.data.sources.linkedin;

import com.justinquinnb.onefeed.data.model.content.Content;
import com.justinquinnb.onefeed.data.model.content.details.SourceInfo;
import com.justinquinnb.onefeed.data.model.source.ContentSource;
import java.time.Instant;

public class LinkedInService implements ContentSource {
    private static final SourceInfo INFO = new SourceInfo("https://linkedin.com", "LinkedIn", "@");
    private final String ID;

    public LinkedInService(String id) {
        ID = id;
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

    @Override
    public String getId() {
        return ID;
    }
}
