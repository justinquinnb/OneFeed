package com.justinquinnb.onefeed.contentsources.github;

import com.justinquinnb.onefeed.data.model.content.Content;
import com.justinquinnb.onefeed.data.model.content.details.Platform;
import com.justinquinnb.onefeed.data.model.source.ContentSource;

import java.time.Instant;

/**
 * Provides a means of accessing GitHub activity through GitHub's GraphQL API.
 *
 * @see <a href="https://docs.github.com/en/graphql/overview/about-the-graphql-api">GitHub GraphQL API Documentation</a>
 */
public class GitHubGraphQlService extends ContentSource {
    private static final Platform INFO = new Platform("https://github.com", "GitHub", "@");

    public GitHubGraphQlService(String id) {
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
