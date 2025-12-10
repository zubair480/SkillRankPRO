package com.resumerank;

import java.time.LocalDate;
import java.time.Year;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExperienceExtractor {

    /** Extracts total years of experience from resume text */
    public int extractYears(String text) {
        if (text == null) return 0;
        int explicitYears = extractExplicitYears(text);
        if (explicitYears > 0) {
            return explicitYears;
        }
        return calculateYearsFromDateRanges(text);
    }

    /** Extract years when explicitly stated (e.g., "5 years", "10+ years") */
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
                // ignore
            }
        }
        return maxYears;
    }

    /** Calculate total years from date ranges (e.g., "2019 - 2022", "Jan 2019 - Present") */
    private int calculateYearsFromDateRanges(String text) {
        if (text == null) return 0;
        String lower = text.toLowerCase();
        int totalYears = 0;
        int currentYear = Year.now().getValue();

        Pattern yearRangePattern = Pattern.compile("\\b(\\d{4})\\s*(?:-|to|–)?\\s*(\\d{4}|present|current)\\b");
        Matcher yearMatcher = yearRangePattern.matcher(lower);
        while (yearMatcher.find()) {
            try {
                int startYear = Integer.parseInt(yearMatcher.group(1));
                String endStr = yearMatcher.group(2).toLowerCase();
                int endYear = (endStr.equals("present") || endStr.equals("current")) ? currentYear : Integer.parseInt(endStr);
                
                if (startYear >= 1990 && startYear <= currentYear && endYear >= startYear && endYear <= currentYear) {
                    int yearsInRole = endYear - startYear;
                    if (yearsInRole >= 0) {
                        totalYears += yearsInRole;
                    }
                }
            } catch (NumberFormatException e) {
                // ignore
            }
        }

        Pattern monthYearPattern = Pattern.compile("\\b(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)[a-z]*\\.?\\s+(\\d{4})\\s*(?:-|to|–)\\s*(?:(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)[a-z]*\\.?\\s+)?(\\d{4}|present|current)\\b");
        Matcher monthMatcher = monthYearPattern.matcher(lower);
        while (monthMatcher.find()) {
            try {
                int startYear = Integer.parseInt(monthMatcher.group(2));
                String endStr = monthMatcher.group(4).toLowerCase();
                int endYear = (endStr.equals("present") || endStr.equals("current")) ? currentYear : Integer.parseInt(endStr);
                
                if (startYear >= 1990 && startYear <= currentYear && endYear >= startYear && endYear <= currentYear) {
                    int yearsInRole = endYear - startYear;
                    if (yearsInRole >= 0) {
                        totalYears += yearsInRole;
                    }
                }
            } catch (NumberFormatException e) {
                // ignore
            }
        }
        return totalYears;
    }
}
