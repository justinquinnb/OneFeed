package com.justinquinnb.onefeed.contentsources.github;

import com.justinquinnb.onefeed.data.model.content.BasicContent;
import com.justinquinnb.onefeed.data.model.content.details.BasicPlatform;
import com.justinquinnb.onefeed.data.model.content.details.ContentSourceId;
import com.justinquinnb.onefeed.data.model.content.details.Platform;
import com.justinquinnb.onefeed.data.model.source.ContentSource;

import java.time.Instant;

/**
 * Provides a means of accessing GitHub activity through GitHub's GraphQL API.
 *
 * @see <a href="https://docs.github.com/en/graphql/overview/about-the-graphql-api">GitHub GraphQL API Documentation</a>
 */
public class GitHubGraphQlService extends ContentSource {
    private static final Platform PLATFORM_INFO = new BasicPlatform("https://github.com", "GitHub", "@");

    public GitHubGraphQlService(ContentSourceId id) {
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
