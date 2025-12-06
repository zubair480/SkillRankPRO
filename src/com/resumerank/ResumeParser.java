package com.resumerank;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResumeParser {

    public String extractText(String filePath) {

        String text = "";

        try {
            File pdfFile = new File(filePath);

            if (!pdfFile.exists()) {
                System.out.println("File not found: " + pdfFile.getAbsolutePath());
                return "";
            }

            try (PDDocument document = PDDocument.load(pdfFile)) {
                PDFTextStripper stripper = new PDFTextStripper();
                text = stripper.getText(document);
            }

        } catch (IOException e) {
            System.out.println("PDF read error: " + e.getMessage());
        }

        return text;
    }
    public void saveExtractedText(String text) {
    try {
        FileWriter fw = new FileWriter("extracted_text.txt");
        fw.write(text);
        fw.close();
        System.out.println("Extracted text saved to extracted_text.txt");
    } catch (IOException e) {
        System.out.println("Error saving extracted text: " + e.getMessage());
    }
}

}
