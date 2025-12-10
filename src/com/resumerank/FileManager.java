package com.resumerank;

import java.io.*;
import java.util.*;

/** Manages file I/O operations for ATS evaluation results */
public class FileManager {

    /** Saves applicant scores to results.txt file */
    public void saveScores(Applicant applicant, Map<String, Double> scores, String bestRole) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("results.txt", true))) {
            bw.write("Applicant: " + applicant.getApplicantId() + " - " + applicant.getName());
            bw.newLine();
            bw.write("Email: " + applicant.getEmail());
            bw.newLine();
            bw.write("Best Fit Role: " + bestRole + " (Score: " + applicant.getFinalScore() + ")");
            bw.newLine();
            bw.write("All Role Scores");
            bw.newLine();
            for (Map.Entry<String, Double> entry : scores.entrySet()) {
                bw.write(entry.getKey() + " : " + entry.getValue());
                bw.newLine();
            }
            bw.write("======================================");
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    /** Reads previously saved ATS results from results.txt */
    public List<String> readScores() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("results.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return lines;
    }
}
