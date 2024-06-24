package lab1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        start();
    }

    private static void start(){
        String directory = "src/lab1/";
        String inputFile = directory + "test1.txt";
        String outputFile = directory + "test2.txt";
        try {
            String fileContent = readFile(inputFile);
            
            if (fileContent.isEmpty()){
                System.out.println("The file " + inputFile + " is empty.");
            }
            else {
                System.out.println("Content read from " + inputFile + ":");
                System.out.println(fileContent);
                writeFile(outputFile, fileContent);
                System.out.println("Content written to " + outputFile);
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String readFile(String fileName) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    private static void writeFile(String fileName, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        }
    }
}
