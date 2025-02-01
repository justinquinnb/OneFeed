package com.justinquinnb.onefeed;

import com.justinquinnb.onefeed.data.model.source.ContentSource;
import com.justinquinnb.onefeed.data.sources.github.GitHubService;
import com.justinquinnb.onefeed.data.sources.instagram.InstaService;
import com.justinquinnb.onefeed.data.sources.linkedin.LinkedInService;
import com.justinquinnb.onefeed.data.sources.sample.SampleService;
import com.justinquinnb.onefeed.data.sources.threads.ThreadsService;
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
	public static final HashMap<String, ContentSource> contentSources = new HashMap<>();
	private static final Logger logger = LoggerFactory.getLogger(OneFeedApplication.class);

	public static void main(String[] args) {
		// On shutdown script
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				logger.info("Shutting down...");
			}
		});

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
	 * Gets and stores instance of each {@link ContentSource} provided in {@link com.justinquinnb.onefeed.data.sources}
	 * directory.
	 */
	private static void getContentSources() {
		logger.info("Instantiating Content Sources...");

		// TODO replace this with the plugin system... register them here and automatically grab info
		contentSources.put("IG", new InstaService());
		logger.info("Instagram service \"{}\" instantiated.", "IG");

		contentSources.put("TH", new  ThreadsService());
		logger.info("Threads service \"{}\" instantiated.", "TH");

		contentSources.put("LI", new LinkedInService());
		logger.info("LinkedIn service \"{}\" instantiated.", "LI");

		contentSources.put("GH", new GitHubService());
		logger.info("GitHub service \"{}\" instantiated.", "GH");

		contentSources.put("SP", new SampleService());
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

		for (ContentSource source : contentSources.values()) {
			if (source.isAvailable()) {
				logger.info("Content Source \"{}\" is available.", source.getSourceName());
				successCount++;
			} else {
				logger.warn("Content Source \"{}\" is unavailable.", source.getSourceName());
				failCount++;
			}
		}

		if (failCount == contentSources.size()) {
			logger.warn("Content Sources tested with all {} unavailable.", failCount);
		} else if (failCount > 0) {
			logger.warn("Content Sources tested with {} available and {} unavailable.", successCount, failCount);
		} else {
			logger.info("All Content Sources are available.");
		}
	}
}