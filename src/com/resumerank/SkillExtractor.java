package com.resumerank;

import java.util.*;

/** Extracts technical skills from resume text */
public class SkillExtractor {

    private List<String> masterSkills = Arrays.asList(
            "java", "spring", "spring boot", "python", "sql", "mysql", "postgres",
            "aws", "azure", "gcp", "docker", "kubernetes", "microservices",
            "react", "javascript", "html", "css", "tableau", "power bi",
            "machine learning", "ml", "deep learning", "nlp"
    );

    /** Extracts matching skills from resume text */
    public List<String> extractSkills(String text) {
        List<String> found = new ArrayList<>();
        if (text == null) return found;
        String lower = text.toLowerCase();
        for (String skill : masterSkills) {
            if (lower.contains(skill.toLowerCase())) {
                found.add(skill);
            }
        }
        return found;
    }
}
