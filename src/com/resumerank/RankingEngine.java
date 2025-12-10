package com.resumerank;

import java.util.*;

public class RankingEngine {

    /**
     * Calculates skill matching score (40% of total)
     * Counts required skills found in applicant's resume
     */
    public double calculateSkillScore(Applicant applicant, JobRole role) {
        List<String> extracted = applicant.getExtractedSkills();
        if (extracted == null) extracted = new ArrayList<>();

        int matched = 0;
        for (String skill : role.getSkills()) {
            for (String s : extracted) {
                if (s.equalsIgnoreCase(skill)) {
                    matched++;
                    break;
                }
            }
        }

        int total = role.getSkills().size();
        if (total == 0) return 0.0;

        double ratio = (double) matched / total;
        return ratio * 40.0;
    }

    /**
     * Calculates keyword matching score (25% of total)
     * Searches resume for job description keywords
     */
    public double calculateKeywordScore(Applicant applicant, JobRole role) {
        String text = applicant.getResumeText();
        if (text == null) return 0.0;

        String lower = text.toLowerCase();
        int matches = 0;

        for (String kw : role.getKeywords()) {
            if (lower.contains(kw.toLowerCase())) {
                matches++;
            }
        }

        double score = matches * 5.0;
        if (score > 25.0) score = 25.0;
        return score;
    }

    /**
     * Calculates experience score (20% of total)
     * 4 points per year, capped at 20
     */
    public double calculateExperienceScore(Applicant applicant) {
        int years = applicant.getTotalExperienceYears();
        double score = years * 4.0;
        if (score > 20.0) score = 20.0;
        return score;
    }

    /**
     * Calculates formatting score (10% of total)
     * Resume presentation quality (0-10 scale)
     */
    public double calculateFormattingScore(Applicant applicant) {
        int format = applicant.getFormattingScore();
        if (format < 0) format = 0;
        if (format > 10) format = 10;
        return format;
    }

    /**
     * Calculates bonus score (5% of total)
     * Certifications: +3, Projects: +2
     */
    public double calculateExtrasScore(Applicant applicant) {
        double score = 0.0;

        if (applicant.getCertificationsSection() != null &&
            !applicant.getCertificationsSection().isBlank()) {
            score += 3.0;
        }

        if (applicant.getProjectsSection() != null &&
            !applicant.getProjectsSection().isBlank()) {
            score += 2.0;
        }

        if (score > 5.0) score = 5.0;
        return score;
    }

    /**
     * Calculates final ATS score (all 5 components combined)
     */
    public double calculateFinalScoreForRole(Applicant applicant, JobRole role) {
        double skillScore = calculateSkillScore(applicant, role);
        double keywordScore = calculateKeywordScore(applicant, role);
        double expScore = calculateExperienceScore(applicant);
        double formatScore = calculateFormattingScore(applicant);
        double extrasScore = calculateExtrasScore(applicant);

        double total = skillScore + keywordScore + expScore + formatScore + extrasScore;
        if (total > 100.0) total = 100.0;
        return total;
    }

    /**
     * Scores applicant against multiple job roles
     */
    public Map<String, Double> scoreApplicantAcrossRoles(Applicant applicant, List<JobRole> roles) {
        Map<String, Double> scores = new LinkedHashMap<>();
        for (JobRole role : roles) {
            double score = calculateFinalScoreForRole(applicant, role);
            scores.put(role.getRoleName(), score);
        }
        return scores;
    }

    /**
     * Finds the best matching role (highest score)
     */
    public Map.Entry<String, Double> getBestRole(Map<String, Double> scores) {
        Map.Entry<String, Double> best = null;
        for (Map.Entry<String, Double> entry : scores.entrySet()) {
            if (best == null || entry.getValue() > best.getValue()) {
                best = entry;
            }
        }
        return best;
    }

    /**
     * Displays role rankings in console
     */
    public void displayRoleRanking(Map<String, Double> scores) {
        System.out.println("\nRole Scores");
        System.out.println("-----------");
        for (Map.Entry<String, Double> entry : scores.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
