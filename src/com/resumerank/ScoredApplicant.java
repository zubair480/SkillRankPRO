package com.resumerank;

/**
 * ScoredApplicant Class - Applicant with Detailed Scoring Information
 * 
 * INHERITANCE IMPLEMENTATION: This class extends the Applicant class,
 * demonstrating object-oriented inheritance principles. ScoredApplicant
 * builds upon the Applicant foundation by adding detailed scoring metrics.
 * 
 * This class represents an Applicant after they have been evaluated against
 * a specific job role. It extends Applicant and adds scoring breakdown details.
 * 
 * Key Features:
 * - Inherits all 12 attributes from parent Applicant class
 * - Adds 7 specialized scoring attributes
 * - Provides conversion constructor from Applicant to ScoredApplicant
 * 
 * Data Points Added (7 new attributes):
 * 1. keywordMatches: Number of job description keywords found
 * 2. matchSummary: Text summary of the match quality
 * 3. skillScore: Score for matching required skills (0-40)
 * 4. keywordScore: Score for matching keywords (0-25)
 * 5. experienceScore: Score based on years of experience (0-20)
 * 6. formattingScorePart: Contribution of resume formatting (0-10)
 * 7. extrasScore: Score for certifications/projects (0-5)
 * 
 * Total Score = skillScore + keywordScore + experienceScore + 
 *               formattingScorePart + extrasScore (out of 100)
 * 
 * @author ATS Resume Ranking System
 * @version 2.0
 */
public class ScoredApplicant extends Applicant {

    // ============ SCORING BREAKDOWN ATTRIBUTES ============
    
    /** Number of job description keywords matched in the resume */
    private int keywordMatches;
    
    /** Text summary describing the match quality and fit */
    private String matchSummary;

    /** Score for matching required skills (0-40 points) */
    private double skillScore;
    
    /** Score for matching job description keywords (0-25 points) */
    private double keywordScore;
    
    /** Score based on total years of experience (0-20 points) */
    private double experienceScore;
    
    /** Score component from resume formatting quality (0-10 points) */
    private double formattingScorePart;
    
    /** Score for certifications and projects (0-5 points, BONUS) */
    private double extrasScore;

    /**
     * Default Constructor
     * Creates an empty ScoredApplicant by calling parent default constructor
     */
    public ScoredApplicant() {
        super();
    }

    /**
     * Conversion Constructor
     * Creates a ScoredApplicant from an existing Applicant object
     * This constructor copies all data from the parent Applicant
     * 
     * @param a Applicant object to convert into a ScoredApplicant
     */
    public ScoredApplicant(Applicant a) {
        // Call parent constructor with basic applicant data
        super(a.getApplicantId(), a.getName(), a.getEmail(), a.getResumeText());
        
        // Copy all extended fields from parent Applicant
        this.setExtractedSkills(a.getExtractedSkills());
        this.setEducationSection(a.getEducationSection());
        this.setExperienceSection(a.getExperienceSection());
        this.setProjectsSection(a.getProjectsSection());
        this.setCertificationsSection(a.getCertificationsSection());
        this.setTotalExperienceYears(a.getTotalExperienceYears());
        this.setFormattingScore(a.getFormattingScore());
    }

    // ============ GETTERS AND SETTERS FOR SCORING METRICS ============

    /**
     * Gets the number of job keywords matched in resume
     * @return count of matching keywords
     */
    public int getKeywordMatches() { return keywordMatches; }
    
    /**
     * Sets the number of job keywords matched in resume
     * @param keywordMatches number of matches to record
     */
    public void setKeywordMatches(int keywordMatches) { this.keywordMatches = keywordMatches; }

    /**
     * Gets the match summary describing applicant-to-role fit
     * @return text description of match quality
     */
    public String getMatchSummary() { return matchSummary; }
    
    /**
     * Sets the match summary describing applicant-to-role fit
     * @param matchSummary description to store
     */
    public void setMatchSummary(String matchSummary) { this.matchSummary = matchSummary; }

    /**
     * Gets the skill matching score (0-40 points)
     * Represents how many required skills the applicant possesses
     * @return skill score contribution
     */
    public double getSkillScore() { return skillScore; }
    
    /**
     * Sets the skill matching score (0-40 points)
     * @param skillScore score to assign
     */
    public void setSkillScore(double skillScore) { this.skillScore = skillScore; }

    /**
     * Gets the keyword matching score (0-25 points)
     * Represents how many job description keywords appear in resume
     * @return keyword score contribution
     */
    public double getKeywordScore() { return keywordScore; }
    
    /**
     * Sets the keyword matching score (0-25 points)
     * @param keywordScore score to assign
     */
    public void setKeywordScore(double keywordScore) { this.keywordScore = keywordScore; }

    /**
     * Gets the experience score (0-20 points)
     * Weighted by total years of professional experience
     * @return experience score contribution
     */
    public double getExperienceScore() { return experienceScore; }
    
    /**
     * Sets the experience score (0-20 points)
     * @param experienceScore score to assign
     */
    public void setExperienceScore(double experienceScore) { this.experienceScore = experienceScore; }

    /**
     * Gets the formatting score contribution (0-10 points)
     * Reflects resume presentation quality and professionalism
     * @return formatting score contribution
     */
    public double getFormattingScorePart() { return formattingScorePart; }
    
    /**
     * Sets the formatting score contribution (0-10 points)
     * @param formattingScorePart score to assign
     */
    public void setFormattingScorePart(double formattingScorePart) { this.formattingScorePart = formattingScorePart; }

    /**
     * Gets the extras score (0-5 BONUS points)
     * Awards points for certifications and significant projects
     * @return extras score contribution
     */
    public double getExtrasScore() { return extrasScore; }
    
    /**
     * Sets the extras score (0-5 BONUS points)
     * @param extrasScore score to assign
     */
    public void setExtrasScore(double extrasScore) { this.extrasScore = extrasScore; }
}
