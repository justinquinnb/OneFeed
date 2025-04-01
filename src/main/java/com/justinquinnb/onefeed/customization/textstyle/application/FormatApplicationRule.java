package com.justinquinnb.onefeed.customization.textstyle.application;

import com.justinquinnb.onefeed.JsonToString;
import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.formattings.TextFormatting;

import java.util.function.Function;

/**
 * An indication of how to apply a {@link TextFormatting} type to text in order to generate
 * {@link FormattingMarkedText}.
 */
public class FormatApplicationRule {
    /**
     * The type of {@link TextFormatting} that {@code this} {@code FormatApplicationRule}'s {@link #process} specifies
     * the  markup procedure for.
     */
    private final Class<? extends TextFormatting> formatting;

    /**
     * The {@link Function} that text formatted with {@code this} {@link FormatApplicationRule}'s {@link #formatting}
     * is piped into in order to produce some marked-up/formatted text.
     */
    private final Function<FormattingMarkedText, MarkedUpText> process;

    /**
     * Creates a {@link FormatApplicationRule} mapping the provided {@link TextFormatting} to a {@code Function} that
     * can apply it to text.
     *
     * @param formatting the type of {@link TextFormatting} that the process should be invoked to generate
     * @param process the {@code Function} that can apply the specified type of {@code formatting} to text
     */
    public FormatApplicationRule(
            Class<? extends TextFormatting> formatting, Function<FormattingMarkedText, MarkedUpText> process
    ) {
        this.formatting = formatting;
        this.process = process;
    }

    /**
     * Gets {@code this} {@link FormatApplicationRule}'s {@link #formatting}.
     *
     * @return the {@link TextFormatting} that {@code this} {@code FormatApplicationRule}'s {@link #process} specifies
     * the markup procedure for
     */
    public final Class<? extends TextFormatting> getFormatting() {
        return this.formatting;
    }

    /**
     * Gets {@code this} {@link FormatApplicationRule}'s {@link #process}.
     *
     * @return the {@link Function} that specifies how to mark up/format text that calls for {@code this}
     * {@code FormatApplicationRule}'s {@link #formatting}
     */
    public final Function<FormattingMarkedText, MarkedUpText> getProcess() {
        return this.process;
    }

    /**
     * Formats/marks up the text marked by the {@link TextFormatting} specified by {@code this}
     * {@link FormatApplicationRule}'s {@link #formatting}
     *
     * @param markedText the {@link #formatting}-marked text to be marked up by {@code this}
     *                  {@code FormatApplicationRule}'s formatting {@link #process}
     *
     * @return {@code markedText}'s text marked up to achieve the desired formatting in the desired markup language
     */
    public final MarkedUpText apply(FormattingMarkedText markedText) {
        return this.process.apply(markedText);
    }

    @Override
    public String toString() {
        return JsonToString.of(this);
    }
}