package com.justinquinnb.onefeed.customization.textstyle;

//TODO

import java.util.Objects;

/**
 *
 */
public abstract class TextFormatting {
    /**
     *
     */
    private String label;

    /**
     *
     * @param label
     */
    public TextFormatting(String label) {
        this.label = label.toUpperCase().strip();
    }

    /**
     *
     * @return
     */
    public String getLabel() {
        return this.label;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getClass(), this.label);
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        return this.hashCode() == o.hashCode();
    }
}