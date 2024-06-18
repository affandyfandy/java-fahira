package lab3;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class App {
    static Scanner in = new Scanner(System.in);
    static Set<Character> vowels = new HashSet<>();
    static {
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');
    }

    private static void checkVowel(char[] arrChar){
        boolean res = false;
        for (char ch : arrChar){
            if (vowels.contains(Character.toLowerCase(ch))){
                res = true;
                break;
            }
        }
        if (!res){
            throw new VowelNotFoundException("The string doesn't contain any vowel");
        }
    }

    public static void main(String[] args) {
        System.out.print("Enter a string: ");
        String input = in.nextLine().trim();
    
        try{
            char[] charInput = input.toCharArray();
            checkVowel(charInput);
        } catch (VowelNotFoundException e){
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("System exit...");
        }
        in.close();
    }
}