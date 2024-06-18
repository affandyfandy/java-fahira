package lab3;

import lab2.Subject;
import lab2.Teacher;

public class Main {
    public static void main(String[] args) {
        System.out.println("Create new subjects...");
        Subject subject1 = new Subject("Mathematics", "Class 1");
        Subject subject2 = new Subject("Biology", "Class 1");
        System.out.println("Create new teacher...");
        Teacher teacher = new Teacher("Tam", 35, subject1);
        System.out.println("Create new student...");
        Student student = new Student("Fahira", 26);
        student.setEnroll(subject1);
        student.setEnroll(subject2);
        System.out.println(student.learning());
        
    }
}
