package com.resumerank;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * ResultFormatter Class - Formats ATS Results in Multiple Formats
 * 
 * This class provides flexible output formatting for ATS evaluation results.
 * Supports multiple output formats including JSON, CSV, and formatted text.
 * 
 * Formats Supported:
 * - JSON: Machine-readable, industry-standard format
 * - CSV: Spreadsheet-friendly format for Excel/reporting
 * - Formatted Text: Human-readable console output with tables
 * 
 * User-Defined Methods (4):
 * 1. exportToJSON() - Exports results as JSON to file
 * 2. exportToCSV() - Exports results as CSV to file
 * 3. formatAsTable() - Formats results as console table
 * 4. formatScoreBreakdown() - Creates detailed score breakdown
 * 
 * @author ATS Resume Ranking System
 * @version 2.0
 */
public class ResultFormatter {

    /**
     * Exports ATS evaluation results to JSON format
     * 
     * Creates a structured JSON file with complete evaluation data:
     * - Applicant information (ID, name, email)
     * - Timestamp of evaluation
     * - Skills extracted from resume
     * - Experience level
     * - Score breakdown by component
     * - Role rankings with scores
     * - Best matching role with recommendation
     * 
     * JSON Structure:
     * {
     *   "applicant": {...},
     *   "evaluation": {...},
     *   "scores": {...},
     *   "recommendation": {...}
     * }
     * 
     * @param applicant The evaluated applicant
     * @param scores Map of role names to ATS scores
     * @param bestRole The best matching role
     * @param filename Output JSON filename
     */
    public void exportToJSON(Applicant applicant, Map<String, Double> scores, String bestRole, String filename) {
        try (FileWriter fw = new FileWriter(filename)) {
            StringBuilder json = new StringBuilder();
            json.append("{\n");
            
            // Applicant information
            json.append("  \"applicant\": {\n");
            json.append("    \"id\": \"").append(escapeJson(applicant.getApplicantId())).append("\",\n");
            json.append("    \"name\": \"").append(escapeJson(applicant.getName())).append("\",\n");
            json.append("    \"email\": \"").append(escapeJson(applicant.getEmail())).append("\"\n");
            json.append("  },\n");
            
            // Evaluation metadata
            json.append("  \"evaluation\": {\n");
            json.append("    \"timestamp\": \"").append(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append("\",\n");
            json.append("    \"experience_years\": ").append(applicant.getTotalExperienceYears()).append(",\n");
            json.append("    \"formatting_score\": ").append(applicant.getFormattingScore()).append("/10,\n");
            json.append("    \"extracted_skills\": ").append(formatJsonArray(applicant.getExtractedSkills())).append("\n");
            json.append("  },\n");
            
            // Score breakdown
            json.append("  \"scores\": {\n");
            int count = 0;
            for (Map.Entry<String, Double> entry : scores.entrySet()) {
                json.append("    \"").append(escapeJson(entry.getKey())).append("\": ").append(String.format("%.2f", entry.getValue()));
                if (count < scores.size() - 1) json.append(",");
                json.append("\n");
                count++;
            }
            json.append("  },\n");
            
            // Recommendation
            json.append("  \"recommendation\": {\n");
            json.append("    \"best_fit_role\": \"").append(escapeJson(bestRole)).append("\",\n");
            json.append("    \"score\": ").append(String.format("%.2f", applicant.getFinalScore())).append(",\n");
            json.append("    \"fit_percentage\": \"").append(String.format("%.1f", applicant.getFinalScore())).append("%\"\n");
            json.append("  }\n");
            
            json.append("}\n");
            
            fw.write(json.toString());
            System.out.println("✓ JSON results exported to: " + filename);
            
        } catch (IOException e) {
            System.out.println("Error exporting JSON: " + e.getMessage());
        }
    }

    /**
     * Exports ATS evaluation results to CSV format
     * 
     * Creates a CSV file suitable for importing into Excel or other spreadsheet tools.
     * Includes all applicant information and scores in a tabular format.
     * 
     * CSV Structure (one row per role):
     * Applicant ID, Name, Email, Role, Score, Best Match
     * 
     * @param applicant The evaluated applicant
     * @param scores Map of role names to ATS scores
     * @param bestRole The best matching role
     * @param filename Output CSV filename
     */
    public void exportToCSV(Applicant applicant, Map<String, Double> scores, String bestRole, String filename) {
        try (FileWriter fw = new FileWriter(filename, true)) {
            // Header row (only if file is empty)
            File f = new File(filename);
            if (f.length() == 0) {
                fw.append("Applicant ID,Name,Email,Role,Score,Best Match,Timestamp\n");
            }
            
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            
            // Data rows (one per role)
            for (Map.Entry<String, Double> entry : scores.entrySet()) {
                String isBest = entry.getKey().equals(bestRole) ? "YES" : "NO";
                fw.append(escapeCsv(applicant.getApplicantId())).append(",");
                fw.append(escapeCsv(applicant.getName())).append(",");
                fw.append(escapeCsv(applicant.getEmail())).append(",");
                fw.append(escapeCsv(entry.getKey())).append(",");
                fw.append(String.format("%.2f", entry.getValue())).append(",");
                fw.append(isBest).append(",");
                fw.append(timestamp).append("\n");
            }
            
            System.out.println("✓ CSV results exported to: " + filename);
            
        } catch (IOException e) {
            System.out.println("Error exporting CSV: " + e.getMessage());
        }
    }

    /**
     * Formats ATS results as a console table with proper alignment and spacing
     * 
     * Creates a professional-looking table output with:
     * - Applicant information header
     * - Formatted score table with columns
     * - Best match highlighted
     * - Summary statistics
     * 
     * @param applicant The evaluated applicant
     * @param scores Map of role names to ATS scores
     * @param bestRole The best matching role
     */
    public void formatAsTable(Applicant applicant, Map<String, Double> scores, String bestRole) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                   ATS EVALUATION RESULTS");
        System.out.println("=".repeat(70));
        
        // Applicant information
        System.out.println("\nAPPLICANT INFORMATION:");
        System.out.println("  ID:              " + applicant.getApplicantId());
        System.out.println("  Name:            " + applicant.getName());
        System.out.println("  Email:           " + applicant.getEmail());
        System.out.println("  Experience:      " + applicant.getTotalExperienceYears() + " years");
        System.out.println("  Skills:          " + applicant.getExtractedSkills().size() + " identified");
        System.out.println("  Formatting:      " + applicant.getFormattingScore() + "/10");
        
        // Score table
        System.out.println("\n" + "-".repeat(70));
        System.out.println(String.format("%-35s %-15s %s", "JOB ROLE", "SCORE", "MATCH"));
        System.out.println("-".repeat(70));
        
        for (Map.Entry<String, Double> entry : scores.entrySet()) {
            String role = entry.getKey();
            double score = entry.getValue();
            String marker = role.equals(bestRole) ? "⭐ BEST FIT" : "";
            System.out.println(String.format("%-35s %10.2f/100  %s", role, score, marker));
        }
        
        System.out.println("-".repeat(70));
        
        // Recommendation
        System.out.println("\nRECOMMENDATION:");
        System.out.println("  Best Fit Role:   " + bestRole);
        System.out.println("  Final Score:     " + String.format("%.2f", applicant.getFinalScore()) + "/100");
        System.out.println("  Fit Rating:      " + getRatingLabel(applicant.getFinalScore()));
        
        System.out.println("\n" + "=".repeat(70));
    }

    /**
     * Formats detailed score breakdown for an applicant
     * 
     * Shows how the final score is calculated with all components:
     * - Skill matching score
     * - Keyword matching score
     * - Experience score
     * - Formatting score
     * - Bonus points
     * 
     * @param applicant The evaluated applicant
     * @param role The job role being evaluated
     * @param engine RankingEngine instance for score calculation
     */
    public void formatScoreBreakdown(Applicant applicant, JobRole role, RankingEngine engine) {
        System.out.println("\nSCORE BREAKDOWN FOR: " + role.getRoleName());
        System.out.println("-".repeat(50));
        
        double skillScore = engine.calculateSkillScore(applicant, role);
        double keywordScore = engine.calculateKeywordScore(applicant, role);
        double expScore = engine.calculateExperienceScore(applicant);
        double formatScore = engine.calculateFormattingScore(applicant);
        double extrasScore = engine.calculateExtrasScore(applicant);
        double total = skillScore + keywordScore + expScore + formatScore + extrasScore;
        
        System.out.println(String.format("Skill Matching:      %6.2f / 40.0", skillScore));
        System.out.println(String.format("Keyword Matching:    %6.2f / 25.0", keywordScore));
        System.out.println(String.format("Experience:          %6.2f / 20.0", expScore));
        System.out.println(String.format("Formatting Quality:  %6.2f / 10.0", formatScore));
        System.out.println(String.format("Certifications/Projects: %6.2f /  5.0", extrasScore));
        System.out.println("-".repeat(50));
        System.out.println(String.format("TOTAL SCORE:         %6.2f / 100.0", Math.min(total, 100.0)));
    }

    /**
     * Converts a score to a descriptive rating label
     * 
     * @param score The ATS score (0-100)
     * @return Rating label (Excellent, Good, Average, etc.)
     */
    private String getRatingLabel(double score) {
        if (score >= 85) return "Excellent ⭐⭐⭐⭐⭐";
        if (score >= 70) return "Good ⭐⭐⭐⭐";
        if (score >= 55) return "Average ⭐⭐⭐";
        if (score >= 40) return "Below Average ⭐⭐";
        return "Poor ⭐";
    }

    /**
     * Escapes special characters in JSON strings
     * @param str Input string
     * @return Escaped string safe for JSON
     */
    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    /**
     * Escapes special characters in CSV values
     * @param str Input string
     * @return Escaped string safe for CSV
     */
    private String escapeCsv(String str) {
        if (str == null) return "";
        if (str.contains(",") || str.contains("\"") || str.contains("\n")) {
            return "\"" + str.replace("\"", "\"\"") + "\"";
        }
        return str;
    }

    /**
     * Formats a list as JSON array
     * @param list Input list
     * @return JSON array string
     */
    private String formatJsonArray(List<?> list) {
        if (list == null || list.isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            sb.append("\"").append(escapeJson(list.get(i).toString())).append("\"");
            if (i < list.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
