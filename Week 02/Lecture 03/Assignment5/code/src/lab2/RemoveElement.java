package lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

// Remove lines which is duplicated data by 1 key field
// Input: file data (.csv or .txt) and position key field for txt or key field name for csv
// Output: write to new file with no duplication by key field

public class RemoveElement {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        String inputFile = "src/lab2/input.txt";
        String outputFile = "src/lab2/output.txt";

        Set<String> uniqueWord = new HashSet<>();
        File file = new File(inputFile);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineWords = line.split("\\s+");
                
                for (String word : lineWords) {
                    uniqueWord.add(word);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            e.printStackTrace();
        }
        for (String word : uniqueWord){
            sb.append(word).append(" ");
        }
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(sb.toString());
            System.out.println("Output file: " + outputFile);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}





