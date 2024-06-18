package lab2;
import java.util.Scanner;

public class Main {
    static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Create new subject...");
        Subject subject = new Subject("Mathematics", "Class 1");
        System.out.println("Create new teacher...");
        Teacher teacher = new Teacher("Tam", 35, subject);
        System.out.println(teacher.teaching());
    }
}
