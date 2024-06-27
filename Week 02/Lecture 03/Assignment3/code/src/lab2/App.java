package lab2;

import java.util.Scanner;

public class App {
    static String[] menu = {"Americano", "Ice Vanilla Latte", "Matcha Latte", "Espresso", "Mocha Latte"};
    static Scanner in = new Scanner(System.in);

    private static void validateInput(int input) throws InputOutOfBoundsException {
        if (input < 1 || input > 5) {
            throw new InputOutOfBoundsException("Input is out of the valid range of 1 to 5.");
        }
        else {
            System.out.println("Thank you for ordering " + menu[input-1]);
        }
    }

    public static void main(String[] args) {
        try{
            System.out.println("Load an array consists of 5 beverages menu...");
            System.out.print("Enter your order (1-5): ");
            int cmd = Integer.parseInt(in.nextLine());
            validateInput(cmd);
        } catch (InputOutOfBoundsException e){
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e){
            System.out.println("Error: Input invalid! Please enter a valid number.");
        }
        in.close();
    }

}
