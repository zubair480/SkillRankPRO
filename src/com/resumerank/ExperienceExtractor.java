package com.resumerank;

import java.time.LocalDate;
import java.time.Year;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExperienceExtractor {

    public int extractYears(String text) {
        if (text == null) return 0;

        // First, try to extract explicit "X years" mentions
        int explicitYears = extractExplicitYears(text);
        if (explicitYears > 0) {
            return explicitYears;
        }

        // If no explicit mention, calculate from date ranges (e.g., "2019 - 2022")
        int calculatedYears = calculateYearsFromDateRanges(text);
        return calculatedYears;
    }

    /**
     * Extract years when explicitly stated (e.g., "5 years", "10+ years")
     */
    private int extractExplicitYears(String text) {
        Pattern p = Pattern.compile("(\\d+)\\+?\\s*(year|years|yrs)");
        Matcher m = p.matcher(text.toLowerCase());

        int maxYears = 0;
        while (m.find()) {
            try {
                int years = Integer.parseInt(m.group(1));
                if (years > maxYears) {
                    maxYears = years;
                }
            } catch (NumberFormatException e) {
                // ignore bad numbers
            }
        }
        return maxYears;
    }

    /**
     * Calculate total years from date ranges found in text
     * Supports formats like: "2019 - 2022", "2019 2022", "Jan 2019 - Dec 2022", "Present", "Current"
     */
    private int calculateYearsFromDateRanges(String text) {
        if (text == null) return 0;

        String lower = text.toLowerCase();
        int totalYears = 0;
        int currentYear = Year.now().getValue();

        // Pattern 1: "YYYY YYYY" or "YYYY - YYYY" or "YYYY – YYYY"
        // This will match: "2022 Present", "2019 2022", "2022-Present", etc.
        Pattern yearRangePattern = Pattern.compile("\\b(\\d{4})\\s*(?:-|to|–)?\\s*(\\d{4}|present|current)\\b");
        Matcher yearMatcher = yearRangePattern.matcher(lower);

        while (yearMatcher.find()) {
            try {
                int startYear = Integer.parseInt(yearMatcher.group(1));
                String endStr = yearMatcher.group(2).toLowerCase();
                int endYear;
                
                if (endStr.equals("present") || endStr.equals("current")) {
                    endYear = currentYear;
                } else {
                    endYear = Integer.parseInt(endStr);
                }

                // Validate that start year is reasonable (after 1990) and before/equal to current year
                if (startYear >= 1990 && startYear <= currentYear && endYear >= startYear && endYear <= currentYear) {
                    int yearsInRole = endYear - startYear;
                    // Only count if it's at least 0 years (reasonable experience duration)
                    if (yearsInRole >= 0) {
                        totalYears += yearsInRole;
                    }
                }
            } catch (NumberFormatException e) {
                // ignore bad numbers
            }
        }

        // Pattern 2: Month-year ranges: "Jan 2019 - Dec 2022" or "January 2019 to December 2022"
        Pattern monthYearPattern = Pattern.compile("\\b(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)[a-z]*\\.?\\s+(\\d{4})\\s*(?:-|to|–)\\s*(?:(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)[a-z]*\\.?\\s+)?(\\d{4}|present|current)\\b");
        Matcher monthMatcher = monthYearPattern.matcher(lower);

        while (monthMatcher.find()) {
            try {
                int startYear = Integer.parseInt(monthMatcher.group(2));
                String endStr = monthMatcher.group(4).toLowerCase();
                int endYear;
                
                if (endStr.equals("present") || endStr.equals("current")) {
                    endYear = currentYear;
                } else {
                    endYear = Integer.parseInt(endStr);
                }

                // Validate years
                if (startYear >= 1990 && startYear <= currentYear && endYear >= startYear && endYear <= currentYear) {
                    int yearsInRole = endYear - startYear;
                    if (yearsInRole >= 0) {
                        totalYears += yearsInRole;
                    }
                }
            } catch (NumberFormatException e) {
                // ignore bad numbers
            }
        }

        return totalYears;
    }
}
