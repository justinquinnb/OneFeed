package com.justinquinnb.onefeed.api;

import com.justinquinnb.onefeed.data.model.content.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class ContentController {

    private ContentService contentService;

    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    /**
     * Aggregates {@code count}-many pieces of content from the provided {@code fromSources} between the instants specified
     * by {@code betweenTimes}.
     *
     * @param contentCount the amount of content to attempt aggregating, if that many exists.
     * @param fromSources OPTIONAL - a {@code +}-delimited series of content source identifiers
     * @param betweenTimes OPTIONAL - an encoded time/date range string, using the format specified in {@link #parseTime}
     *
     * @return {@code count}-many pieces of content that meets all the provided requirements.
     */
    @GetMapping("/content")
    public Optional<Content[]> getContent(
            @RequestParam(name = "count") Integer contentCount,
            @RequestParam(name = "from") Optional<String> fromSources,
            @RequestParam(name = "between") Optional<String> betweenTimes
    ) {
        // If a source filter is present, parse and use it
        String[] sources = new String[]{""};
        if (fromSources.isPresent()) {
            sources = fromSources.get().split("+");
        }

        // If a time/date filter is present, parse and use it
        LocalDateTime[] timeRange = new LocalDateTime[]{LocalDateTime.now(), LocalDateTime.MIN};
        if (betweenTimes.isPresent()) {
            timeRange = parseTimeRange(betweenTimes.get());
        }

        // Now grab the content with the correct method
        if (fromSources.isPresent() && betweenTimes.isPresent()) {
            return ContentService.getContent(contentCount, sources, timeRange);
        } else if (fromSources.isPresent()) {
            return ContentService.getContent(contentCount, sources);
        } else if (betweenTimes.isPresent()) {
            return ContentService.getContent(contentCount, timeRange);
        } else {
            return ContentService.getContent(contentCount);
        }
    }

    /**
     * Instantiates an array of {@code LocalDateTime} objects representing the time range encoded
     * in {@code encodedRange}. This range is inclusive.
     *
     * @param encodedRange a string encoding an inclusive time range as follows:
     *                     {@code yyyyMMddHHmm-yyyyMMddHHmm} <br><br>
     *
     *                     Everything left of the "-" contains the start date and time and everything to the right
     *                     contains the end date and time. The symbols can be interpreted using the key in
     *                     {@link #parseTime(String)}.<br><br>
     *
     *                     As this method is strictly used for filtering feed content, everything beyond minutes has
     *                     been omitted, defaulting to {@code 0}.
     *
     * @return an array of {@code 2} {@code LocalDateTime} objects representing the time and dates encoded by
     * {@code encodedRange}. The {@code LocalDateTime} object at index {@code 0} is always the start date of the range.
     * The {@code LocalDateTime} object at index {@code 0} is always the end date of the range.
     *
     * @throws IllegalArgumentException if the time range is either malformed or invalid.
     */
    private static LocalDateTime[] parseTimeRange(String encodedRange) throws IllegalArgumentException {
        try {
            LocalDateTime from = parseTime(encodedRange.substring(0,12));
            LocalDateTime to = parseTime(encodedRange.substring(13));

            if (!from.isBefore(to)) {
                throw new IllegalArgumentException("To time must be before from time");
            }

            return new LocalDateTime[]{from, to};
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid time range: " + encodedRange);
        }
    }

    /**
     * Parses an {@code encodedDateTime} into a {@code LocalDateTime} object.
     *
     * @param encodedDateTime a date time encoded in the following format:
     *                         <ul>
     *                          <li>{@code yyyy} four-digit year</li>
     *                          <li>{@code MM} two-digit month</li>
     *                          <li>{@code dd} two-digit day</li>
     *                          <li>{@code HH} two-digit hour</li>
     *                          <li>{@code mm} two-digit minute</li>
     *                         </ul>
     * @return a {@code LocalDateTime} representing the time and date encoded in {@code encodedDateTime}.
     * @throws IllegalArgumentException if the {@code encodedDateTime} is malformed.
     */
    private static LocalDateTime parseTime(String encodedDateTime) throws IllegalArgumentException {
        try {
            int year = Integer.parseInt(encodedDateTime.substring(0, 4));
            int month = Integer.parseInt(encodedDateTime.substring(4, 6));
            int day = Integer.parseInt(encodedDateTime.substring(6, 8));
            int hour = Integer.parseInt(encodedDateTime.substring(8, 10));
            int minute = Integer.parseInt(encodedDateTime.substring(10, 12));

            return LocalDateTime.of(year, month, day, hour, minute);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Malformed time: " + encodedDateTime);
        }
    }
}
