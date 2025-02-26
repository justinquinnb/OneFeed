package com.justinquinnb.onefeed;

import com.justinquinnb.onefeed.data.model.source.ContentSource;
import com.justinquinnb.onefeed.data.contentsources.github.GitHubService;
import com.justinquinnb.onefeed.data.contentsources.instagram.InstaService;
import com.justinquinnb.onefeed.data.contentsources.linkedin.LinkedInService;
import com.justinquinnb.onefeed.data.contentsources.sample.SampleService;
import com.justinquinnb.onefeed.data.contentsources.threads.ThreadsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.HashMap;

@EnableAsync
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class OneFeedApplication {
	/**
	 * Maps unique {@link ContentSource} identifiers to their respective service file.
	 */
	public static final HashMap<String, ContentSource> CONTENT_SOURCES = new HashMap<>();
	private static final Logger logger = LoggerFactory.getLogger(OneFeedApplication.class);

	public static void main(String[] args) {
		// On shutdown script
		Runtime.getRuntime().addShutdownHook(new Thread(() -> logger.info("Shutting down...")));

		// Console info
		printOneFeedInfo();

		try {
			// Run application
			SpringApplication.run(OneFeedApplication.class, args);

			// Grab and test all the Content Sources
			getContentSources();
			testContentSources();
			logger.info("OneFeed successfully started.");
		} catch (Exception e) {
			logger.warn("Something's gone wrong. Check the latest log file for more info.");
		}
	}

	/**
	 * Prints OneFeed info.
	 */
	private static void printOneFeedInfo() {
		System.out.println("""
				~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				OneFeed - The Free Feed Aggregator
				Developed by Justin Quinn - https://github.com/justinquinnb
				~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				""");
	}

	/**
	 * Gets and stores instance of each {@link ContentSource} provided in {@link com.justinquinnb.onefeed.data.contentsources}
	 * directory.
	 */
	private static void getContentSources() {
		logger.info("Instantiating Content Sources...");

		// TODO replace this with the plugin system... register them here and automatically grab info
		CONTENT_SOURCES.put("IG", new InstaService("IG"));
		logger.info("Instagram service \"{}\" instantiated.", "IG");

		CONTENT_SOURCES.put("TH", new  ThreadsService("TH"));
		logger.info("Threads service \"{}\" instantiated.", "TH");

		CONTENT_SOURCES.put("LI", new LinkedInService("LI"));
		logger.info("LinkedIn service \"{}\" instantiated.", "LI");

		CONTENT_SOURCES.put("GH", new GitHubService("GH"));
		logger.info("GitHub service \"{}\" instantiated.", "GH");

		CONTENT_SOURCES.put("SP", new SampleService("SP"));
		logger.info("Sample service \"{}\" instantiated.", "SP");

		logger.info("Content Sources successfully instantiated.");
	}

	/**
	 * Attempts to establish a connection to every {@link ContentSource}.
	 */
	private static void testContentSources() {
		logger.info("Testing Content Sources...");

		// Update status
		int successCount = 0, failCount = 0;

		for (ContentSource source : CONTENT_SOURCES.values()) {
			if (source.isAvailable()) {
				logger.info("Content Source \"{}\" is available.", source.getId());
				successCount++;
			} else {
				logger.warn("Content Source \"{}\" is unavailable.", source.getId());
				failCount++;
			}
		}

		if (failCount == CONTENT_SOURCES.size()) {
			logger.warn("Content Sources tested with all {} unavailable.", failCount);
		} else if (failCount > 0) {
			logger.warn("Content Sources tested with {} available and {} unavailable.", successCount, failCount);
		} else {
			logger.info("All Content Sources are available.");
		}
	}
}