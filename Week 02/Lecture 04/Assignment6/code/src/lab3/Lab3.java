package lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lab3 {
    static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.print("Insert total words: ");
        int total = Integer.parseInt(in.nextLine());
        System.out.println("Input words: ");
        List<String> words = new ArrayList<>();
        for (int i=0; i < total; i++){
            String input = in.nextLine().trim();
            words.add(input);
        }
        System.out.print("Letter: ");
        String letter = in.nextLine();
        long count = words.stream()
                    .filter(s -> s.startsWith(letter))
                    .count();
        System.out.println(count);
        in.close();
    }
}
