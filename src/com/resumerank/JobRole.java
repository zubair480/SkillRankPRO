package com.resumerank;

import java.util.List;

/**
 * JobRole Class - Represents a Target Job Position
 * 
 * This class encapsulates the requirements for a specific job role/position.
 * It stores the skills and keywords required for the job and is used by the
 * RankingEngine to match against applicant resumes.
 * 
 * The job role serves as the baseline for evaluation. An Applicant's resume
 * is compared against the JobRole's requirements to generate an ATS score.
 * 
 * Data Points (3 attributes):
 * - roleName: Official job title (e.g., "Java Developer", "Data Analyst")
 * - skills: Required technical skills for the position
 * - keywords: Industry/domain keywords related to the role
 * 
 * Example: Java Developer role might require:
 *   Skills: Java, Spring Boot, SQL, Docker, Microservices, REST APIs
 *   Keywords: backend, OOP, unit testing, API, deployment
 * 
 * @author ATS Resume Ranking System
 * @version 2.0
 */
public class JobRole {

    // ============ JOB ROLE ATTRIBUTES ============
    
    /** Official name/title of the job position (e.g., "Software Engineer", "Data Analyst") */
    private String roleName;
    
    /** List of required technical skills for this position */
    private List<String> skills;
    
    /** List of industry keywords and terminology relevant to this role */
    private List<String> keywords;

    /**
     * Constructor for JobRole
     * Creates a new job role with specified requirements
     * 
     * @param roleName Official title of the job position
     * @param skills List of technical skills required for the role
     * @param keywords List of keywords/terminology for the industry/role
     */
    public JobRole(String roleName, List<String> skills, List<String> keywords) {
        this.roleName = roleName;
        this.skills = skills;
        this.keywords = keywords;
    }

    // ============ GETTERS ============
    
    /**
     * Gets the name of the job role
     * @return job position title
     */
    public String getRoleName() { return roleName; }
    
    /**
     * Gets the list of required technical skills
     * @return skills list for this position
     */
    public List<String> getSkills() { return skills; }
    
    /**
     * Gets the list of relevant keywords/terminology
     * @return keywords list for this position
     */
    public List<String> getKeywords() { return keywords; }
}
