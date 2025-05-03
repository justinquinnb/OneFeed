package com.justinquinnb.onefeed.customization.textstyle;

import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormatting;

/**
 * A {@code String} of text with its formatting specified by a tree of parent {@code FormattingMarkedText} nodes with
 * their component, formatted (or unformatted) substrings as child {@code FormattingTree}s.
 */
public class FormattingTree extends FormattedText {
    /**
     * The "base" {@code String} of formatted (or unformatted) text from which all child ({@link #children})
     * {@link FormattingMarkedText} substrings are derived from.
     */
    private final FormattingMarkedText root;

    /**
     * The substrings of the {@link #root} that contain their own unique {@link TextFormatting}s and (possibly)
     * formatted substrings.
     */
    private final FormattingTree[] children;

    /**
     * Constructs a {@code FormattingTree} with root {@code root} and child {@code FormattingTree}s {@code children}.
     *
     * @param root the "base" {@code String} of formatted (or unformatted) text from which all child
     *             ({@link #children}) {@link FormattingMarkedText} substrings are derived from
     * @param children the substrings of the {@link #root} that contain their own unique {@link TextFormatting}s and
     *                 (possibly) formatted substrings
     */
    public FormattingTree(FormattingMarkedText root, FormattingTree[] children) {
        super(root.getText());
        this.root = root;
        this.children = children;
    }

    /**
     * Gets the root {@code FormattingMarkedText} of {@code this} tree.
     *
     * @return the root {@code FormattingMarkedText} of {@code this} {@code FormattingTree}
     */
    public FormattingMarkedText getRoot() {
        return this.root;
    }

    /**
     * Gets the child {@code FormattingTree}s of {@code this} tree.
     *
     * @return the child {@code FormattingTree}s of {@code this} {@code FormattingTree}
     */
    public FormattingTree[] getChildren() {
        return this.children;
    }
}