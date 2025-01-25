package com.justinquinnb.onefeed.api;

import com.justinquinnb.onefeed.OneFeedApplication;
import com.justinquinnb.onefeed.data.model.content.Content;
import com.justinquinnb.onefeed.data.model.content.ContentComparator;
import com.justinquinnb.onefeed.data.model.source.ContentSource;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.PriorityQueue;

@Service
public class ContentService {
    /**
     * Gets the {@code count} latest units of {@link Content} from all available {@link ContentSource}s in reverse-
     * chronological order.
     *
     * @param count the amount of {@code Content} to retrieve
     *
     * @return at most {@code count}-many units of {@code Content}. If less than {@code count}-many units of
     * {@code Content} can be retrieved, then all that could be retrieved is returned.
     */
    public static Content[] getContent(int count) {
        // Get all the Content that could possibly be needed to make a count-sized aggregate
        Collection<Content> allContent = new ArrayList<>();
        Collection<Content> newContent;

        for (ContentSource source : OneFeedApplication.contentSources.values()) {
            newContent = Arrays.stream(source.getLatestContent(count)).toList();
            allContent.addAll(newContent);
        }

        return aggregateContent(allContent, count);
    }

    /**
     * Gets the {@code count} latest units of {@link Content} from the {@link ContentSource}s specified in
     * {@code fromSources} in reverse-chronological order.
     *
     * @param count the amount of {@code Content} to retrieve
     * @param fromSources an array of {@code ContentSource} identifier {@code Strings} specifying which data sources
     *                    to pull from
     *
     * @return at most {@code count}-many units of {@code Content} from the {@code ContentSource}s specified in
     * {@code fromSources}. If less than {@code count}-many units of {@code Content} can be retrieved, then all that
     * could be retrieved is returned.
     */
    public static Content[] getContent(int count, String[] fromSources) {
        // Get all the Content that could possibly be needed to make a count-sized aggregate
        Collection<Content> allContent = new ArrayList<>();
        Collection<Content> newContent;

        for (String sourceName : fromSources) {
            ContentSource source = OneFeedApplication.contentSources.get(sourceName);

            newContent = Arrays.stream(source.getLatestContent(count)).toList();
            allContent.addAll(newContent);
        }

        return aggregateContent(allContent, count);
    }

    /**
     * Gets the {@code count} latest units of {@link Content} between the specified {@code betweenTimes} from all
     * available {@link ContentSource}s in reverse-chronological order.
     *
     * @param count the amount of {@code Content} to retrieve
     * @param betweenTimes the {@link Instant}s that {@code Content} should fall between, element 0 indicating the start
     *                     and element 1 indicating the end, inclusive
     *
     * @return at most {@code count}-many units of {@code Content} between the specified {@code betweenTimes} from all
     * available {@link ContentSource}s. If less than {@code count}-many units of {@code Content} can be retrieved, then
     * all that could be retrieved is returned.
     */
    public static Content[] getContent(int count, Instant[] betweenTimes) {
        // Get all the Content that could possibly be needed to make a count-sized aggregate
        Collection<Content> allContent = new ArrayList<>();
        Collection<Content> newContent;

        for (ContentSource source : OneFeedApplication.contentSources.values()) {
            newContent = Arrays.stream(source.getLatestContent(count, betweenTimes)).toList();
            allContent.addAll(newContent);
        }

        return aggregateContent(allContent, count);
    }

    /**
     * Gets the {@code count} latest units of {@link Content} between the specified {@code betweenTimes} from all
     * available {@link ContentSource}s in reverse-chronological order.
     *
     * @param count the amount of {@code Content} to retrieve
     * @param fromSources an array of {@code ContentSource} identifier {@code Strings} specifying which data source to
     *                    pull from
     * @param betweenTimes the {@link Instant}s that {@code Content} should fall between, element 0 indicating the start
     *                     and element 1 indicating the end, inclusive
     *
     * @return at most {@code count}-many units of {@code Content} between the specified {@code betweenTimes} from the
     * {@code ContentSource}s specified in {@code fromSources}. If less than {@code count}-many units of {@code Content}
     * can be retrieved, then all that could be retrieved is returned.
     */
    public static Content[] getContent(int count, String[] fromSources, Instant[] betweenTimes) {
        // Get all the Content that could possibly be needed to make a count-sized aggregate
        Collection<Content> allContent = new ArrayList<>();
        Collection<Content> newContent;

        for (String sourceName : fromSources) {
            ContentSource source = OneFeedApplication.contentSources.get(sourceName);

            newContent = Arrays.stream(source.getLatestContent(count, betweenTimes)).toList();
            allContent.addAll(newContent);
        }

        return aggregateContent(allContent, count);
    }

    /**
     * Takes a collection of miscellaneous {@link Content} and aggregates it into a single, chronologically-ordered
     * array of {@code Content} of at most {@code count} length.
     *
     * @param content any collection of {@code Content}
     * @param count the desired amount of {@code Content} to place into the final array
     *
     * @return an array of {@code Content} in reverse-chronological order, of {@code count} length at max. Should the
     * provided collection of content be any smaller than the desired count, the output array will match that size
     * instead.
     */
    private static Content[] aggregateContent(Collection<Content> content, int count) {
        Content[] aggregateFeed = new Content[0];

        if (!content.isEmpty()) {
            aggregateFeed = new Content[count];

            PriorityQueue<Content> sortedContent = new PriorityQueue<>(content.size(), new ContentComparator());

            // Take the count-most recent bits of content and return it
            sortedContent.addAll(content);

            for (int i = 0; i < count; i++) {
                aggregateFeed[i] = sortedContent.poll();
            }
        }

        return aggregateFeed;
    }
}