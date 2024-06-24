package lab4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

class Student {
    private String name;
    private int age;
    private int studentClass;

    public Student(String name, int age, int studentClass){
        this.name = name;
        this.age = age;
        this.studentClass = studentClass;
    }

    public String getName(){
        return this.name;
    }

    public int getAge(){
        return this.age;
    }

    public int getStudentClass(){
        return this.studentClass;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age + '\'' +
                ", class=" + studentClass +
                '}';
    }
}

public class Lab4 {
    static List<Student> students = new ArrayList<>();
    static Scanner in = new Scanner(System.in);

    private static List<Student> orderedStudents(){
        List<Student> orderedStudent = students.stream()
                                                .sorted(Comparator.comparing(Student::getName))
                                                .toList();
        return orderedStudent;
    }

    private static void loadData(){
        Student s1 = new Student("Kelly", 19, 2);
        Student s2 = new Student("Wakasa", 20, 4);
        Student s3 = new Student("Luke", 18, 4);
        Student s4 = new Student("Amber", 22, 5);
        Student s5 = new Student("Sydney", 20, 3);

        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        students.add(s5);
    }

    private static Student oldestStudent(){
        Optional<Student> oldest = students.stream()
                                    .max(Comparator.comparingInt(Student::getAge));
        return oldest.orElse(null);
    }

    private static List<Student> findStudentByKeyword(String keyword){
        List<Student> selectedStudent = students.stream()
                                                .filter(s -> s.getName()
                                                .toLowerCase()
                                                .contains(keyword.toLowerCase()))
                                                .toList();
        return selectedStudent;
    }
    public static void main(String[] args) {
        System.out.println("Create 5 students...");
        loadData();

        System.out.println("Show all students ordered by name...");
        orderedStudents().forEach(System.out::println);

        System.out.println("Find student has max age...");
        System.out.println(oldestStudent());

        System.out.println("Check any student names match with specific keywords...");
        System.out.print("Keyword: ");
        String input = in.next();
        findStudentByKeyword(input).forEach(System.out::println);
    }
}

