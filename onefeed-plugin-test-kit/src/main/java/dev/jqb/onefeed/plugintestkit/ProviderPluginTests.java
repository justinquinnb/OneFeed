package dev.jqb.onefeed.plugintestkit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.jqb.onefeed.api.feed.OneFeedProviderPlugin;
import dev.jqb.onefeed.api.feed.Profile;
import dev.jqb.onefeed.api.feed.Provider;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
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
    extends OneFeedPluginTests<T>
{
    public Provider<?> provider;

    @BeforeAll
    public void getProvider() {
        this.provider = plugin.getProvider();
    }

    /**
     * For every feed specified for testing, try retrieving its profile
     */
    @TestFactory
    public Stream<DynamicTest> retrieveFeedProfiles() {
        List<String> feedNames = plugin.getFeedNames();

        if (feedNames == null || feedNames.isEmpty()) {
            return Stream.of(DynamicTest.dynamicTest("Initialization check", () -> {
                throw new IllegalStateException("Plugin reported 0 feeds. Check your initialization.");
            }));
        }

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
}
