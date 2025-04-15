package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.text.ParseException;
import java.util.regex.Pattern;

/**
 * A type capable of generating {@link MarkedUpText} in the originally-defined Markdown language provided some text.
 */
public interface Markdown extends MarkupLanguage {
    /**
     * Marks up the provided {@code text} in Markdown according to the formatting specifications specified in the
     * implementing class.
     *
     * @param text the {@code String} to apply some desired Markdown to
     *
     * @return the {@code text} formatted in the desired way using Markdown
     */
    public MarkedUpText applyMd(String text);

    /**
     * Provides the {@link Pattern} delimiting a type of {@link TextFormatting} in Markdown.
     *
     * @return the {@code Pattern} specifying how the implementing type is formatted in the Markdown
     *
     * @implSpec If no {@code Pattern} exists for the type in Markdown, {@code null} should be
     * returned.
     */
    public Pattern getMdPattern();

    /**
     * Generates a {@link FormattingMarkedText} object that specifies how the {@code text} should be formatted given
     * its markup in Markdown. The returned {@code FormattingMarkedText} contains the original {@code text} with all of
     * its markup removed, making it markup-language-agnostic.
     *
     * @param text the text formatted as {@code this} {@link Html}-implementing type
     *
     * @return a {@code FormattingMarkedText} object representing the type of formatting specified by the Markdown
     * contained in {@code text} with a copy of {@code text} that has all of its markup removed
     *
     * @implNote
     * If the desired {@code TextFormatting} could not be extracted from the provided {@code text}, it is strongly
     * encouraged to return a new {@code FormattingMarkedText} instance containing the text contained in {@code text}
     * as-is with a default {@code TextFormatting} type such as the provided {@link DefaultFormat}.
     * <br><br>
     * Also note that the produced {@code FormattingMarkedText} may not employ the same type of {@link TextFormatting}
     * as {@code this} {@code Html} language implementor. In some cases, this may be the intended behavior and is
     * handled without issue by OneFeed. Generally speaking though, such behavior should be avoided.
     * <br><br>
     * Additionally important to consider: the {@code text} provided to this method may not contain markup for the
     * implementing type. In the majority of cases, the {@code text} will contain just the content itself and its
     * surrounding and embedded formatting markup (see example A). However, as it is possible for the text to include
     * more (example B), it is encouraged to implement parsing that can account for this edge case, even when that edge
     * case has no practical use, does not adhere to the principles of the system, and therefore should not be employed.
     * <ul>
     *     <li>
     *         <b>Example A:</b> Expected {@code String} in the provided {@code MarkedUpText}<br>
     *         {@code **context *text***}
     *     </li>
     *     <li>
     *         <b>Example B:</b> Possible (but unlikely) edge case<br>
     *         {@code Junk surrounding the **content *text***}
     *     </li>
     * </ul>
     */
    public FormattingMarkedText extractFromMd(MarkedUpText text);
}