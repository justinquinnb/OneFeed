package com.justinquinnb.onefeed.api;

import com.justinquinnb.onefeed.data.model.content.Content;
import com.justinquinnb.onefeed.exceptions.IllegalContentCountException;
import com.justinquinnb.onefeed.exceptions.InvalidTimeException;
import com.justinquinnb.onefeed.exceptions.InvalidTimeRangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class ContentController {
    private ContentService contentService;
    private static final Logger logger = LoggerFactory.getLogger(ContentController.class);

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
    // TODO make this return a ResponseEntity type instead, implementing an exception handler (controller advice) to intercept
    @GetMapping("/content")
    public Content[] getContent(
            @RequestParam(name = "count") Integer contentCount,
            @RequestParam(name = "from") Optional<String> fromSources,
            @RequestParam(name = "between") Optional<String> betweenTimes
    ) throws ExecutionException, InterruptedException {
        logger.info("Content request received with arguments: contentCount={} fromSources={} betweenTimes={}",
                contentCount.toString(), fromSources.toString(), betweenTimes.toString());

        // Require a valid content count
        if (contentCount <= 0) {
            logger.warn("{} is an illegal content Count: contentCount must be greater than 0", contentCount);
            throw new IllegalContentCountException("Content count is invalid: " + contentCount);
        }

        // If a source filter is present, parse and use it
        String[] sources = new String[]{""};
        if (fromSources.isPresent()) {
            sources = fromSources.get().split("\\+");
            logger.debug("Content Source IDs identified in request: {}", Arrays.toString(sources));
        }

        // If a time/date filter is present, parse and use it
        Instant[] timeRange = new Instant[]{Instant.now(), Instant.MIN};
        if (betweenTimes.isPresent()) {
            timeRange = parseTimeRange(betweenTimes.get());
            logger.debug("timeRange identified in request: {}", Arrays.toString(timeRange));
        }

        // Now grab the content with the correct method
        CompletableFuture<Content[]> possibleResponse;

        if (fromSources.isPresent() && betweenTimes.isPresent()) {
            logger.debug("Processing request for: {} pieces from {} between {} and {}",
                    contentCount, Arrays.toString(sources), timeRange[0].toString(), timeRange[1].toString());

            possibleResponse = contentService.getContent(contentCount, sources, timeRange);
        } else if (fromSources.isPresent()) {
            logger.debug("Processing request for: {} pieces from {}", contentCount, Arrays.toString(sources));

            possibleResponse = contentService.getContent(contentCount, sources);
        } else if (betweenTimes.isPresent()) {
            logger.debug("Processing request for: {} pieces between {} and {}",
                    contentCount, timeRange[0].toString(), timeRange[1].toString());

            possibleResponse = contentService.getContent(contentCount, timeRange);
        } else {
            logger.debug("Processing request for: {} pieces", contentCount);

            possibleResponse = contentService.getContent(contentCount);
        }

        return possibleResponse.get();
    }

    /**
     * Instantiates an array of {@link Instant} objects representing the time range encoded
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
     * @return an array of {@code 2} {@code Instant} objects representing the time and dates encoded by
     * {@code encodedRange}. The {@code Instant} object at index {@code 0} is always the start date of the range.
     * The {@code Instant} object at index {@code 0} is always the end date of the range.
     *
     * @throws InvalidTimeRangeException if the time range is invalid or malformed.
     */
    private static Instant[] parseTimeRange(String encodedRange) throws InvalidTimeRangeException {
        logger.debug("Parsing encodedRange \"{}\"", encodedRange);
        try {
            // Parse from/to instants out from range
            Instant from = parseTime(encodedRange.substring(0,12));
            logger.trace("Parsed from from encodedRange into \"{}\"", from.toString());
            Instant to = parseTime(encodedRange.substring(13));
            logger.trace("Parsed to from encodedRange into \"{}\"", to.toString());

            // Ensure the range is valid (i.e. the instants are chronological)
            if (!from.isBefore(to)) {
                logger.warn("{} is an invalid time range: to time must be before from", encodedRange);
                throw new InvalidTimeRangeException("To time must be before from time: " + encodedRange);
            }

            logger.debug("Parsed encodedRange \"{}\" into Instants {} and {}",
                    encodedRange, from.toString(), to.toString());
            return new Instant[]{from, to};
        } catch (Exception e) {
            logger.warn("Could not parse time range \"{}\": range is invalid or malformed", encodedRange);
            logger.debug("L {}", e.getLocalizedMessage());
            throw new InvalidTimeRangeException("Invalid or malformed time range: " + encodedRange);
        }
    }

    /**
     * Parses an {@code encodedInstant} into an {@link Instant}.
     *
     * @param encodedInstant an {@code Instant} encoded in the following format:
     *                         <ul>
     *                          <li>{@code yyyy} four-digit year</li>
     *                          <li>{@code MM} two-digit month</li>
     *                          <li>{@code dd} two-digit day</li>
     *                          <li>{@code HH} two-digit hour</li>
     *                          <li>{@code mm} two-digit minute</li>
     *                         </ul>
     * @return an {@code Instant} representing the time and date encoded in {@code encodedInstant}.
     * @throws InvalidTimeException if the {@code encodedInstant} is invalid or malformed.
     */
    private static Instant parseTime(String encodedInstant) throws InvalidTimeException {
        logger.debug("Parsing encodedInstant \"{}\"", encodedInstant);
        try {
            // Separate the encodedInstant into its component time denomination integers
            int year = Integer.parseInt(encodedInstant.substring(0, 4));
            logger.trace("Parsed year into int \"{}\"", year);
            int month = Integer.parseInt(encodedInstant.substring(4, 6));
            logger.trace("Parsed month into int \"{}\"", month);
            int day = Integer.parseInt(encodedInstant.substring(6, 8));
            logger.trace("Parsed day into int \"{}\"", day);
            int hour = Integer.parseInt(encodedInstant.substring(8, 10));
            logger.trace("Parsed hour into int \"{}\"", hour);
            int minute = Integer.parseInt(encodedInstant.substring(10, 12));
            logger.trace("Parsed minute into int \"{}\"", minute);

            // Instantiate an Instant using the values parsed above
            Instant asInstant = Instant.from(LocalDateTime.of(year, month, day, hour, minute).atZone(ZoneId.of("UTC")));
            logger.debug("Parsed encodedInstant \"{}\" into {}", encodedInstant, asInstant.toString());
            return asInstant;
        } catch (NumberFormatException e) {
            logger.warn("Could not parse instant \"{}\": instant is invalid or malformed", encodedInstant);
            logger.debug("L {}", e.getLocalizedMessage());
            throw new InvalidTimeException("Invalid or malformed time: " + encodedInstant);
        }
    }
}
