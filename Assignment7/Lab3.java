import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

public class Lab3 {

    private static <T, K> Map<K, T> listToMap(List<T> list, Function<T, K> keyExtractor) {
        return list.stream()
                    .collect(Collectors.toMap(keyExtractor, Function.identity()));
    }

    public static void main(String[] args) {
        List<Student> students = List.of(
                new Student("Kelly", "210762"),
                new Student("Ashley", "210783"),
                new Student("Luke", "210789"),
                new Student("Carol", "210766")
        );

        // Convert list of students to map using id as key
        Map<String, Student> studentMapById = listToMap(students, Student::getId);
        System.out.println("Convert list to map by student id...");
        studentMapById.forEach((key, value) -> System.out.println(key + " -> " + value));

        // Convert list of students to map using name as key
        Map<String, Student> studentMapByName = listToMap(students, Student::getName);
        System.out.println("Conver list to map by student name...");
        studentMapByName.forEach((key, value) -> System.out.println(key + " -> " + value));
    }
}
