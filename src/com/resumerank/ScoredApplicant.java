package com.resumerank;

/** Extends Applicant with detailed scoring breakdown metrics */
public class ScoredApplicant extends Applicant {

    private int keywordMatches;
    private String matchSummary;
    private double skillScore;
    private double keywordScore;
    private double experienceScore;
    private double formattingScorePart;
    private double extrasScore;

    public ScoredApplicant() {
        super();
    }

    /** Converts an Applicant to a ScoredApplicant */
    public ScoredApplicant(Applicant a) {
        super(a.getApplicantId(), a.getName(), a.getEmail(), a.getResumeText());
        this.setExtractedSkills(a.getExtractedSkills());
        this.setEducationSection(a.getEducationSection());
        this.setExperienceSection(a.getExperienceSection());
        this.setProjectsSection(a.getProjectsSection());
        this.setCertificationsSection(a.getCertificationsSection());
        this.setTotalExperienceYears(a.getTotalExperienceYears());
        this.setFormattingScore(a.getFormattingScore());
    }

    public int getKeywordMatches() { return keywordMatches; }
    public void setKeywordMatches(int keywordMatches) { this.keywordMatches = keywordMatches; }

    public String getMatchSummary() { return matchSummary; }
    public void setMatchSummary(String matchSummary) { this.matchSummary = matchSummary; }

    public double getSkillScore() { return skillScore; }
    public void setSkillScore(double skillScore) { this.skillScore = skillScore; }

    public double getKeywordScore() { return keywordScore; }
    public void setKeywordScore(double keywordScore) { this.keywordScore = keywordScore; }

    public double getExperienceScore() { return experienceScore; }
    public void setExperienceScore(double experienceScore) { this.experienceScore = experienceScore; }

    public double getFormattingScorePart() { return formattingScorePart; }
    public void setFormattingScorePart(double formattingScorePart) { this.formattingScorePart = formattingScorePart; }

    public double getExtrasScore() { return extrasScore; }
    public void setExtrasScore(double extrasScore) { this.extrasScore = extrasScore; }
}
