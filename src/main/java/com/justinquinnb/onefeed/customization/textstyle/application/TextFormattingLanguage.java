package com.justinquinnb.onefeed.customization.textstyle.application;

import com.justinquinnb.onefeed.customization.defaults.TextFormatting;
import com.justinquinnb.onefeed.customization.textstyle.UnshrinkableHashSet;

import java.util.*;

/**
 * Specifies a language of {@link TextFormatting}s. Children of this class and any extensions inherit all of their
 * parents' {@link #usedFormattings}, effectively making each child an equivalent or superset of its parents.
 */
public abstract class TextFormattingLanguage {
    /**
     * The {@link TextFormatting}s used in {@code this} {@link TextFormattingLanguage}.
     */
    private final Set<TextFormatting> usedFormattings = new UnshrinkableHashSet<TextFormatting>();

    /**
     * Creates a {@link TextFormattingLanguage} using the {@link TextFormatting}s added in the implementation of
     * {@link #addFormattings()}.
     */
    protected TextFormattingLanguage() {
        // Add the TextFormattings specified to expand the parent TextFormattingLanguage
        this.addFormattings();
    }

    /**
     * Adds any desired {@link TextFormatting}s to the {@link TextFormattingLanguage}.
     */
     protected abstract void addFormattings();

     protected void addFormatting(TextFormatting textFormatting) {
         usedFormattings.add(textFormatting);
     }

    /**
     * Gets the {@code TextFormatting}s used by {@code this} {@code TextFormattingLanguage}.
     *
     * @return a copy of the {@code TextFormatting}s used by {@code this} {@code TextFormattingLanguage}
     */
    public Set<TextFormatting> getUsedFormattings() {
        return Set.copyOf(usedFormattings);
    }

    /**
     * Checks whether {@code this} {@link TextFormattingLanguage} uses the provided {@code format}.
     *
     * @param format the {@link TextFormatting} whose usage in {@code this} {@code TextFormattingLanguage} to check
     * @return {@code true} if {@code this} {@code TextFormattingLanguage} uses the provided {@code format}.
     */
    public boolean contains(TextFormatting format) {
        return this.usedFormattings.contains(format);
    }

    /**
     * Compares {@code this} {@link TextFormattingLanguage} to the other {@code TextFormattingLabelLanguage}, {@code other},
     * on the basis of same {@code TextFormat} usage.
     *
     * @param other the {@code TextFormattingLabelLanguage} to compare to
     *
     * @return {@code true} if {@code this} {@code TextFormattingLabelLanguage} uses the exact same {@code TextFormat}s as
     * the other one, {@code other}, otherwise (or if {@code null} is passed) {@code false}
     */
    public boolean matches(TextFormattingLanguage other) {
        // Avoid NPEs
        if (other == null) {
            return false;
        }

        // Deem the languages
        return this.usedFormattings.equals(other.getUsedFormattings());
    }

    /**
     * Determines if the {@link #usedFormattings} of {@code this} {@link TextFormattingLanguage} are a subset
     * of the {@code other} {@code TextFormattingLanguage}.
     *
     * @param other the {@code other} {@code TextFormattingLanguage} whose set of {@code usedFormattings} to compare to
     * @return {@code true} if {@code this} {@code TextFormattingLanguage}'s {@code usedFormattings} match or are a
     * subset of the {@code other}'s
     */
    public boolean isSubsetOf(TextFormattingLanguage other) {
        // NPEs are avoided through short-circuit eval with matches() invocation
        return this.matches(other) || other.getUsedFormattings().containsAll(this.usedFormattings);
    }

    /**
     * Determines if the {@link #usedFormattings} of {@code this} {@link TextFormattingLanguage} are a superset
     * of the {@code other} {@code TextFormattingLanguage}.
     *
     * @param other the {@code other} {@code TextFormattingLanguage} whose set of {@code usedFormattings} to compare to
     * @return {@code true} if {@code this} {@code TextFormattingLanguage}'s {@code usedFormattings} match or are a
     * superset of the {@code other}'s
     */
    public boolean isSupersetOf(TextFormattingLanguage other) {
        // NPEs are avoided through short-circuit eval with matches() invocation
        return this.matches(other) || this.usedFormattings.containsAll(other.getUsedFormattings());
    }

    /**
     * Generates a hash code based on the contents of {@link #usedFormattings}.
     *
     * @return a hash code that is unique to {@link TextFormattingLanguage}s with the same set of {@code usedFormattings}
     */
    @Override
    public int hashCode() {
        return Objects.hash(usedFormattings);
    }

    /**
     * Determines equality based strictly on the {@link #usedFormattings} of {@code this} {@link TextFormattingLanguage},
     * should the comparison {@code obj} have said property.
     *
     * @param obj the other {@link Object} to compare {@code this} one to
     * @return {@code true} if {@code obj} is a {@code TextFormattingLanguage} and uses the same {@link TextFormatting}s
     */
    @Override
    public boolean equals(Object obj) {
        return this.hashCode() == obj.hashCode();
    }
}