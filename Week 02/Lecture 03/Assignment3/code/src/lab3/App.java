package lab3;

import java.util.Scanner;

public class App {
    static Scanner in = new Scanner(System.in);

    private static void checkVowel(char[] arrChar){
        boolean res = false;
        for (char ch : arrChar){
            if (ch == 'a' || ch == 'i' || ch == 'u' || ch == 'e' || ch == 'o'){
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
            System.out.println(e.getMessage());
        } finally {
            System.out.println("System exit...");
        }
        in.close();
    }
}