package com.resumerank;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== ATS Resume Role Classifier ===\n");

        System.out.print("Enter Applicant ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Resume PDF Path: ");
        String path = sc.nextLine();

        ResumeParser parser = new ResumeParser();
        String fullText = parser.extractText(path);

        if (fullText == null || fullText.isBlank()) {
            System.out.println("Could not read resume text. Exiting.");
            return;
        }

        parser.saveExtractedText(fullText);

        SectionExtractor sectionExtractor = new SectionExtractor();
        ExperienceExtractor experienceExtractor = new ExperienceExtractor();
        SkillExtractor skillExtractor = new SkillExtractor();
        FormattingScoreCalculator formattingCalculator = new FormattingScoreCalculator();

        String education = sectionExtractor.extractSection(fullText, "education");
        String experience = sectionExtractor.extractSection(fullText, "experience");
        String projects = sectionExtractor.extractSection(fullText, "projects");
        String certs = sectionExtractor.extractSection(fullText, "certifications");

        int years = experienceExtractor.extractYears(fullText);
        List<String> skills = skillExtractor.extractSkills(fullText);
        int formattingScore = formattingCalculator.calculate(fullText);

        Applicant applicant = new Applicant(id, name, email, fullText);
        applicant.setEducationSection(education);
        applicant.setExperienceSection(experience);
        applicant.setProjectsSection(projects);
        applicant.setCertificationsSection(certs);
        applicant.setTotalExperienceYears(years);
        applicant.setExtractedSkills(skills);
        applicant.setFormattingScore(formattingScore);

        System.out.println("\nParsed Resume Summary");
        System.out.println("---------------------");
        System.out.println("Extracted Skills: " + skills);
        System.out.println("Total Experience Years: " + years);
        System.out.println("Formatting Score (0-10): " + formattingScore);

        RoleManager roleManager = new RoleManager();
        List<JobRole> roles = roleManager.loadRoles();

        RankingEngine engine = new RankingEngine();
        Map<String, Double> scores = engine.scoreApplicantAcrossRoles(applicant, roles);

        Map.Entry<String, Double> best = engine.getBestRole(scores);
        if (best != null) {
            applicant.setFinalScore(best.getValue());
        } else {
            System.out.println("No suitable role detected.");
            return;
        }

        ResultFormatter formatter = new ResultFormatter();
        formatter.formatAsTable(applicant, scores, best.getKey());
        formatter.exportToJSON(applicant, scores, best.getKey(), "results_" + applicant.getApplicantId() + ".json");
        formatter.exportToCSV(applicant, scores, best.getKey(), "results_all.csv");

        FileManager fm = new FileManager();
        fm.saveScores(applicant, scores, best.getKey());
        System.out.println("\n✓ All results saved successfully!");
    }
}
