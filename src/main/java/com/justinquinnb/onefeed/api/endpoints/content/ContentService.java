package com.justinquinnb.onefeed.api.endpoints.content;

import com.justinquinnb.onefeed.OneFeedApplication;
import com.justinquinnb.onefeed.content.Content;
import com.justinquinnb.onefeed.customization.addon.contentsource.ContentSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.concurrent.CompletableFuture;

/**
 * Provides the data for OneFeed's {@code /content} API endpoints.
 * @see ContentController
 */
@Service
public class ContentService {
    private static final Logger logger = LoggerFactory.getLogger(ContentService.class);

    /**
     * Gets the {@code count} latest units of {@link Content} from all available {@link ContentSource}s
     * in reverse-chronological order.
     *
     * @param count the amount of {@code Content} to retrieve
     *
     * @return at most {@code count}-many units of {@code Content}. If less than {@code count}-many
     * units of {@code Content} can be retrieved, then all that could be retrieved is returned.
     */
    @Async
    public CompletableFuture<Content[]> getContent(int count) {
        logger.debug("Attempting to retrieve Content: {} pieces", count);

        // Get all the Content that could possibly be needed to make a count-sized aggregate
        CompletableFuture<Content[]> completableFuture = new CompletableFuture<>();

        Collection<Content> allContent = new ArrayList<>();
        Collection<Content> newContent;

        // For every Content Source identified at OneFeed startup, try to get `count`-many pieces of
        // Content because any given source might hold the top `count` most recent pieces
        for (String sourceId : OneFeedApplication.CONTENT_SOURCES.keySet()) {
            logger.debug("Attempting to retrieve {} pieces of Content from {}", count, sourceId);
            newContent = Arrays.stream(OneFeedApplication.CONTENT_SOURCES.get(sourceId).getLatestContent(count)).toList();

            logger.trace("Retrieved {} pieces of Content from {}: {}", newContent.size(), sourceId,
                newContent);
            allContent.addAll(newContent);
        }

        // Aggregate the Content into an array of at most `count` most recent pieces of Content because the amount of
        // Content posted across all Content Sources may total less than the desired count
        Content[] aggregation = aggregateContent(allContent, count);
        logger.debug("Retrieved Content aggregated into: {}", Arrays.toString(aggregation));

        completableFuture.complete(aggregation);
        return completableFuture;
    }

    /**
     * Gets the {@code count} latest units of {@link Content} from the {@link ContentSource}s
     * specified in {@code fromSources} in reverse-chronological order.
     *
     * @param count the amount of {@code Content} to retrieve
     * @param fromSources an array of {@code ContentSource}s specifying
     *                    which data sources to pull from
     *
     * @return at most {@code count}-many units of {@code Content} from the {@code ContentSource}s
     * specified in {@code fromSources}. If less than {@code count}-many units of {@code Content}
     * can be retrieved, then all that could be retrieved is returned.
     * @throws InvalidSourceIdException if {@code fromSources} contains a Content Source ID that
     * OneFeed is not aware of.
     */
    @Async
    public CompletableFuture<Content[]> getContent(int count, ContentSource[] fromSources)
            throws InvalidSourceIdException {
        logger.debug("Attempting to retrieve Content: {} pieces from sources {}", count, Arrays.toString(fromSources));

        // Get all the Content that could possibly be needed to make a count-sized aggregate
        CompletableFuture<Content[]> completableFuture = new CompletableFuture<>();
        Collection<Content> allContent = new ArrayList<>();
        Collection<Content> newContent;

        // For every Content Source ID specified, try to get `count`-many pieces of Content
        // because any given source might hold the top `count` most recent pieces
        for (ContentSource source : fromSources) {
            // Retrieve the actual Content
            logger.debug("Attempting to retrieve {} pieces of Content from {}",
                count, source.SOURCE_ID);
            newContent = Arrays.stream(source.getLatestContent(count)).toList();

            logger.trace("Retrieved {} pieces of Content from {}: {}",
                    newContent.size(), source.SOURCE_ID, newContent);
            allContent.addAll(newContent);
        }

        // Aggregate the Content into an array of at most `count` most recent pieces of Content
        // because the amount of Content posted across the desired Content Sources may total less
        // than the desired count
        Content[] aggregation = aggregateContent(allContent, count);
        logger.debug("Retrieved Content aggregated into: {}", Arrays.toString(aggregation));

        completableFuture.complete(aggregation);
        return completableFuture;
    }

    /**
     * Gets the {@code count} latest units of {@link Content} between the specified
     * {@code betweenTimes} from all available {@link ContentSource}s in reverse-chronological order.
     *
     * @param count the amount of {@code Content} to retrieve
     * @param betweenTimes the {@link Instant}s that {@code Content} should fall between, element 0
     *                     indicating the start and element 1 indicating the end, inclusive
     *
     * @return at most {@code count}-many units of {@code Content} between the specified
     * {@code betweenTimes} from all available {@link ContentSource}s. If less than
     * {@code count}-many units of {@code Content} can be retrieved, then all that could be
     * retrieved is returned.
     */
    @Async
    public CompletableFuture<Content[]> getContent(int count, Instant[] betweenTimes) {
        logger.debug("Attempting to retrieve Content: {} pieces between instants {} and {}",
                count, betweenTimes[0], betweenTimes[1]);

        // Get all the Content that could possibly be needed to make a count-sized aggregate
        CompletableFuture<Content[]> completableFuture = new CompletableFuture<>();
        Collection<Content> allContent = new ArrayList<>();
        Collection<Content> newContent;

        for (String sourceId : OneFeedApplication.CONTENT_SOURCES.keySet()) {
            // Retrieve the actual content
            logger.debug("Attempting to retrieve {} pieces of Content from {} between instants {} and {}",
                    count, sourceId, betweenTimes[0].toString(), betweenTimes[1].toString());
            newContent = Arrays.stream(OneFeedApplication.CONTENT_SOURCES.get(sourceId)
                    .getLatestContent(count, betweenTimes)).toList();

            logger.trace("Retrieved {} pieces of Content from {}: {}",
                    newContent.size(), sourceId, newContent.toString());
            allContent.addAll(newContent);
        }

        // Aggregate the Content into an array of at most `count` most recent pieces of Content
        // because the amount of Content posted across all Content Sources may total less than the
        // desired count
        Content[] aggregation = aggregateContent(allContent, count);
        logger.debug("Retrieved Content aggregated into: {}", Arrays.toString(aggregation));

        completableFuture.complete(aggregation);
        return completableFuture;
    }

    /**
     * Gets the {@code count} latest units of {@link Content} between the specified
     * {@code betweenTimes} from all available {@link ContentSource}s in reverse-chronological order.
     *
     * @param count the amount of {@code Content} to retrieve
     * @param fromSources an array of {@code ContentSource}s specifying which data sources to pull from
     * @param betweenTimes the {@link Instant}s that {@code Content} should fall between, element 0
     *                     indicating the start and element 1 indicating the end, inclusive
     *
     * @return at most {@code count}-many units of {@code Content} between the specified
     * {@code betweenTimes} from the {@code ContentSource}s specified in {@code fromSources}. If
     * less than {@code count}-many units of {@code Content} can be retrieved, then all that could
     * be retrieved is returned.
     * @throws InvalidSourceIdException if {@code fromSources} contains a Content Source ID that
     * OneFeed is not aware of.
     */
    @Async
    public CompletableFuture<Content[]> getContent(
        int count, ContentSource[] fromSources, Instant[] betweenTimes)
        throws InvalidSourceIdException
    {
        logger.debug("Attempting to retrieve Content: {} pieces from sources {} between instants {} and {}",
                count, Arrays.toString(fromSources), betweenTimes[0], betweenTimes[1]);

        // Get all the Content that could possibly be needed to make a count-sized aggregate
        CompletableFuture<Content[]> completableFuture = new CompletableFuture<>();
        Collection<Content> allContent = new ArrayList<>();
        Collection<Content> newContent;

        for (ContentSource source : fromSources) {
            // Then retrieve the actual Content
            logger.debug("Attempting to retrieve {} pieces of Content from source {} between instants {} and {}",
                    count, source.SOURCE_ID, betweenTimes[0].toString(), betweenTimes[1].toString());
            newContent = Arrays.stream(source.getLatestContent(count, betweenTimes)).toList();

            logger.trace("Retrieved {} pieces of Content from {}: {}",
                    newContent.size(), source.SOURCE_ID, newContent.toString());
            allContent.addAll(newContent);
        }

        /* Aggregate the Content into an array of at most `count` most recent pieces of Content
           because the amount of Content posted across the Content Sources and between the desired
           instants may total less than the desired count */
        Content[] aggregation = aggregateContent(allContent, count);
        logger.debug("Retrieved Content aggregated into: {}", Arrays.toString(aggregation));

        completableFuture.complete(aggregation);
        return completableFuture;
    }

    /**
     * Takes a collection of miscellaneous {@link Content} and aggregates it into a single,
     * chronologically-ordered array of {@code Content} of at most {@code count} length.
     *
     * @param content any collection of {@code Content}
     * @param count the desired amount of {@code Content} to place into the final array
     *
     * @return an array of {@code Content} in reverse-chronological order, of {@code count} length
     * at max. Should the provided collection of content be any smaller than the desired count, the
     * output array will match that size instead.
     */
    private static Content[] aggregateContent(Collection<Content> content, int count) {
        logger.debug("Attempting to aggregate the following Content into an array of {} pieces: {}",
                count, Arrays.toString(content.toArray()));

        Content[] aggregateFeed = new Content[0];

        // If no Content was sent through, then don't waste resources trying to aggregate nothing
        if (!content.isEmpty()) {
            logger.debug("{} pieces of Content found in collection, aggregation commencing",
                content.size());

            // Employ a PriorityQueue to do the sorting for us
            PriorityQueue<Content> sortedContent = new PriorityQueue<>(content.size(), new ContentComparator());

            // Take the count-most recent bits of content and return it
            sortedContent.addAll(content);
            int finalCount = Math.min(count, sortedContent.size());
            aggregateFeed = new Content[finalCount];

            for (int i = 0; i < finalCount; i++) {
                aggregateFeed[i] = sortedContent.poll();
                logger.trace("Added the following piece of Content into the final aggregation: {}",
                        aggregateFeed[i].toString());
            }
        } else {
            logger.debug("No Content in collection, aggregation skipped");
        }

        logger.debug("Aggregated {} pieces of Content into: {}", aggregateFeed.length, Arrays.toString(aggregateFeed));
        return aggregateFeed;
    }
}