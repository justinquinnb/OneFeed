package com.justinquinnb.onefeed;

import com.justinquinnb.onefeed.data.sources.github.GitHubService;
import com.justinquinnb.onefeed.data.sources.instagram.InstaService;
import com.justinquinnb.onefeed.data.sources.linkedin.LinkedInService;
import com.justinquinnb.onefeed.data.model.source.ContentSource;
import com.justinquinnb.onefeed.data.sources.threads.ThreadsService;
import com.justinquinnb.onefeed.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class OneFeedApplication {
	public static ContentSource[] contentSources = new ContentSource[4];

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

		// Initialize OneFeed
		// Initialize content sources
		getContentSources();
		testContentSources();

		// Start up Spring Boot
		try {
			Logger.logToBothF("%t %s - Initialization successful.");
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

		// Test OneFeed endpoints?
		// TODO
		// TODO implement logger for java
	}

	/**
	 * Prints OneFeed info.
	 */
	private static void printOneFeedInfo() {
		Logger.logToBoth("%t %i - Starting OneFeed...");
		Logger.logToBothF("""
				\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				OneFeed v0.0.1 - The Free Feed Aggregator
				Developed by Justin Quinn - https://github.com/justinquinnb
				~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				""");
		Logger.logToBothF("%t %i - Initializing...");
	}

	/**
	 * Gets and stores instance of each {@code ContentSource} provided in {@link com.justinquinnb.onefeed.data.sources}
	 * directory.
	 */
	private static void getContentSources() {
		contentSources[0] = new InstaService();
		Logger.logToBothF("\t\t%t %s - Instagram service instantiated.");

		contentSources[1] = new ThreadsService();
		Logger.logToBothF("\t\t%t %s - Threads service instantiated.");

		contentSources[2] = new LinkedInService();
		Logger.logToBothF("\t\t%t %s - LinkedIn service instantiated.");

		contentSources[3] = new GitHubService();
		Logger.logToBothF("\t\t%t %s - GitHub service instantiated.");

		Logger.logToBothF("\t%t %s - All content sources instantiated.");
	}

	/**
	 * Attempts to establish a connection to every content source.
	 */
	private static void testContentSources() {
		Logger.logToBothF("\t%t %i - Testing content sources...");

		// Update status
		int successCount = 0, failCount = 0;

		for(ContentSource source : contentSources) {
			if(source.isAvailable()) {
				successCount++;
			} else {
				failCount++;
			}
		}

		if (failCount > 0) {
			Logger.logToBothF(
					"\t%t %w - Content sources tested with " + successCount + "available and " + failCount + " unavailable."
			);
		} else {
			Logger.logToBothF("\t%t %s - All content sources are available.");
		}
	}
}