package com.justinquinnb.onefeed.contentsources.linkedin;

import com.justinquinnb.onefeed.data.model.content.BasicContent;
import com.justinquinnb.onefeed.data.model.content.details.BasicPlatform;
import com.justinquinnb.onefeed.data.model.content.details.Platform;
import com.justinquinnb.onefeed.data.model.source.ContentSource;

import java.time.Instant;

/**
 * Provides a means of accessing LinkedIn posts through LinkedIn's API.
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin">LinkedIn's API Documentation</a>
 */
public class LinkedInService extends ContentSource {
    private static final Platform INFO = new BasicPlatform("https://linkedin.com", "LinkedIn", "@");

    public LinkedInService(String id) {
        super(id);
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

    @Override
    public Platform getSourceInfo() {
        return INFO;
    }
}
