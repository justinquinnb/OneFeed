package com.justinquinnb.onefeed.contentsources.linkedin;

import com.justinquinnb.onefeed.data.model.content.Content;
import com.justinquinnb.onefeed.data.model.content.details.Platform;
import com.justinquinnb.onefeed.data.model.source.ContentSource;

import java.time.Instant;

/**
 * Provides a means of accessing LinkedIn posts through LinkedIn's API.
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin">LinkedIn's API Documentation</a>
 */
public class LinkedInService extends ContentSource {
    private static final Platform INFO = new Platform("https://linkedin.com", "LinkedIn", "@");

    public LinkedInService(String id) {
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
