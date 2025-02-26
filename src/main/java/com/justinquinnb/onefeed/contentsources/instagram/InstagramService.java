package com.justinquinnb.onefeed.contentsources.instagram;

import com.justinquinnb.onefeed.data.model.content.Content;
import com.justinquinnb.onefeed.data.model.content.details.Platform;
import com.justinquinnb.onefeed.data.model.source.ContentSource;

import java.time.Instant;

/**
 * Provides a means of accessing Instagram posts through Meta's Instagram Platform API.
 *
 * @see <a href="https://developers.facebook.com/docs/instagram-platform">Meta's Instagram Platform API Documentation</a>
 */
public class InstagramService extends ContentSource {
    private static final Platform INFO = new Platform("https://instagram.com", "Instagram", "@");

    public InstagramService(String id) {
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
