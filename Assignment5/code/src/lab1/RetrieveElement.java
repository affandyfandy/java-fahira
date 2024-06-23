package lab1;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RetrieveElement {
    static final Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(10);
        numbers.add(20);
        numbers.add(30);
        numbers.add(40);
        numbers.add(50);
        try{
            System.out.print("Get element at index: ");
            int idx = in.nextInt();
            System.out.println("Element at index " + idx + ": " + numbers.get(idx));
        } catch (InputMismatchException e){
            System.out.println(e.getMessage());
        } catch (IndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }
        in.close();
    }
}
