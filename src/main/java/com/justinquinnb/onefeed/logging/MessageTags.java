package com.justinquinnb.onefeed.logging;

public enum MessageTags {
    INFO,
    WARN,
    FAIL,
    SUCCESS;

    public String toConsoleString() {
        String asString = "";

        switch(this) {
            case INFO:
                asString = "INFO";
                break;
            case WARN:
                asString = "\u001B[33mWARN\u001B[0m";
                break;
            case FAIL:
                asString = "\u001B[31mWARN\u001B[0m";
                break;
            case SUCCESS:
                asString = "\u001B[32mSUCCESS\u001B[0m";
                break;
        }

        return asString;
    }

    public String toFileString() {
        String asString = "";

        switch(this) {
            case INFO:
                asString = "INFO";
                break;
            case WARN:
                asString = "WARN";
                break;
            case FAIL:
                asString = "WARN";
                break;
            case SUCCESS:
                asString = "SUCCESS";
                break;
        }

        return asString;
    }
}
