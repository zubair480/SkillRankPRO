package com.resumerank;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/** Formats ATS results in multiple output formats (JSON, CSV, table) */
public class ResultFormatter {

    /** Exports results to JSON format */
    public void exportToJSON(Applicant applicant, Map<String, Double> scores, String bestRole, String filename) {
        try (FileWriter fw = new FileWriter(filename)) {
            StringBuilder json = new StringBuilder();
            json.append("{\n");
            json.append("  \"applicant\": {\n");
            json.append("    \"id\": \"").append(escapeJson(applicant.getApplicantId())).append("\",\n");
            json.append("    \"name\": \"").append(escapeJson(applicant.getName())).append("\",\n");
            json.append("    \"email\": \"").append(escapeJson(applicant.getEmail())).append("\"\n");
            json.append("  },\n");
            json.append("  \"evaluation\": {\n");
            json.append("    \"timestamp\": \"").append(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append("\",\n");
            json.append("    \"experience_years\": ").append(applicant.getTotalExperienceYears()).append(",\n");
            json.append("    \"formatting_score\": ").append(applicant.getFormattingScore()).append("/10,\n");
            json.append("    \"extracted_skills\": ").append(formatJsonArray(applicant.getExtractedSkills())).append("\n");
            json.append("  },\n");
            json.append("  \"scores\": {\n");
            int count = 0;
            for (Map.Entry<String, Double> entry : scores.entrySet()) {
                json.append("    \"").append(escapeJson(entry.getKey())).append("\": ").append(String.format("%.2f", entry.getValue()));
                if (count < scores.size() - 1) json.append(",");
                json.append("\n");
                count++;
            }
            json.append("  },\n");
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

    /** Exports results to CSV format */
    public void exportToCSV(Applicant applicant, Map<String, Double> scores, String bestRole, String filename) {
        try (FileWriter fw = new FileWriter(filename, true)) {
            File f = new File(filename);
            if (f.length() == 0) {
                fw.append("Applicant ID,Name,Email,Role,Score,Best Match,Timestamp\n");
            }
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
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

    /** Formats results as console table */
    public void formatAsTable(Applicant applicant, Map<String, Double> scores, String bestRole) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                   ATS EVALUATION RESULTS");
        System.out.println("=".repeat(70));
        System.out.println("\nAPPLICANT INFORMATION:");
        System.out.println("  ID:              " + applicant.getApplicantId());
        System.out.println("  Name:            " + applicant.getName());
        System.out.println("  Email:           " + applicant.getEmail());
        System.out.println("  Experience:      " + applicant.getTotalExperienceYears() + " years");
        System.out.println("  Skills:          " + applicant.getExtractedSkills().size() + " identified");
        System.out.println("  Formatting:      " + applicant.getFormattingScore() + "/10");
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
        System.out.println("\nRECOMMENDATION:");
        System.out.println("  Best Fit Role:   " + bestRole);
        System.out.println("  Final Score:     " + String.format("%.2f", applicant.getFinalScore()) + "/100");
        System.out.println("  Fit Rating:      " + getRatingLabel(applicant.getFinalScore()));
        System.out.println("\n" + "=".repeat(70));
    }

    /** Formats detailed score breakdown */
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

    /** Converts score to rating label */
    private String getRatingLabel(double score) {
        if (score >= 85) return "Excellent ⭐⭐⭐⭐⭐";
        if (score >= 70) return "Good ⭐⭐⭐⭐";
        if (score >= 55) return "Average ⭐⭐⭐";
        if (score >= 40) return "Below Average ⭐⭐";
        return "Poor ⭐";
    }

    /** Escapes special characters in JSON strings */
    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    /** Escapes special characters in CSV values */
    private String escapeCsv(String str) {
        if (str == null) return "";
        if (str.contains(",") || str.contains("\"") || str.contains("\n")) {
            return "\"" + str.replace("\"", "\"\"") + "\"";
        }
        return str;
    }

    /** Formats list as JSON array */
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
