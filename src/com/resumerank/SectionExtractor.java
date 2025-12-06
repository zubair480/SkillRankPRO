package com.resumerank;

public class SectionExtractor {

// Extracts text after a header until a blank line or end of text
    public String extractSection(String text, String header) {
        if (text == null) return "";
        String lower = text.toLowerCase();
        String headerLower = header.toLowerCase();

        int start = lower.indexOf(headerLower);
        if (start == -1) {
            return "";
        }

        int contentStart = start + headerLower.length();

        int end = lower.indexOf("\n\n", contentStart);
        if (end == -1) {
            end = text.length();
        }

        return text.substring(contentStart, end).trim();
    }
}
