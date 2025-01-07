package com.justinquinnb.onefeed;

import com.justinquinnb.onefeed.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.justinquinnb.onefeed.data.model.source.SourceAddon;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class OneFeedApplication {
	/**
	 * Contains instances of all the recognized data sources.
	 */
	private static SourceAddon[] sources;

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				Logger.logToBoth("Exiting application...");
				Logger.endLog();
			}
		});

		// Console info
		printOneFeedInfo();
		try {
			// Initialize sources
			registerSources();
			testSources();

			Logger.logToBothF("%t %s - Initialization successful.");
			Logger.logToBothF("%t Starting Spring Boot...");

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
		Logger.logToBoth("[Run] Starting OneFeed...");
		Logger.logToBothF("""
				\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				OneFeed v0.0.1 - The Free Feed Aggregator
				Developed by Justin Quinn - https://github.com/justinquinnb
				~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				""");
		Logger.logToBoth("Initializing...");
	}

	/**
	 * Register all data sources.
	 */
	private static void registerSources() {
		Logger.logToBothF("\t%t [1/2] Registering data sources...");

		// TODO init all sources by pushing info.xml files of each package in addon and base
		// dirs into the OneFeedSource constructor. Print each, indicating success or failure.
		// "

		// Grab all files from sources directory
		File sourceDir = new File("src/main/java/com/justinquinnb/onefeed/data/sources");
		File[] sourceFiles = sourceDir.listFiles();

		if (sourceFiles != null) {
			// Files exist in the sources directory, so check if they're valid addons and register them if so
			ArrayList<SourceAddon> sourceAddons = new ArrayList<>();
			File sourceAddonInfo;
			SourceAddon sourceAddon;

			// For every file/dir in the sources dir, check if it's a valid addon and register it if so
			for (File sourceFile : sourceFiles) {
				sourceAddonInfo = new File(sourceFile.getPath() + "/info.xml");
				if (sourceAddonInfo.exists()) {
					sourceAddon = new SourceAddon(sourceFile.getPath());
					sourceAddons.add(sourceAddon);

					Logger.diffLogToBothF(
							"\t\t%t Registered: " + sourceAddon.getAddonName() + "v" + sourceAddon.getAddonVersion() + " for " + sourceAddon.getSourceName(),
							"\t\t%t Registered: " + sourceAddon.getAddonName() + "v" + sourceAddon.getAddonVersion() + " for " + sourceAddon.getSourceName() + " by " + sourceAddon.getAddonAuthor() +
									"\n\t\t\tSource implementor: " + sourceAddon.getSource().getName() + "\n\t\t\tSee " + sourceAddon.getAddonUrl() + " for more info."
					);
				}
			}

			sources = sourceAddons.toArray(new SourceAddon[0]);
		} else {
			throw new IllegalStateException("No source addons found.");
		}

		// Update status
		Logger.logToBothF("\t%t [1/2] %s - " + sources.length + " data sources registered.");
	}

	/**
	 * Test all base and addon data sources.
	 */
	private static void testSources() {
		Logger.logToBothF("\t%t [2/2] Testing data sources...");

		// TODO run test on all sources, printing each one's success or failure.

		// Update status
		int successCount = 0, failCount = 0;

		// TODO WARN if there are some failures, otherwise precede with SUCCESS like above method
		if (failCount > 0) {
			Logger.logToBothF(
					"\t%t [2/2] %w - Data sources tested with " + successCount + "successes and " + failCount + " fails."
			);
		} else {
			Logger.logToBothF("\t%t [2/2] %s - All data sources are available.");
		}
	}
}