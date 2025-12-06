package com.resumerank;

import java.util.List;

/**
 * Applicant Class - Core Data Model
 * 
 * This class represents a job applicant and stores all relevant information
 * extracted from their resume. It serves as the primary data container for
 * the ATS Resume Ranking System.
 * 
 * Data Points (12 attributes):
 * - applicantId, name, email: Applicant contact information
 * - resumeText: Full resume content as text
 * - extractedSkills: Technical skills parsed from resume
 * - educationSection, experienceSection, projectsSection, certificationsSection: Resume sections
 * - totalExperienceYears: Years of professional experience
 * - formattingScore: Resume formatting quality score (0-10)
 * - finalScore: ATS ranking score after evaluation
 * 
 * @author ATS Resume Ranking System
 * @version 2.0
 */
public class Applicant {

    // ============ BASIC APPLICANT INFORMATION ============
    /** Unique identifier for the applicant */
    private String applicantId;
    
    /** Full name of the applicant */
    private String name;
    
    /** Email address of the applicant */
    private String email;
    
    /** Full resume content as extracted text from PDF */
    private String resumeText;
    
    /** Final ATS ranking score (0-100) after all evaluations */
    private double finalScore;

    // ============ EXTRACTED RESUME INFORMATION ============
    /** List of technical skills extracted from resume */
    private List<String> extractedSkills;
    
    /** Education section text from resume */
    private String educationSection;
    
    /** Professional experience section text from resume */
    private String experienceSection;
    
    /** Projects section text from resume */
    private String projectsSection;
    
    /** Certifications section text from resume */
    private String certificationsSection;
    
    /** Total years of professional experience calculated from resume dates */
    private int totalExperienceYears;
    
    /** Resume formatting quality score on a scale of 0-10 */
    private int formattingScore;

    /**
     * Default Constructor
     * Initializes an empty Applicant object with all fields set to null/0
     */
    public Applicant() {}

    /**
     * Parameterized Constructor
     * Creates an Applicant with basic information
     * 
     * @param applicantId Unique identifier for the applicant
     * @param name Full name of the applicant
     * @param email Email address of the applicant
     * @param resumeText Full resume content as text
     */
    public Applicant(String applicantId, String name, String email, String resumeText) {
        this.applicantId = applicantId;
        this.name = name;
        this.email = email;
        this.resumeText = resumeText;
    }

    // ============ GETTERS AND SETTERS ============

    /**
     * Gets the applicant ID
     * @return unique applicant identifier
     */
    public String getApplicantId() { return applicantId; }
    
    /**
     * Sets the applicant ID
     * @param applicantId unique identifier to assign
     */
    public void setApplicantId(String applicantId) { this.applicantId = applicantId; }

    /**
     * Gets the applicant's full name
     * @return applicant name
     */
    public String getName() { return name; }
    
    /**
     * Sets the applicant's full name
     * @param name applicant name to assign
     */
    public void setName(String name) { this.name = name; }

    /**
     * Gets the applicant's email address
     * @return email address
     */
    public String getEmail() { return email; }
    
    /**
     * Sets the applicant's email address
     * @param email email address to assign
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Gets the full resume text content
     * @return resume text extracted from PDF
     */
    public String getResumeText() { return resumeText; }
    
    /**
     * Sets the full resume text content
     * @param resumeText resume content to store
     */
    public void setResumeText(String resumeText) { this.resumeText = resumeText; }

    /**
     * Gets the final ATS ranking score
     * @return score between 0 and 100
     */
    public double getFinalScore() { return finalScore; }
    
    /**
     * Sets the final ATS ranking score
     * @param finalScore ATS score to assign
     */
    public void setFinalScore(double finalScore) { this.finalScore = finalScore; }

    /**
     * Gets the list of extracted technical skills
     * @return list of skills found in resume
     */
    public List<String> getExtractedSkills() { return extractedSkills; }
    
    /**
     * Sets the list of extracted technical skills
     * @param extractedSkills skills list to assign
     */
    public void setExtractedSkills(List<String> extractedSkills) { this.extractedSkills = extractedSkills; }

    /**
     * Gets the education section from resume
     * @return education section text
     */
    public String getEducationSection() { return educationSection; }
    
    /**
     * Sets the education section from resume
     * @param educationSection education content to store
     */
    public void setEducationSection(String educationSection) { this.educationSection = educationSection; }

    /**
     * Gets the professional experience section
     * @return experience section text
     */
    public String getExperienceSection() { return experienceSection; }
    
    /**
     * Sets the professional experience section
     * @param experienceSection experience content to store
     */
    public void setExperienceSection(String experienceSection) { this.experienceSection = experienceSection; }

    /**
     * Gets the projects section from resume
     * @return projects section text
     */
    public String getProjectsSection() { return projectsSection; }
    
    /**
     * Sets the projects section from resume
     * @param projectsSection projects content to store
     */
    public void setProjectsSection(String projectsSection) { this.projectsSection = projectsSection; }

    /**
     * Gets the certifications section from resume
     * @return certifications section text
     */
    public String getCertificationsSection() { return certificationsSection; }
    
    /**
     * Sets the certifications section from resume
     * @param certificationsSection certifications content to store
     */
    public void setCertificationsSection(String certificationsSection) { this.certificationsSection = certificationsSection; }

    /**
     * Gets the total years of professional experience
     * @return number of years calculated from resume dates
     */
    public int getTotalExperienceYears() { return totalExperienceYears; }
    
    /**
     * Sets the total years of professional experience
     * @param totalExperienceYears number of years to assign
     */
    public void setTotalExperienceYears(int totalExperienceYears) { this.totalExperienceYears = totalExperienceYears; }

    /**
     * Gets the resume formatting quality score
     * @return formatting score between 0 and 10
     */
    public int getFormattingScore() { return formattingScore; }
    
    /**
     * Sets the resume formatting quality score
     * @param formattingScore score to assign (0-10 scale)
     */
    public void setFormattingScore(int formattingScore) { this.formattingScore = formattingScore; }
}
