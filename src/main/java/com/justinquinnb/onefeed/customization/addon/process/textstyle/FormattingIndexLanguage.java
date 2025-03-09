package com.justinquinnb.onefeed.customization.addon.process.textstyle;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Specifies the language of {@link TextFormat}s in use.
 */
public class FormattingIndexLanguage {
    /**
     * The {@link TextFormat}s being used in {@code this} {@link FormattingIndexLanguage}
     */
    private TextFormat[] usedFormats;

    /**
     * Creates a {@link FormattingIndexLanguage} using the {@link TextFormat}s specified by {@code useFormats}.
     * @param useFormats the {@code TextFormat}s that comprise {@code this} {@code FormattingIndexLanguage}
     */
    public FormattingIndexLanguage(TextFormat[] useFormats) {
        this.usedFormats = Arrays.copyOf(useFormats, useFormats.length);
    }

    /**
     * Gets the {@code TextFormat}s used by {@code this} {@code FormattingIndexLanguage}.
     *
     * @return the {@code TextFormat}s used by {@code this} {@code FormattingIndexLanguage}
     */
    public TextFormat[] getUsedFormats() {
        return Arrays.copyOf(usedFormats, usedFormats.length);
    }

    /**
     * Checks whether {@code this} {@link FormattingIndexLanguage} uses the provided {@code format}.
     *
     * @param format the {@link TextFormat} whose usage in {@code this} {@code FormattingIndexLanguage} to check
     * @return {@code true} if {@code this} {@code FormattingIndexLanguage} uses the provided {@code format}.
     */
    public boolean contains(TextFormat format) {
        boolean found = false;
        int i = 0;

        // Compare only by type, as that's all that matters for a TextFormat instance.
        while(!found && i < usedFormats.length) {
            if (format.getClass().equals(usedFormats[i].getClass())) {
                found = true;
            }
            i++;
        }

        return found;
    }

    /**
     * Compares {@code this} {@link FormattingIndexLanguage} to the other {@code FormattingIndexLanguage}, {@code other},
     * on the basis of same {@code TextFormat} usage (contents of {@link #usedFormats} match, ignoring order and
     * duplicates).
     * @param other the {@code FormattingIndexLanguage} to compare to
     *
     * @return {@code true} if {@code this} {@code FormattingIndexLanguage} uses the exact same {@code TextFormat}s as
     * the other one, {@code other}, otherwise (or if {@code null} is passed) {@code false}
     */
    public boolean matches(FormattingIndexLanguage other) {
        // Avoid NPEs
        if (other == null) {
            return false;
        }

        // Ignore duplicates and order

        // TODO replace this and all other usages with something that compares not on instance but on format contents
        HashSet<TextFormat> thisLangsFormats = new HashSet<TextFormat>(Arrays.asList(this.usedFormats));
        HashSet<TextFormat> otherLangsFormats = new HashSet<TextFormat>(Arrays.asList(other.getUsedFormats()));
        return thisLangsFormats.equals(otherLangsFormats);
    }

    public boolean isSubsetOf(FormattingIndexLanguage other) {
        if (this.matches(other)) {
            return true;
        } else if (other == null) {
            return false;
        }

        HashSet<TextFormat> thisLangsFormats = new HashSet<TextFormat>(Arrays.asList(this.usedFormats));
        HashSet<TextFormat> otherLangsFormats = new HashSet<TextFormat>(Arrays.asList(other.getUsedFormats()));
        return otherLangsFormats.containsAll(thisLangsFormats);
    }

    public boolean isSupersetOf(FormattingIndexLanguage other) {
        if (this.matches(other)) {
            return true;
        } else if (other == null) {
            return false;
        }

        HashSet<TextFormat> thisLangsFormats = new HashSet<TextFormat>(Arrays.asList(this.usedFormats));
        HashSet<TextFormat> otherLangsFormats = new HashSet<TextFormat>(Arrays.asList(other.getUsedFormats()));
        return thisLangsFormats.containsAll(otherLangsFormats);
    }
}