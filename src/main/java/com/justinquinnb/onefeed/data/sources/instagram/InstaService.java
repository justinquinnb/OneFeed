package com.justinquinnb.onefeed.data.sources.instagram;

import com.justinquinnb.onefeed.data.model.content.Content;
import com.justinquinnb.onefeed.data.model.source.APIEndpoint;

import java.time.Instant;

public class InstaService extends APIEndpoint {
    private static final String baseUrl = "";
    private static final String sourceName = "Instagram";

    @Override
    protected String getBaseUrl() {
        return baseUrl;
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
    public String getSourceName() {
        return sourceName;
    }
}
