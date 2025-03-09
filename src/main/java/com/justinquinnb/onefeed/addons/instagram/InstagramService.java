package com.justinquinnb.onefeed.addons.instagram;

import com.justinquinnb.onefeed.content.BasicContent;
import com.justinquinnb.onefeed.content.details.BasicPlatform;
import com.justinquinnb.onefeed.content.details.ContentSourceId;
import com.justinquinnb.onefeed.content.details.Platform;
import com.justinquinnb.onefeed.customization.addon.contentsource.ContentSource;

import java.time.Instant;

/**
 * Provides a means of accessing Instagram posts through Meta's Instagram Platform API.
 *
 * @see <a href="https://developers.facebook.com/docs/instagram-platform">Meta's Instagram Platform API Documentation</a>
 */
public class InstagramService extends ContentSource {
    private static final Platform PLATFORM_INFO = new BasicPlatform("https://instagram.com", "Instagram", "@");

    public InstagramService(ContentSourceId id) {
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
