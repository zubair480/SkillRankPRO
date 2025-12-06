package com.resumerank;

import java.util.*;

/**
 * RankingEngine Class - Core ATS Scoring Algorithm
 * 
 * This class implements the intelligent ranking/scoring algorithm that evaluates
 * how well an applicant matches specific job roles. It's the heart of the ATS system,
 * calculating detailed scores across multiple criteria.
 * 
 * SCORING METHODOLOGY:
 * The algorithm uses a weighted scoring system (max 100 points):
 * - Skill Match Score:      0-40 points (40%)  - Required skills found in resume
 * - Keyword Match Score:    0-25 points (25%)  - Job keywords found in resume
 * - Experience Score:       0-20 points (20%)  - Years of professional experience
 * - Formatting Score:       0-10 points (10%)  - Resume presentation quality
 * - Extras Score (BONUS):   0-5 points  (5%)   - Certifications and projects
 * 
 * User-Defined Methods (9 total):
 * 1. calculateSkillScore() - Match required skills
 * 2. calculateKeywordScore() - Match job keywords
 * 3. calculateExperienceScore() - Evaluate years of experience
 * 4. calculateFormattingScore() - Assess resume quality
 * 5. calculateExtrasScore() - Bonus for certs/projects
 * 6. calculateFinalScoreForRole() - Combine all scores for one role
 * 7. scoreApplicantAcrossRoles() - Evaluate against multiple roles
 * 8. getBestRole() - Find highest-scoring role
 * 9. displayRoleRanking() - Format and display results
 * 
 * @author ATS Resume Ranking System
 * @version 2.0
 */
public class RankingEngine {

    // ============ SCORING COMPONENT 1: SKILL MATCHING (0-40 points) ============
    
    /**
     * Calculates the skill matching score (40% of total score)
     * 
     * Algorithm: Counts how many of the REQUIRED job skills are present
     * in the applicant's extracted skills list. Converts to percentage and
     * multiplies by 40 to get the skill score contribution.
     * 
     * Example: If job requires [Java, Spring, SQL, Docker] and applicant has
     * [Java, Spring, Docker], then 3/4 = 75% match = 30 points
     * 
     * @param applicant Applicant whose skills are being evaluated
     * @param role JobRole defining required skills
     * @return skill score between 0.0 and 40.0
     */
    public double calculateSkillScore(Applicant applicant, JobRole role) {
        // Get applicant's extracted skills, default to empty list if null
        List<String> extracted = applicant.getExtractedSkills();
        if (extracted == null) extracted = new ArrayList<>();

        // Count how many required skills the applicant has
        int matched = 0;
        for (String skill : role.getSkills()) {
            for (String s : extracted) {
                // Case-insensitive comparison
                if (s.equalsIgnoreCase(skill)) {
                    matched++;
                    break;  // Count each required skill only once
                }
            }
        }

        // Calculate percentage match
        int total = role.getSkills().size();
        if (total == 0) return 0.0;

        double ratio = (double) matched / total;
        return ratio * 40.0;  // Max 40 points for skills
    }

    // ============ SCORING COMPONENT 2: KEYWORD MATCHING (0-25 points) ============
    
    /**
     * Calculates the keyword matching score (25% of total score)
     * 
     * Algorithm: Searches the resume text for job description keywords.
     * Each keyword match adds 5 points (capped at 25 points maximum).
     * This evaluates domain/industry terminology relevance.
     * 
     * Example: If job keywords [backend, microservices, REST API] and resume
     * contains all three, that's 3 * 5 = 15 points
     * 
     * @param applicant Applicant whose resume is being analyzed
     * @param role JobRole defining relevant keywords
     * @return keyword score between 0.0 and 25.0
     */
    public double calculateKeywordScore(Applicant applicant, JobRole role) {
        // Get applicant's full resume text
        String text = applicant.getResumeText();
        if (text == null) return 0.0;

        // Convert to lowercase for case-insensitive searching
        String lower = text.toLowerCase();
        int matches = 0;

        // Count how many keywords are found in the resume
        for (String kw : role.getKeywords()) {
            if (lower.contains(kw.toLowerCase())) {
                matches++;
            }
        }

        // Calculate score: 5 points per keyword match, max 25 points
        double score = matches * 5.0;
        if (score > 25.0) score = 25.0;  // Cap at 25 points
        return score;
    }

    // ============ SCORING COMPONENT 3: EXPERIENCE (0-20 points) ============
    
    /**
     * Calculates the experience score (20% of total score)
     * 
     * Algorithm: Evaluates total years of professional experience.
     * Each year is worth 4 points, capped at 20 points (5+ years = full score).
     * This reflects that job experience is a key qualification indicator.
     * 
     * Example: 3 years experience = 3 * 4 = 12 points
     *          5+ years experience = 20 points (capped)
     * 
     * @param applicant Applicant whose experience is being evaluated
     * @return experience score between 0.0 and 20.0
     */
    public double calculateExperienceScore(Applicant applicant) {
        // Get total years extracted from resume dates
        int years = applicant.getTotalExperienceYears();
        
        // Calculate score: 4 points per year
        double score = years * 4.0;
        
        // Cap at 20 points (5+ years gets full experience score)
        if (score > 20.0) score = 20.0;
        
        return score;
    }

    // ============ SCORING COMPONENT 4: FORMATTING QUALITY (0-10 points) ============
    
    /**
     * Calculates the formatting/presentation score (10% of total score)
     * 
     * Algorithm: Uses the resume formatting score (0-10 scale) directly.
     * A well-formatted resume shows professionalism and attention to detail.
     * The score has already been assessed separately on a 0-10 scale.
     * 
     * @param applicant Applicant whose resume formatting is being evaluated
     * @return formatting score between 0.0 and 10.0
     */
    public double calculateFormattingScore(Applicant applicant) {
        // Get the formatting score already calculated during resume analysis
        int format = applicant.getFormattingScore();
        
        // Ensure score is within valid range (0-10)
        if (format < 0) format = 0;
        if (format > 10) format = 10;
        
        return format;  // Already on 0-10 scale, no conversion needed
    }

    // ============ SCORING COMPONENT 5: BONUS - EXTRAS (0-5 points) ============
    
    /**
     * Calculates the bonus score for certifications and projects (5% of total score)
     * 
     * Algorithm: Awards bonus points for having:
     * - Certifications: +3 points (indicates continuous learning/validation)
     * - Projects: +2 points (demonstrates practical application of skills)
     * Total capped at 5 points
     * 
     * This incentivizes applicants to include these sections in their resume.
     * 
     * @param applicant Applicant whose extra qualifications are being evaluated
     * @return extras score between 0.0 and 5.0
     */
    public double calculateExtrasScore(Applicant applicant) {
        double score = 0.0;

        // Check if applicant has certifications section
        if (applicant.getCertificationsSection() != null &&
            !applicant.getCertificationsSection().isBlank()) {
            score += 3.0;  // 3 points for having certifications
        }

        // Check if applicant has projects section
        if (applicant.getProjectsSection() != null &&
            !applicant.getProjectsSection().isBlank()) {
            score += 2.0;  // 2 points for having projects
        }

        // Cap at 5 points total
        if (score > 5.0) score = 5.0;
        
        return score;
    }

    // ============ COMPOSITE SCORING ============
    
    /**
     * Calculates the final ATS score for one applicant-role combination
     * 
     * This method combines all 5 scoring components to produce a single
     * comprehensive ATS score for how well an applicant matches a specific role.
     * 
     * Score Breakdown:
     * - Skills (40%) + Keywords (25%) + Experience (20%) + 
     *   Formatting (10%) + Extras (5%) = TOTAL (max 100)
     * 
     * @param applicant Applicant being evaluated
     * @param role JobRole to evaluate against
     * @return Final ATS score between 0.0 and 100.0
     */
    public double calculateFinalScoreForRole(Applicant applicant, JobRole role) {
        // Calculate individual score components
        double skillScore = calculateSkillScore(applicant, role);
        double keywordScore = calculateKeywordScore(applicant, role);
        double expScore = calculateExperienceScore(applicant);
        double formatScore = calculateFormattingScore(applicant);
        double extrasScore = calculateExtrasScore(applicant);

        // Sum all components
        double total = skillScore + keywordScore + expScore + formatScore + extrasScore;
        
        // Ensure total doesn't exceed 100 (safety cap)
        if (total > 100.0) total = 100.0;
        
        return total;
    }

    // ============ MULTI-ROLE EVALUATION ============
    
    /**
     * Scores an applicant against multiple job roles
     * 
     * This method evaluates a single applicant against all available job roles
     * and returns a map of role-to-score pairs, showing how well they fit each role.
     * 
     * @param applicant Applicant being evaluated
     * @param roles List of JobRole objects to evaluate against
     * @return LinkedHashMap of (role name -> ATS score) pairs, ordered by insertion
     */
    public Map<String, Double> scoreApplicantAcrossRoles(Applicant applicant, List<JobRole> roles) {
        // Use LinkedHashMap to preserve insertion order (maintains role order)
        Map<String, Double> scores = new LinkedHashMap<>();

        // Calculate score for each role
        for (JobRole role : roles) {
            double score = calculateFinalScoreForRole(applicant, role);
            scores.put(role.getRoleName(), score);
        }
        
        return scores;
    }

    // ============ RESULT ANALYSIS ============
    
    /**
     * Finds the role with the highest ATS score for an applicant
     * 
     * This method determines the "best fit" role by finding the entry
     * with the maximum score value. Useful for quick recommendation.
     * 
     * @param scores Map of role names to their ATS scores
     * @return Map.Entry representing the best-matching role and its score,
     *         or null if scores map is empty
     */
    public Map.Entry<String, Double> getBestRole(Map<String, Double> scores) {
        Map.Entry<String, Double> best = null;
        
        // Iterate through scores to find maximum
        for (Map.Entry<String, Double> entry : scores.entrySet()) {
            if (best == null || entry.getValue() > best.getValue()) {
                best = entry;
            }
        }
        
        return best;
    }

    /**
     * Displays ATS scores for all roles in a formatted table
     * 
     * This is a utility method for console output, displaying the scoring
     * results in a readable format with role names and their scores.
     * 
     * @param scores Map of role names to their ATS scores
     */
    public void displayRoleRanking(Map<String, Double> scores) {
        System.out.println("\nRole Scores");
        System.out.println("-----------");
        for (Map.Entry<String, Double> entry : scores.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
