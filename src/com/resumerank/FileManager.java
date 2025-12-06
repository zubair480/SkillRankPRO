package com.resumerank;

import java.io.*;
import java.util.*;

/**
 * FileManager Class - Handles File I/O Operations
 * 
 * This class manages reading and writing of ATS evaluation results to/from
 * text files. It serves as the persistence layer for the application,
 * allowing results to be stored and retrieved from disk.
 * 
 * BONUS FEATURE: Demonstrates file read/write capabilities as required by
 * project guidelines. Methods serialize applicant scoring data to text format
 * and retrieve it for reporting/analysis.
 * 
 * User-Defined Methods (2):
 * 1. saveScores() - Persists applicant evaluation results to file
 * 2. readScores() - Retrieves previously saved results from file
 * 
 * File Format: Text file with pipe-separated values and section separators
 * Filename: results.txt
 * 
 * @author ATS Resume Ranking System
 * @version 2.0
 */
public class FileManager {

    /**
     * Saves an applicant's ATS evaluation scores to results.txt file
     * 
     * This method writes the complete scoring breakdown for an applicant
     * to a text file, including:
     * - Applicant identification (ID, name)
     * - Contact information (email)
     * - Best matching role and its score
     * - Score breakdown across all evaluated job roles
     * 
     * File I/O Implementation:
     * - Uses BufferedWriter with FileWriter for efficient I/O
     * - Uses try-with-resources for automatic resource management
     * - Appends results (doesn't overwrite previous entries)
     * - Includes separator line for readability
     * 
     * @param applicant The Applicant object containing scoring results
     * @param scores Map of all role scores (role name -> ATS score)
     * @param bestRole The job role with the highest ATS score
     */
    public void saveScores(Applicant applicant, Map<String, Double> scores, String bestRole) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("results.txt", true))) {
            // Write applicant identification
            bw.write("Applicant: " + applicant.getApplicantId() + " - " + applicant.getName());
            bw.newLine();
            
            // Write contact information
            bw.write("Email: " + applicant.getEmail());
            bw.newLine();
            
            // Write best matching role and score
            bw.write("Best Fit Role: " + bestRole + " (Score: " + applicant.getFinalScore() + ")");
            bw.newLine();
            
            // Write header for all role scores
            bw.write("All Role Scores");
            bw.newLine();
            
            // Write all role scores from the map
            for (Map.Entry<String, Double> entry : scores.entrySet()) {
                bw.write(entry.getKey() + " : " + entry.getValue());
                bw.newLine();
            }
            
            // Write separator for visual organization
            bw.write("======================================");
            bw.newLine();
            
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    /**
     * Reads previously saved ATS evaluation results from results.txt file
     * 
     * This method retrieves all lines from the results.txt file and returns
     * them as a list of strings for display or further processing.
     * 
     * File I/O Implementation:
     * - Uses BufferedReader with FileReader for efficient reading
     * - Uses try-with-resources for automatic resource management
     * - Reads entire file line by line
     * - Returns empty list if file doesn't exist or is empty
     * 
     * @return List of strings, each representing one line from the results file
     *         Returns empty list if file cannot be read or doesn't exist
     */
    public List<String> readScores() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("results.txt"))) {
            String line;
            // Read all lines from the file
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return lines;
    }
}
