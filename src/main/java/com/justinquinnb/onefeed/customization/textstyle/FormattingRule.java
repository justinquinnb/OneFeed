package com.justinquinnb.onefeed.customization.textstyle;

import java.util.function.Function;

/**
 * An indication of how to mark up text that requires some type of {@link TextFormatting}, {@link #formatting}.
 *
 * @param <T> the language that the {@link TextFormatting} {@code this} {@link FormattingRule}'s {@code formatting}
 *           belongs to
 */
public class FormattingRule<T extends TextFormatting> {
    /**
     * The type of {@link TextFormatting} that {@code this} {@code FormattingRule}'s {@link #process} specifies the
     * markup procedure for. Must belong to the language specified by {@link T}.
     */
    private final Class<? extends T> formatting;

    /**
     * The {@link Function} that text formatted with {@code this} {@link FormattingRule}'s {@link #formatting} is piped
     * into in order to produce some marked-up/formatted text.
     */
    private final Function<FormattingMarkedText<T>, String> process;

    /**
     * Creates a {@link FormattingRule} mapping the provided {@link TextFormatting} to a {@code Function} that can
     * apply it to text.
     *
     * @param formatting the type of {@link TextFormatting} that the process should be invoked to generate
     * @param process the {@code Function} that can apply the specified type of {@code formatting} to text
     */
    public FormattingRule(Class<? extends T> formatting, Function<FormattingMarkedText<T>, String> process) {
        this.formatting = formatting;
        this.process = process;
    }

    /**
     * Gets {@code this} {@link FormattingRule}'s {@link #formatting}.
     *
     * @return the {@link TextFormatting} that {@code this} {@code FormattingRule}'s {@link #process} specifies the
     * markup procedure for
     */
    public Class<? extends T> getFormatting() {
        return this.formatting;
    }

    /**
     * Gets {@code this} {@link FormattingRule}'s {@link #process}.
     *
     * @return the {@link Function} that specifies how to mark up/format text that calls for {@code this}
     * {@code FormattingRule}'s {@link #formatting}
     */
    public Function<FormattingMarkedText<T>, String> getProcess() {
        return this.process;
    }

    /**
     * Formats/marks up the text marked by the {@link TextFormatting} specified by {@code this} {@link FormattingRule}'s
     * {@link #formatting}
     *
     * @param markedText the {@link #formatting}-marked text to be marked up by {@code this} {@code FormattingRule}'s
     *                   formatting {@link #process}
     *
     * @return {@code markedText}'s text marked up to achieve the desired formatting in the desired markup language
     */
    public String apply(FormattingMarkedText<T> markedText) {
        return this.process.apply(markedText);
    }
}