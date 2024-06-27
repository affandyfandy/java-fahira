import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

class Student {
    String name;
    String id;

    public Student(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

public class Lab2 {

    private static <T> void sortByField(List<T> list, Comparator<? super T> comparator) {
        Collections.sort(list, comparator);
    }

    private static <T, U extends Comparable<? super U>> Optional<T> maxByField(List<T> list, Comparator<? super T> comparator) {
        return list.stream().max(comparator);
    }

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Kelly", "210762"));
        students.add(new Student("Ashley", "210783"));
        students.add(new Student("Luke", "210789"));
        students.add(new Student("Carol", "210770"));
        students.add(new Student("Zeni", "210729"));

        System.out.println("Sort by student name (ascending)...");
        sortByField(students, Comparator.comparing(Student::getName));
        System.out.println(students);

        System.out.println("Sort by student id (ascending)...");
        sortByField(students, Comparator.comparing(Student::getId));
        System.out.println(students);

        System.out.println("Find student by maximum id...");
        Optional<Student> studentWithMaxId = maxByField(students, Comparator.comparing(Student::getId));
        System.out.println(studentWithMaxId);

        System.out.println("Find student by maximum name...");
        Optional<Student> studentWithMaxName = maxByField(students, Comparator.comparing(Student::getName));
        System.out.println(studentWithMaxName);

    }
}
