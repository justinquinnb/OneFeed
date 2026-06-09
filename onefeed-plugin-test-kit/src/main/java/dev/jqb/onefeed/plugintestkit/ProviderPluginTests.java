package dev.jqb.onefeed.plugintestkit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.jqb.onefeed.api.content.Content;
import dev.jqb.onefeed.api.content.Normalizer;
import dev.jqb.onefeed.api.content.PlatformContent;
import dev.jqb.onefeed.api.provider.OneFeedProviderPlugin;
import dev.jqb.onefeed.api.feed.Platform;
import dev.jqb.onefeed.api.provider.Provider;
import dev.jqb.onefeed.api.feed.SourceInfo;
import dev.jqb.onefeed.api.impl.Media;
import dev.jqb.onefeed.api.impl.OneFeedContent;
import dev.jqb.onefeed.api.impl.Profile;
import java.net.URI;
import java.util.List;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestInstance;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Tests the basic functionalities of a {@link OneFeedProviderPlugin}, particularly its provided
 * {@link Provider} implementation
 *
 * @param <T> your specific {@link OneFeedProviderPlugin} implementation
 */
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public non-sealed abstract class ProviderPluginTests<T extends OneFeedProviderPlugin>
    extends OneFeedPluginTests<T> {

    public Provider<? extends PlatformContent> provider;
    public int contentPerPageLimit;
    public PlatformContent normalizerInput;
    public OneFeedContent expectedNormalizerOutput;

    @BeforeAll
    public void getProvider() {
        this.provider = plugin.getProvider();
        this.contentPerPageLimit = getContentPerPageLimit();
        this.normalizerInput = getNormalizerInput();
        this.expectedNormalizerOutput = getExpectedNormalizerOutput();
    }

    /**
     * Get the maximum number of content pieces that the provider will return per page.
     *
     * @return the maximum number of content pieces that the provider will return per page
     */
    protected abstract int getContentPerPageLimit();

    /**
     * Gets a sample piece of content to attempt to normalize.
     *
     * @return a sample piece of content to attempt to normalize
     */
    protected abstract PlatformContent getNormalizerInput();

    /**
     * Gets the sample piece of content correctly normalized as a piece of {@link OneFeedContent}.
     *
     * @return a sample piece of content correctly normalized as a piece of {@link OneFeedContent}
     */
    protected abstract OneFeedContent getExpectedNormalizerOutput();

    @Test
    public void normalizerWorksAsExpected() {
        Normalizer<PlatformContent, OneFeedContent> normalizer =
            (Normalizer<PlatformContent, OneFeedContent>) provider.getNormalizer();
        OneFeedContent normalizerOutput = normalizer.normalize(normalizerInput);

        validateOfcEquality(normalizerOutput, expectedNormalizerOutput);
    }

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

                SourceInfo source = profile.getSource();
                softly.assertThat(source).as("Source is not null").isNotNull();
                softly.assertThat(source.getIdOnPlatform())
                    .as("ID on platform is not blank").isNotBlank();
                softly.assertThat(source.getUrlOnPlatform()).as("Source URL is not blank")
                    .isNotBlank();

                softly.assertThat(profile.getHandle()).as("Handle is not blank")
                    .isNotBlank();

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
     * existence of the basic {@link PlatformContent} fields
     *
     * @param feedName the name of the feed whose profile to try retrieving
     */
    private void retrieveSingleContent(String feedName) {
        Flux<? extends PlatformContent> flux = provider
            .fetchRecentContent(feedName, 1);
        List<PlatformContent> content = (List<PlatformContent>) flux.collectList().block();
        assertNotNull(content);

        // Not necessarily a fail because the feed may just have no content
        if (content.isEmpty()) {
            log.warn("No content retrieved for feed: {}", feedName);
        }

        assert (content.size() <= 1);

        PlatformContent platformContent = content.getFirst();
        log.debug("Retrieved raw content: {}", platformContent);

        validateContent(platformContent);
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
     * response and the existence of the basic {@link PlatformContent} fields
     *
     * @param feedName the name of the feed whose profile to try retrieving
     */
    private void retrieveTwoContentPages(String feedName) {
        Flux<? extends PlatformContent> flux = provider
            .fetchRecentContent(feedName, contentPerPageLimit + 1);
        List<PlatformContent> content = (List<PlatformContent>) flux.collectList().block();

        assertNotNull(content);

        // Not necessarily a fail because the feed may just have no content
        if (content.isEmpty()) {
            log.warn("No content retrieved for feed: {}", feedName);
        }

        assert (content.size() <= contentPerPageLimit + 1);

        if (content.size() < contentPerPageLimit + 1) {
            log.warn("Less than expected content retrieved for: {} ({} expected)",
                content.size(), contentPerPageLimit + 1);
        }

        PlatformContent platformContent = content.getLast();
        log.debug("Testing validity of last content piece: {}", platformContent);

        validateContent(platformContent);
    }

    /**
     * Validates the existence of the basic {@link Content} fields
     *
     * @param content the {@link PlatformContent} to validate
     */
    private static void validateContent(Content content) {
        assertNotNull(content);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(content.getPublished()).as("Published is not null")
            .isNotNull();

        SourceInfo source = content.getSource();
        softly.assertThat(source).as("Source is not null").isNotNull();
        softly.assertThat(source.getIdOnPlatform())
            .as("ID on platform is not blank").isNotBlank();
        softly.assertThat(source.getUrlOnPlatform()).as("Source URL is not blank")
            .isNotBlank();
        softly.assertThat(source.getProviderId()).as("Provider ID is not blank");
        softly.assertThat(source.getFeedName()).as("Feed name is not blank").isNotBlank();

        softly.assertAll();
    }

    /**
     * Validates the equality of the given {@link OneFeedContent} pieces.
     *
     * @param actual   the piece of content to validate
     * @param expected the piece of content to compare against
     */
    private static void validateOfcEquality(OneFeedContent actual, OneFeedContent expected) {
        SoftAssertions softly = new SoftAssertions();
        log.debug("Validating each piece's base Content data...");
        validateContent(actual);
        validateContent(expected);

        log.debug("Validating the equality of actual content:\n{}\nagainst expected content:\n{}",
            actual, expected);

        // Base Content info
        // Source
        SourceInfo actualSource = actual.getSource();
        SourceInfo expectedSource = expected.getSource();

        softly.assertThat(actualSource.getIdOnPlatform()).as("Source IDs on platform match")
            .isEqualTo(expectedSource.getIdOnPlatform());
        softly.assertThat(actualSource.getUrlOnPlatform()).as("Source URLs match")
            .isEqualTo(expectedSource.getUrlOnPlatform());

        // All other base Content fields
        softly.assertThat(actual.getPublished()).as("Published dates match")
            .isEqualTo(expected.getPublished());

        softly.assertThat(actual.getNextPageCursor()).as("Next page cursors match")
            .isEqualTo(expected.getNextPageCursor());

        // Primary reaction count
        softly.assertThat(actual.getPrimaryReactionCount())
            .as("Primary reaction counts match")
            .isEqualTo(expected.getPrimaryReactionCount());

        // Textual content
        softly.assertThat(actual.getTitle()).as("Titles match")
            .isEqualTo(expected.getTitle());

        softly.assertThat(actual.getBody()).as("Bodies match")
            .isEqualTo(expected.getBody());

        // Media
        boolean actualHasMedia = actual.getMedia() != null;
        boolean expectedHasMedia = expected.getMedia() != null;
        softly.assertThat(actualHasMedia).as("Media existence matches")
            .isEqualTo(expectedHasMedia);

        softly.assertThat(actual.getMedia().size()).as("Media count matches")
            .isEqualTo(expected.getMedia().size());

        for (int i = 0; i < actual.getMedia().size(); i++) {
            validateMediaEquality(actual.getMedia().get(i), expected.getMedia().get(i), softly, i);
        }

        softly.assertAll();
    }

    /**
     * Validates the equality of the given {@link Media} pieces.
     *
     * @param actual   the piece of media to validate
     * @param expected the piece of media to compare against
     */
    private static void validateMediaEquality(Media actual, Media expected,
        SoftAssertions softly, int mediaNum
    ) {
        softly.assertThat(actual.getType()).as("Media %s's types match", mediaNum)
            .isEqualTo(expected.getType());

        softly.assertThat(actual.getHref()).as("Media %s's hrefs match", mediaNum)
            .isEqualTo(expected.getHref());

        softly.assertThat(actual.getTitle()).as("Media %s's titles match", mediaNum)
            .isEqualTo(expected.getTitle());

        softly.assertThat(actual.getSrc()).as("Media %s's srcs match", mediaNum)
            .isEqualTo(expected.getSrc());

        softly.assertThat(actual.getThumbnailSrc()).as("Media %s's thumbnail srcs match", mediaNum)
            .isEqualTo(expected.getThumbnailSrc());

        softly.assertThat(actual.getCaption()).as("Media %s's captions match", mediaNum)
            .isEqualTo(expected.getCaption());

        softly.assertThat(actual.getAltText()).as("Media %s's alt texts match", mediaNum)
            .isEqualTo(expected.getAltText());
    }
}
