import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Employee implements Serializable{
    private static final long serialVersionUID = 1L;

    private String name;
    private int id;
    private transient double salary;

    public Employee(String name, int id, double salary) {
        this.name = name;
        this.id = id;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{name='" + name + "', id=" + id + ", salary=" + salary + "}";
    }
}

public class Lab1 {

    private static void writeToFile(List<Employee> employees, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(employees);
            System.out.println("Serialization successful. Employees written to file: " + filename);
        } catch (IOException e) {
            System.err.println("Error writing objects to file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static List<Employee> readFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Employee>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading objects from file: " + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Jonathan", 1, 21000000));
        employees.add(new Employee("Julia", 2, 19000000));
        employees.add(new Employee("Kody", 3, 8000000));
        writeToFile(employees, "employees.ser");
        List<Employee> deserializedEmployees = readFromFile("employees.ser");
        if (deserializedEmployees != null) {
            System.out.println("Deserialized Employees:");
            deserializedEmployees.forEach(System.out::println);
        }
    }
}
