package com.justinquinnb.onefeed.addons.linkedin;

import com.justinquinnb.onefeed.content.BasicContent;
import com.justinquinnb.onefeed.content.details.BasicPlatform;
import com.justinquinnb.onefeed.content.details.ContentSourceId;
import com.justinquinnb.onefeed.content.details.Platform;
import com.justinquinnb.onefeed.customization.source.ContentSource;

import java.time.Instant;

/**
 * Provides a means of accessing LinkedIn posts through LinkedIn's API.
 *
 * @see <a href="https://learn.microsoft.com/en-us/linkedin">LinkedIn's API Documentation</a>
 */
public class LinkedInService extends ContentSource {
    private static final Platform PLATFORM_INFO = new BasicPlatform("https://linkedin.com", "LinkedIn", "@");

    public LinkedInService(ContentSourceId id) {
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
