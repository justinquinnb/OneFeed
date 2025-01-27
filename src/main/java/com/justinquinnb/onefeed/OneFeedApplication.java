package com.justinquinnb.onefeed;

import com.justinquinnb.onefeed.data.model.source.ContentSource;
import com.justinquinnb.onefeed.data.sources.github.GitHubService;
import com.justinquinnb.onefeed.data.sources.instagram.InstaService;
import com.justinquinnb.onefeed.data.sources.linkedin.LinkedInService;
import com.justinquinnb.onefeed.data.sources.sample.SampleService;
import com.justinquinnb.onefeed.data.sources.threads.ThreadsService;
import com.justinquinnb.onefeed.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

import java.util.Arrays;
import java.util.HashMap;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class OneFeedApplication {
	/**
	 * Maps unique {@link ContentSource} identifiers to their respective service file.
	 */
	public static final HashMap<String, ContentSource> contentSources = new HashMap<>();

	public static void main(String[] args) {
		// On shutdown script
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				Logger.logToBothF("%t %w - Exiting application...");
				Logger.endLog();
			}
		});

		// Console info
		printOneFeedInfo();


		try {
			// Initialize OneFeed
			// Initialize content sources
			getContentSources();
			testContentSources();
			
			Logger.logToBothF("%t %s - Initialization successful.");

			// Start up Spring Boot
			Logger.logToBothF("%t %i - Starting Spring Boot...");

			// Run application
			SpringApplication.run(OneFeedApplication.class, args);
			Logger.logToBothF("%t %s - Spring Boot has started.");
		} catch (Exception e) {
			Logger.diffLogToBothF(
					"%t %f - Something went wrong.\n\tView the most recent log file for more info.",
					"%t %f - Something went wrong.\n\t" + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace())
					);
		}
	}

	/**
	 * Prints OneFeed info.
	 */
	private static void printOneFeedInfo() {
		Logger.logToBothF("%t %i - Starting OneFeed...");
		Logger.logToBoth("""
				\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				OneFeed v0.0.1 - The Free Feed Aggregator
				Developed by Justin Quinn - https://github.com/justinquinnb
				~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				""");
		Logger.logToBothF("%t %i - Initializing...");
	}

	/**
	 * Gets and stores instance of each {@link ContentSource} provided in {@link com.justinquinnb.onefeed.data.sources}
	 * directory.
	 */
	private static void getContentSources() {
		contentSources.put("IG", new InstaService());
		Logger.logToBothF("\t\t%t %s - Instagram service instantiated.");

		contentSources.put("TH", new  ThreadsService());
		Logger.logToBothF("\t\t%t %s - Threads service instantiated.");

		contentSources.put("LI", new LinkedInService());
		Logger.logToBothF("\t\t%t %s - LinkedIn service instantiated.");

		contentSources.put("GH", new GitHubService());
		Logger.logToBothF("\t\t%t %s - GitHub service instantiated.");

		contentSources.put("SP", new SampleService());
		Logger.logToBothF("\t\t%t %s - Sample service instantiated.");

		Logger.logToBothF("\t%t %s - All content sources instantiated.");
	}

	/**
	 * Attempts to establish a connection to every {@link ContentSource}.
	 */
	private static void testContentSources() {
		Logger.logToBothF("\t%t %i - Testing content sources...");

		// Update status
		int successCount = 0, failCount = 0;

		for (ContentSource source : contentSources.values()) {
			if (source.isAvailable()) {
				Logger.logToBothF("\t\t%t %s - " + source.getSourceName() + " is available.");
				successCount++;
			} else {
				Logger.logToBothF("\t\t%t %w - " + source.getSourceName() + " is unavailable.");
				failCount++;
			}
		}

		if (failCount == contentSources.size()) {
			Logger.logToBothF(
					"\t%t %f - Content sources tested with all " + failCount + " unavailable."
			);
		} else if (failCount > 0) {
			Logger.logToBothF(
					"\t%t %f - Content sources tested with " + successCount + " available and " + failCount + " unavailable."
			);
		} else {
			Logger.logToBothF("\t%t %s - All content sources are available.");
			throw new RuntimeException("All content sources are unavailable.");
		}
	}
}