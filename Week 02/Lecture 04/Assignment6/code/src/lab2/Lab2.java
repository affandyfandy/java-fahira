package lab2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Lab2 {
    private static void removeDuplicates(String inputFile, String outputFile, String field) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            Set<String> keys = new HashSet<>();

            reader.lines()
                .map(line -> line.split(","))
                .filter(fields -> keys.add(fields[getIndexForKey(field)]))
                .map(fields -> String.join(",", fields))
                .forEach(line -> {
                try {
                    writer.write(line);
                    writer.newLine();
                } catch (IOException e) {
                    throw new RuntimeException("Error writing line to file", e);
                }
            });
        }
    }

    private static int getIndexForKey(String field) {
        return 0;
    }

    public static void main(String[] args) {
        String inputFile = "src/lab2/input.csv";
        String outputFile = "src/lab2/output.csv";
        String keyField = "id";

        try {
            removeDuplicates(inputFile, outputFile, keyField);
            System.out.println("Duplicates removed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
