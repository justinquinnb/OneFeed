package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A type capable of generating {@link MarkedUpText} in HTML provided some text.
 */
public interface Html extends MarkupLanguage {
    /**
     * Marks up the provided {@code text} in HTML according to the formatting specifications specified in the
     * implementing class.
     *
     * @param text the {@code String} to apply some desired HTML to
     *
     * @return the {@code text} formatted in the desired way using HTML
     */
    public MarkedUpText applyHtml(String text);

    /**
     * Generates {@link FormattingMarkedText} marked with the {@code target} {@link TextFormatting} from the content
     * contained within the first HTML element in {@code text} matching one of the provided {@code elementNames}.
     *
     * @param text the {@link MarkedUpText} possibly containing an element with one of the provided {@code elementNames}
     * @param elementNames the possible names of the element to try matching in the {@code text}
     *
     * @return a {@code FormattingMarkedText} object with the content of the first element matching one of the provided
     * {@code elementNames} in {@code text}, marked with the {@code target} {@code TextFormatting}
     * @throws IllegalStateException if no well-formatted elements with a name in {@code elementNames} can be found in
     * the provided {@code text}
     */
    public static FormattingMarkedText extractContentFromElement(
            MarkedUpText text, TextFormatting target, String ... elementNames) throws IllegalStateException
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
     * @param text
     * @param attributeName
     * @param elementName
     *
     * @return // TODO document
     * @throws IllegalStateException
     */
    public static String extractAttributeFromElement(MarkedUpText text, String attributeName, String elementName)
            throws IllegalStateException
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
            throw new IllegalStateException("Desired attribute \"" + attributeName + "\" could not be found for element " +
                    "\"" + elementName + "\" in text \"" + text.getText() + "\"");
        }

        String attribute = attributeMatch.group();

        // Finally, parse out the actual value
        Pattern valStartBound = Pattern.compile(attributeName + "=\"");
        Pattern valEndBound = Pattern.compile("\"$");

        return MarkupLanguage.parseTextBetweenBounds(attribute, valStartBound, valEndBound);
    }
}