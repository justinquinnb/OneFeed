package dev.jqb.onefeed.plugintestkit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.jqb.onefeed.api.content.Content;
import dev.jqb.onefeed.api.content.RawContent;
import dev.jqb.onefeed.api.content.SourceInfo;
import dev.jqb.onefeed.api.feed.FeedIdentifier;
import dev.jqb.onefeed.api.feed.FilteredContent;
import dev.jqb.onefeed.api.feed.OneFeedProviderPlugin;
import dev.jqb.onefeed.api.feed.Platform;
import dev.jqb.onefeed.api.feed.Profile;
import dev.jqb.onefeed.api.feed.Provider;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestInstance;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Tests the basic functionalities of a {@link OneFeedProviderPlugin}, particularly its provided
 * {@link dev.jqb.onefeed.api.feed.Provider} implementation
 *
 * @param <T> your specific {@link OneFeedProviderPlugin} implementation
 */
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public non-sealed abstract class ProviderPluginTests<T extends OneFeedProviderPlugin>
    extends OneFeedPluginTests<T> {

    public Provider<? extends RawContent> provider;
    public int contentPerPageLimit;

    @BeforeAll
    public void getProvider() {
        this.provider = plugin.getProvider();
        this.contentPerPageLimit = getContentPerPageLimit();
    }

    protected abstract int getContentPerPageLimit();

    /**
     * Ensure there are feeds configured for testing
     */
    @Test
    public void feedsAreConfigured() {
        assertNotNull(plugin.getFeedNames());
    }

    /**
     * The provider returns complete platform info
     */
    @Test
    public void platformInfoIsComplete() {
        Platform platform = provider.getPlatformInfo();
        assertNotNull(platform);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(platform.getName()).isNotEmpty()
            .as("Platform name is specified");
        softly.assertThat(platform.getHomepageUrl()).isNotEmpty()
            .as("Platform homepage URL is specified");
        softly.assertAll();
    }

    /**
     * For every feed specified for testing, try retrieving its profile
     */
    @TestFactory
    public Stream<DynamicTest> retrieveFeedProfiles() {
        List<String> feedNames = plugin.getFeedNames();

        return plugin.getFeedNames().stream().map(feedName ->
            DynamicTest.dynamicTest("Profile retrieval for: " + feedName, () -> {
                retrieveFeedProfile(feedName);
            })
        );
    }

    /**
     * Test retrieval of the given feed's {@link Profile}, validating a successful response and the
     * existence of the profile and its fields
     *
     * @param feedName the name of the feed whose profile to try retrieving
     */
    private void retrieveFeedProfile(String feedName) {
        Mono<Profile> mono = provider.getProfile(feedName);

        StepVerifier.create(mono)
            .consumeNextWith(profile -> {
                assertNotNull(profile);
                log.debug("Retrieved profile: {}", profile);

                SoftAssertions softly = new SoftAssertions();
                softly.assertThatCode(
                    () -> URI.create(profile.getProfilePicSrc()).toURL()
                ).as("Valid profilePicSrc URL").doesNotThrowAnyException();

                softly.assertThatCode(
                    () -> URI.create(profile.getFeedUrl()).toURL()
                ).as("Valid feedUrl URL").doesNotThrowAnyException();

                softly.assertThat(profile.getHandle()).as("Handle is not blank")
                    .isNotBlank();

                softly.assertThat(profile.getId()).as("ID is not blank").isNotBlank();

                softly.assertThat(profile.getName()).as("Name is not blank").isNotBlank();

                softly.assertAll();
            })
            .verifyComplete();
    }

    /**
     * For every feed specified for testing, try retrieving a single piece of content
     */
    @TestFactory
    public Stream<DynamicTest> retrieveSingleContentFromFeeds() {
        List<String> feedNames = plugin.getFeedNames();

        return plugin.getFeedNames().stream().map(feedName ->
            DynamicTest.dynamicTest("Singular content retrieval for: " + feedName, () -> {
                retrieveSingleContent(feedName);
            })
        );
    }

    /**
     * Test retrieval of the given feed's {@link Content}, validating a successful response and the
     * existence of the basic {@link RawContent} fields
     *
     * @param feedName the name of the feed whose profile to try retrieving
     */
    private void retrieveSingleContent(String feedName) {
        Mono<? extends FilteredContent<? extends RawContent>> mono = provider
            .fetchRecentContent(feedName, 1, List.of(), new HashMap<>());

        StepVerifier.create(mono)
            .consumeNextWith(filteredContent -> {
                assertNotNull(filteredContent);
                List<RawContent> allContent = (List<RawContent>) filteredContent.getContent();
                assertNotNull(allContent);

                // Not necessarily a fail because the feed may just have no content
                if (allContent.isEmpty()) {
                    log.warn("No content retrieved for feed: {}", feedName);
                }

                assert(allContent.size() <= 1);

                RawContent rawContent = allContent.getFirst();
                log.debug("Retrieved raw content: {}", rawContent);

                validateRawContent(rawContent);
            })
            .verifyComplete();
    }

    /**
     * For every feed specified for testing, try retrieving two pages of content
     */
    @TestFactory
    public Stream<DynamicTest> retrieveTwoContentPagesFromFeeds() {
        List<String> feedNames = plugin.getFeedNames();

        return plugin.getFeedNames().stream().map(feedName ->
            DynamicTest.dynamicTest("Multi-page content retrieval for: " + feedName, () -> {
                retrieveTwoContentPages(feedName);
            })
        );
    }

    /**
     * Test retrieval of two pages of the given feed's {@link Content}, validating a successful
     * response and the existence of the basic {@link RawContent} fields
     *
     * @param feedName the name of the feed whose profile to try retrieving
     */
    private void retrieveTwoContentPages(String feedName) {
        Mono<? extends FilteredContent<? extends RawContent>> mono = provider
            .fetchRecentContent(feedName, contentPerPageLimit + 1, List.of(), new HashMap<>());

        StepVerifier.create(mono)
            .consumeNextWith(filteredContent -> {
                assertNotNull(filteredContent);
                List<RawContent> allContent = (List<RawContent>) filteredContent.getContent();

                assertNotNull(allContent);

                // Not necessarily a fail because the feed may just have no content
                if (allContent.isEmpty()) {
                    log.warn("No content retrieved for feed: {}", feedName);
                }

                assert(allContent.size() <= contentPerPageLimit + 1);

                if (allContent.size() < contentPerPageLimit + 1) {
                    log.warn("Less than expected content retrieved for: {} ({} expected)",
                        allContent.size(), contentPerPageLimit + 1);
                }

                RawContent rawContent = allContent.getLast();
                log.debug("Testing validity of last content piece: {}", rawContent);

                validateRawContent(rawContent);
            })
            .verifyComplete();
    }

    /**
     * Validates the existence of the basic {@link RawContent} fields
     * @param rawContent the {@link RawContent} to validate
     */
    private static void validateRawContent(RawContent rawContent) {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(rawContent.getPublished()).as("Published is not null")
            .isNotNull();

        SourceInfo source = rawContent.getSource();
        softly.assertThat(source).as("Source is not null").isNotNull();
        softly.assertThat(source.getIdOnPlatform())
            .as("ID on platform is not blank").isNotBlank();
        softly.assertThat(source.getUrl()).as("Source URL is not blank")
            .isNotBlank();

        FeedIdentifier feedId = source.getFeedId();
        softly.assertThat(feedId).as("Feed ID is not null").isNotNull();
        softly.assertThat(feedId.getProviderId()).as("Provider ID is not blank");
        softly.assertThat(feedId.getName()).as("Feed name is not blank").isNotBlank();

        softly.assertAll();
    }
}
