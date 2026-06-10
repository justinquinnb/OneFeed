package dev.jqb.onefeed.plugintestkit;

import static org.junit.jupiter.api.Assertions.assertNull;

import dev.jqb.onefeed.api.author.NormalizedAuthor;
import dev.jqb.onefeed.api.caching.Cacher;
import dev.jqb.onefeed.api.caching.OneFeedCacherPlugin;
import dev.jqb.onefeed.api.content.Content;
import dev.jqb.onefeed.api.author.Author;
import dev.jqb.onefeed.api.content.NormalizedContent;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * Tests the basic functionalities of a {@link OneFeedCacherPlugin}, particularly its provided
 * {@link dev.jqb.onefeed.api.caching.Cacher} implementation
 *
 * @param <T> your specific {@link OneFeedCacherPlugin} implementation
 */
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public non-sealed abstract class CacherPluginTests<T extends OneFeedCacherPlugin>
    extends OneFeedPluginTests<T>
{
    public Cacher<NormalizedContent, NormalizedAuthor> cacher;
    public NormalizedContent sampleContent;
    public NormalizedContent updatedSampleContent;
    public NormalizedAuthor sampleAuthor;
    public NormalizedAuthor updatedSampleAuthor;

    @BeforeAll
    public void getCacher() {
        this.cacher = (Cacher<NormalizedContent, NormalizedAuthor>) plugin.getCacher();
        this.sampleContent = this.getSampleContent();
        this.updatedSampleContent = this.getUpdatedSampleContent();
        this.sampleAuthor = this.getSampleAuthor();
        this.updatedSampleAuthor = this.getUpdatedSampleAuthor();
    }

    protected abstract NormalizedContent getSampleContent();
    protected abstract NormalizedAuthor getSampleAuthor();
    protected abstract NormalizedContent getUpdatedSampleContent();
    protected abstract NormalizedAuthor getUpdatedSampleAuthor();
    protected abstract boolean contentMatches(NormalizedContent one, NormalizedContent two);
    protected abstract boolean authorsMatch(NormalizedAuthor one, NormalizedAuthor two);

    /**
     * Test whether errors are thrown when caching content.
     */
    @Test
    @Order(1)
    public void cacheSampleContent() {
        cacher.cacheContent(List.of(sampleContent));
    }

    /**
     * Test whether errors are thrown when caching an author.
     */
    @Test
    @Order(2)
    public void cacheSampleAuthor() {
        cacher.cacheAuthors(List.of(sampleAuthor));
    }

    /**
     * Test whether the sample content is retrievable from the cache.
     */
    @Test
    @Order(3)
    public void fetchSampleContent() {
        NormalizedContent content = cacher.fetchContent(sampleContent.getSource());
        assert contentMatches(sampleContent, content);
    }

    /**
     * Test whether the sample author is retrievable from the cache.
     */
    @Test
    @Order(4)
    public void fetchSampleAuthor() {
        NormalizedAuthor author = cacher.fetchAuthor(sampleAuthor.getFeedIdentifier());
        assert authorsMatch(sampleAuthor, author);
    }

    /**
     * Test whether the sample content can be updated in the cache without error
     */
    @Test
    @Order(5)
    public void updateSampleContent() {
        cacher.cacheContent(List.of(updatedSampleContent));
        NormalizedContent updatedSampleContent = cacher.fetchContent(sampleContent.getSource());
        assert contentMatches(sampleContent, updatedSampleContent);
    }

    @Test
    @Order(6)
    public void updateSampleAuthor() {
        cacher.cacheAuthors(List.of(updatedSampleAuthor));
        NormalizedAuthor updatedSampleAuthor = cacher.fetchAuthor(sampleAuthor.getFeedIdentifier());
        assert authorsMatch(sampleAuthor, updatedSampleAuthor);
    }

    @Test
    @Order(7)
    public void removeSampleContent() {
        cacher.removeContent(sampleContent.getFeedIdentifier(),
            sampleContent.getSource().getIdOnPlatform());
        assertNull(cacher.fetchContent(sampleContent.getSource()));
    }

    @Test
    @Order(8)
    public void removeSampleAuthor() {
        cacher.removeAuthor(sampleAuthor.getFeedIdentifier());
        assertNull(cacher.fetchAuthor(sampleAuthor.getFeedIdentifier()));
    }
}
