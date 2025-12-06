package com.resumerank;

public class FormattingScoreCalculator {

    // Returns 0 to 10
    public int calculate(String text) {
        if (text == null) return 0;

        int score = 0;
        String lower = text.toLowerCase();

        if (lower.contains("summary")) score += 2;
        if (lower.contains("skills")) score += 2;
        if (lower.contains("experience")) score += 2;
        if (lower.contains("education")) score += 2;
        if (lower.contains("projects")) score += 2;

        if (score > 10) score = 10;
        return score;
    }
}
