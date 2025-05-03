package com.justinquinnb.onefeed.customization.textstyle.markup;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.FormattingMismatchException;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.MarkupLangMismatchException;
import com.justinquinnb.onefeed.customization.textstyle.application.TextFormattingApplier;
import com.justinquinnb.onefeed.customization.textstyle.parsing.TextFormattingParser;

import java.text.ParseException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A type capable of generating {@link MarkedUpText} in HTML provided some text.
 *
 *
 * @implNote
 * While the intention is for multiple types of {@link TextFormatting}s to implement this interface when they are
 * compatible with HTML, multiple {@code TextFormatting} definitions and behaviors can be defined and registered
 * anywhere this interface is implemented. While it is discouraged for consistency and maintainability, it may be
 * desirable or offer unknown advantages that are open to exploration and utilization.
 *
 * @implSpec
 * <b>Implementing & Registering a Type of {@code MarkupLanguage}</b><br>
 * Most of the functionality that an implementor of a {@code MarkupLanguage} type must perform is static. For this
 * reason, developers must, in the static body of their implementing type, use
 * {@link TextFormattingRegistry#registerForLanguage} for each implemented language for the {@code TextFormatting} and
 * its various {@code MarkupLanguage} type-specific implementations to be recognized by OneFeed. It is advised to
 * create static methods titled {@code extractFrom___}, {@code apply___}, and {@code get___Pattern} methods that are
 * passed as lambdas to the {@code registerForLanguage} function for consistency's sake, but any means of providing a
 * valid function as an argument is acceptable.
 * <br><br>
 * Developers are encouraged to throw the following exceptions for the following methods so their handling can be
 * globally handled by the employed {@link TextFormattingParser} and {@link TextFormattingApplier}:
 * <br><br>
 * <b>{@code extractFrom___() throws }</b>
 *     <ul>
 *         <li><b>{@link MarkupLangMismatchException}</b> when the provided {@link MarkedUpText} does not employ the
 *         expected {@link MarkupLanguage} type.</li>
 *         <li><b>{@link ParseException}</b> when a {@code TextFormatting} instance cannot be parsed from the
 *         {@code MarkedUpText}'s text.
 *     </ul>
 * <b>{@code apply___() throws }</b>
 *     <ul>
 *         <li><b>{@link FormattingMismatchException}</b> when the provided {@link FormattingMarkedText} does not
 *         employ the expected {@code TextFormatting} type.</li>
 *     </ul>
 * <br><br>
 * In addition to the static methods, each interface will mandate the implementation of some instance methods.
 */
public non-sealed interface Html extends MarkupLanguage {
    /**
     * Marks up the provided {@code text} in HTML according to the formatting specifications specified in the
     * implementing class.
     *
     * @param text the {@code String} to apply some desired HTML to
     *
     * @return the {@code text} formatted in the desired way using HTML
     */
    public MarkedUpText applyAsHtmlTo(String text);

    /**
     * Generates {@link FormattingMarkedText} marked with the {@code target} {@link TextFormatting} from the content
     * contained within the first HTML element in {@code text} matching one of the provided {@code elementNames}.
     *
     * @param text the {@link MarkedUpText} possibly containing an element with one of the provided {@code elementNames}
     * @param elementNames the possible names of the element to try matching in the {@code text}
     *
     * @return a {@code FormattingMarkedText} object with the content of the first element matching one of the provided
     * {@code elementNames} in {@code text}, marked with the {@code target} {@code TextFormatting}
     * @throws ParseException if no well-formatted elements with a name in {@code elementNames} can be found in
     * the provided {@code text}
     */
    public static FormattingMarkedText extractContentFromElement(
            MarkedUpText text, TextFormatting target, String ... elementNames) throws ParseException
    {
        // Find the outermost element tags in order to determine that outermost element's content
        HashMap<Pattern, Pattern> elementTags = new HashMap<>();

        // Build all possible tag pairs
        for (String elementName : elementNames) {
            Pattern openTag = Pattern.compile("^(<" + elementName + "\s(.*)>)", Pattern.DOTALL);
            Pattern closeTag = Pattern.compile("(</" + elementName + "\s>)$", Pattern.DOTALL);
            elementTags.put(openTag, closeTag);
        }

        // Attempt to parse out the content
        // Determine the tag used so the correct content is formatted
        Pattern startTag = MarkupLanguage.findEarliestMatch(
                text, elementTags.keySet().toArray(new Pattern[0]));
        Pattern endTag = elementTags.get(startTag);

        return MarkupLanguage.parseFmtBetweenBounds(text, startTag, endTag, target);
    }

    /**
     * Extracts the value of the attribute named {@code attributeName} in the first element with the name
     * {@code elementName} that occurs in the {@code text}.
     *
     * @param text {@code MarkedUpText} possibly containing an element with the desired {@code elementName} and an
     *             attribute inside it with name {@code attributeName} whose value to return
     * @param attributeName the name of the attribute in the element named {@code elementName} whose value to try
     *                      parsing out
     * @param elementName the name of the element in the {@code MarkedUpText}'s text with an attribute named
     *                    {@code attributeName} to try and extract
     *
     * @return the value of the attribute with key {@code attributeName} in element {@code elementName}, if both were
     * found in the provided {@code text}
     * @throws ParseException if either the desired element or attribute could not be parsed from the provided
     * {@code text}
     */
    public static String extractAttributeFromElement(MarkedUpText text, String attributeName, String elementName)
            throws ParseException
    {
        // Parse out the attributes list from the first match of the desired element
        Pattern attributesStartBound = Pattern.compile("^(<" + elementName);
        Pattern attributesEndBound = Pattern.compile("(>(.*)</" + elementName + "\s>)$", Pattern.DOTALL);

        String elementAttributes = MarkupLanguage.parseTextBetweenBounds(
                text.getText(), attributesStartBound, attributesEndBound);

        // Then parse out the desired attribute
        Pattern attributePattern = Pattern.compile(attributeName + "=\"(.*)\"", Pattern.DOTALL);

        Matcher attributeMatch = attributePattern.matcher(elementAttributes);

        // Stop if a match for the attribute wasn't found
        if (!attributeMatch.find()) {
            throw new ParseException("Desired attribute \"" + attributeName + "\" could not be found for element " +
                    "\"" + elementName + "\" in text \"" + text.getText() + "\"", 0);
        }

        String attribute = attributeMatch.group();

        // Finally, parse out the actual value
        Pattern valStartBound = Pattern.compile(attributeName + "=\"");
        Pattern valEndBound = Pattern.compile("\"$");

        return MarkupLanguage.parseTextBetweenBounds(attribute, valStartBound, valEndBound);
    }
}