package com.resumerank;

import java.util.List;

public class Applicant {
    private String applicantId;
    private String name;
    private String email;
    private String resumeText;
    private double finalScore;
    private List<String> extractedSkills;
    private String educationSection;
    private String experienceSection;
    private String projectsSection;
    private String certificationsSection;
    private int totalExperienceYears;
    private int formattingScore;

    public Applicant() {}

    public Applicant(String applicantId, String name, String email, String resumeText) {
        this.applicantId = applicantId;
        this.name = name;
        this.email = email;
        this.resumeText = resumeText;
    }

    public String getApplicantId() { return applicantId; }
    public void setApplicantId(String applicantId) { this.applicantId = applicantId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getResumeText() { return resumeText; }
    public void setResumeText(String resumeText) { this.resumeText = resumeText; }

    public double getFinalScore() { return finalScore; }
    public void setFinalScore(double finalScore) { this.finalScore = finalScore; }

    public List<String> getExtractedSkills() { return extractedSkills; }
    public void setExtractedSkills(List<String> extractedSkills) { this.extractedSkills = extractedSkills; }

    public String getEducationSection() { return educationSection; }
    public void setEducationSection(String educationSection) { this.educationSection = educationSection; }

    public String getExperienceSection() { return experienceSection; }
    public void setExperienceSection(String experienceSection) { this.experienceSection = experienceSection; }

    public String getProjectsSection() { return projectsSection; }
    public void setProjectsSection(String projectsSection) { this.projectsSection = projectsSection; }

    public String getCertificationsSection() { return certificationsSection; }
    public void setCertificationsSection(String certificationsSection) { this.certificationsSection = certificationsSection; }

    public int getTotalExperienceYears() { return totalExperienceYears; }
    public void setTotalExperienceYears(int totalExperienceYears) { this.totalExperienceYears = totalExperienceYears; }

    public int getFormattingScore() { return formattingScore; }
    public void setFormattingScore(int formattingScore) { this.formattingScore = formattingScore; }
}
