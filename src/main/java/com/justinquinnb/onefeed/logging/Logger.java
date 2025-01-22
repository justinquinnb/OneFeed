package com.justinquinnb.onefeed.logging;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * Handles error and event logging.
 */
public class Logger {
    private static final String logDirFilepath = "src/main/resources/logs";

    private static String currentLog;

    // METHODS

    /**
     * Logs the provided {@code message} to the console.
     *
     * @param message the message to log to the console.
     */
    public static void logToConsole(String message) {
        System.out.println("[" + getTimestamp() + "] " + message);
    }

    /**
     * Logs the provided {@code message} to the console with formatting.
     *
     * @param message the message to log to the console.
     */
    public static void logToConsoleF(String message) {
        System.out.println(formatForConsole(message));
    }

    /**
     * Logs the provided {@code message} to the log file.
     *
     * @param message the message to log in the log file
     */
    public static void logToFile(String message) {
        currentLog += "[" + getTimestamp() + "] " + message + "\n";
    }

    /**
     * Logs the provided {@code message} to the log file with formatting.
     *
     * @param message the message to log in the log file
     */
    public static void logToFileF(String message) {
        currentLog += formatForFile(message) + "\n";
    }

    /**
     * Logs the same message to the console and log file.
     *
     * @param message the message to log in the console and log file
     */
    public static void logToBoth(String message) {
        logToConsole(message);
        logToFile(message);
    }

    /**
     * Logs the same message to the console and log file with formatting.
     *
     * @param message the message to log in the console and log file
     */
    public static void logToBothF(String message) {
        logToConsoleF(message);
        logToFileF(message);
    }

    /**
     * Logs a different message to the console and log file.
     *
     * @param consoleMessage the message to log to the console
     * @param fileMessage the message to log in the log file
     */
    public static void diffLogToBoth(String consoleMessage, String fileMessage) {
        logToConsole(consoleMessage);
        logToFile(fileMessage);
    }

    /**
     * Logs a different message to the console and log file with formatting.
     *
     * @param consoleMessage the message to log to the console
     * @param fileMessage the message to log in the log file
     */
    public static void diffLogToBothF(String consoleMessage, String fileMessage) {
        logToConsoleF(consoleMessage);
        logToFileF(fileMessage);
    }

    /**
     * Generates a timestamp representing the moment of execution.
     *
     * @return a timestamp in {@code yyyy-MM-dd HH:mm:ss} format.
     */
    private static String getTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    /**
     * Generates a timestamp representing the moment of execution.
     *
     * @param dateSeparator the character delimiting each section of the date
     * @param timeSeparator the character delimiting each section of the time
     *
     * @return a timestamp in {@code yyyy-MM-dd HH:mm:ss} format.
     */
    private static String getTimestamp(char dateSeparator, char timeSeparator) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "yyyy" + dateSeparator + "MM" + dateSeparator + "dd HH" + timeSeparator + "mm" + timeSeparator + "ss"
        );
        return now.format(formatter);
    }

    /**
     * Gets the filepath to the latest log file.
     *
     * @return the filepath to the latest log file.
     */
    private static String getLinkToLastLog() {
        String latestFilepath = "";

        // Get all the log file names
        File logDir = new File(logDirFilepath);
        File[] logs = logDir.listFiles();

        // Find the latest log
        for(File log : logs) {
            if(log.getName().compareTo(latestFilepath) > 0) {
                latestFilepath = log.getPath();
            }
        }

        return latestFilepath;
    }

    /**
     * Saves and closes the current log to a timestamped file in the {@link #logDirFilepath}.
     */
    public static void endLog() {
        try {
            // Create the new log file
            String filename = logDirFilepath + "/" + getTimestamp('-', '.') + ".txt";
            File newLog = new File(filename);
            newLog.createNewFile();

            FileWriter writer = new FileWriter(filename);
            writer.write(currentLog);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Formats the provided string using the following substitutions:<br><br>
     * {@code %s} -> current {@code [yyyy-MM-dd hh:mm:ss]} timestamp<br>
     * {@code %i} -> {@code INFO} tag<br>
     * {@code %w} -> {@code WARN} tag<br>
     * {@code %f} -> {@code FAIL} tag<br>
     * {@code %s} -> {@code SUCCESS} tag
     *
     * @param toFormat the message to format
     * @return the original message with the substitutions made.
     */
    private static String formatForConsole(String toFormat) {
        HashMap<Character, String> encodingMap = new HashMap<>();;
        encodingMap.put('t', "[" + getTimestamp() + "]");
        encodingMap.put('i', MessageTags.INFO.toConsoleString());
        encodingMap.put('w', MessageTags.WARN.toConsoleString());
        encodingMap.put('f', MessageTags.FAIL.toConsoleString());
        encodingMap.put('s', MessageTags.SUCCESS.toConsoleString());

        return format(toFormat, encodingMap);
    }

    private static String formatForFile(String toFormat) {
        HashMap<Character, String> encodingMap = new HashMap<>();;
        encodingMap.put('t', "[" + getTimestamp() + "]");
        encodingMap.put('i', MessageTags.INFO.toFileString());
        encodingMap.put('w', MessageTags.WARN.toFileString());
        encodingMap.put('f', MessageTags.FAIL.toFileString());
        encodingMap.put('s', MessageTags.SUCCESS.toFileString());

        return format(toFormat, encodingMap);
    }

    private static String format(String toFormat, HashMap<Character, String> encodingMap) {
        String formatted = toFormat;
        String substitution = "";
        String firstPart, lastPart;
        int lookFromIndex = 0;

        // While format flags exist...
        while(lookFromIndex < formatted.length() && formatted.substring(lookFromIndex).contains("%")) {
            // Find the next flag
            lookFromIndex = formatted.indexOf("%", lookFromIndex);

            // Determine the correct substitution
            substitution = encodingMap.get(formatted.charAt(lookFromIndex + 1));

            if (!substitution.isEmpty()) {
                firstPart = formatted.substring(0, lookFromIndex);
                lastPart = formatted.substring(lookFromIndex + 2);

                formatted = firstPart + substitution + lastPart;
            }

            lookFromIndex += 1;
        }

        return formatted;
    }
}
