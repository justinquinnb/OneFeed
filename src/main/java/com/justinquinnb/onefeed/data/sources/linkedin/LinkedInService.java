package com.justinquinnb.onefeed.data.sources.linkedin;

import com.justinquinnb.onefeed.data.model.content.Content;
import com.justinquinnb.onefeed.data.model.source.APIEndpoint;

public class LinkedInService extends APIEndpoint {
    private static final String baseUrl = "";
    private static final String sourceName = "LinkedIn";

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
    public String getSourceName() {
        return sourceName;
    }
}
