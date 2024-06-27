import java.util.ArrayList;
import java.util.List;

class Animal {
    int id;
    String type;

    public Animal(int id, String type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}

class Cat extends Animal {

    public Cat(int id) {
        super(id, "Cat");
    }
}

public class Lab1 {

    // Method to print animals in a list (using wildcard super)
    private static void printAnimals(List<? super Cat> lst) {
        for (Object obj : lst) {
            System.out.println(obj);
        }
    }

    // Method to count specific types of animals (using wildcard extends)
    private static int countCats(List<? extends Animal> lst) {
        int count = 0;
        for (Animal animal : lst) {
            if (animal.type.equals("Cat")) {
                count++;
            }
        }
        return count;
    }

    // Method to remove the last element from a list (using unbounded wildcard)
    private static int removeLastElement(List<?> lst) {
        lst.remove(lst.size() - 1);
        return lst.size();
    }

    public static void main(String[] args) {
        // Upper bound wildcard example
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal(1, "Dog"));
        animals.add(new Animal(2, "Cat"));
        animals.add(new Animal(3, "Dog"));
        System.out.println("Printing animals with upper bound wildcard...");
        printAnimals(animals);
        // Lower bound wildcard example
        List<Object> objects = new ArrayList<>();
        objects.add(new Object());
        System.out.println("Printing objects with lower bound wildcard...");
        printAnimals(objects)

        // Unbounded wildcard example
        List<Cat> cats = new ArrayList<>();
        cats.add(new Cat(1));
        cats.add(new Cat(2));
        cats.add(new Cat(3));
        System.out.println("Printing cats with unbounded wildcard...");
        printAnimals(cats)

        // Count the number of cats in the list using countCats method
        System.out.println("Count of cats: " + countCats(cats))

        // Remove the last element from the cats list using removeLastElement method
        System.out.println("Removing last element from cats list...");
        System.out.println("New size of cats list: " + removeLastElement(cats));
    }
}
