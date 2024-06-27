package lab2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class Lab2 {

    static Scanner in;

    private static int getIndexForKey(String header, String field, String delimiter) {
        String[] arrHeader = header.split(delimiter);
        for (int i = 0; i < arrHeader.length; i++) {
            if (arrHeader[i].trim().equals(field)) {
                return i;
            }
        }
        return -1;
    }

    private static String detectDelimiter(String header) {
        String[] delimiters = {",", ";", "\t", " "};
        for (String delimiter : delimiters) {
            if (header.contains(delimiter)) {
                return delimiter;
            }
        }
        return "";
    }

     private static void removeDuplicates(String inputFile, String outputFile, String keyField) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String header = reader.readLine();
            String delimiter = detectDelimiter(header);

            int idx = getIndexForKey(header, keyField, delimiter);
            if (idx == -1) {
                throw new IllegalArgumentException("Key field '" + keyField + "' not found in header.");
            }

            Set<String> keys = new LinkedHashSet<>();

            writer.write(header);
            writer.newLine();

            reader.lines()
                .map(line -> line.split(delimiter, -1))
                .forEach(fields -> {
                    if (keys.add(fields[idx])) {
                        try {
                            writer.write(String.join(delimiter, fields));
                            writer.newLine();
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {

        in = new Scanner(System.in);

        String inputFile = "src/lab2/text.txt";
        String outputFile = "src/lab2/text-out.txt";
        System.out.print("Enter key: ");
        String keyField = in.next();
        removeDuplicates(inputFile, outputFile, keyField);
    }
}
