<h2>How oop principle perform in Java?</h2>

### Encapsulation
Encapsulation is the action of 'encapsulating' data (attributes) and methods into a single unit, called a class. It is used to restrict direct access to the data, providing control over data access and modification. Encapsulation is able to group the application into several logical parts to prevent duplicate code.

### Inheritance
Inheritance is the mechanism by which a new class (subclass or derived class) is created from an existing class (superclass or base class). The subclass is inheriting their superclass's attributes and methods. Subclass can reuse the properties and behaviors of their superclass and can also define their unique features.

### Polymorphism
Polymorphism allows object of different classes to be treated as object of a common super class. It enables method-call binding, enhances extensibility by facilitating code reuse and allowing new classes to be added without modifying existing code, and overrides implementation.

### Abstaction
Abstraction is the process of representing the essential features of an object while hiding unnecessary details. Abstract classes serve as placeholders for actual implementation classes, defining behaviors that subclasses implement. This principle allows for the creation of general blueprints that can be specialized and adapted to various specific use cases.

<br>
Example:
</br>

```
// Encapsulation: Creating a class with private data members and public methods to access and modify them
class Person {
    private String name;
    private int age;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        if (age > 0) {
            this.age = age;
        }
    }

    public int getAge() {
        return age;
    }
}

// Inheritance: Creating a subclass Student that inherits from the superclass Person
class Student extends Person {
    private int studentId;

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getStudentId() {
        return studentId;
    }
}

// Polymorphism: Creating a method displayInfo that behaves differently based on the type of object
class Display {
    public void displayInfo(Person person) {
        System.out.println("Name: " + person.getName());
        if (person instanceof Student) {
            System.out.println("Student ID: " + ((Student) person).getStudentId());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Student student = new Student();
        student.setName("Alice");
        student.setAge(20);
        student.setStudentId(123456);

        Display display = new Display();
        display.displayInfo(student);
    }
}
```