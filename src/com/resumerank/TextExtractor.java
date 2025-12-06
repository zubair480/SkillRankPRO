package com.resumerank;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class TextExtractor {

    public static void main(String[] args) {
        try {
            // 1) Change this to your actual PDF path
            File pdfFile = new File("C:\\Users\\aryab\\Downloads\\JavaDeveloperSampleResume.pdf");

            if (!pdfFile.exists()) {
                System.out.println("File not found: " + pdfFile.getAbsolutePath());
                return;
                
            }

            // 2) Load PDF
            PDDocument document = PDDocument.load(pdfFile);

            if (!document.isEncrypted()) {
                PDFTextStripper pdfStripper = new PDFTextStripper();
                String text = pdfStripper.getText(document);
                System.out.println("Extracted Text:\n" + text);
            } else {
                System.out.println("PDF is encrypted and cannot be processed.");
            }

            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
