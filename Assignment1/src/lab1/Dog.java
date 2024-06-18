package lab1;

public class Dog {
    private String color;
    private String name;
    private String breed;

    // Constructor
    public Dog(String color, String name, String breed) {
        this.color = color;
        this.name = name;
        this.breed = breed;
    }

    // Behaviors or methods
    public void wagTail() {
        System.out.println(name + " is wagging its tail.");
    }

    public void bark() {
        System.out.println(name + " is barking: Woof! Woof!");
    }

    public void eat() {
        System.out.println(name + " is eating.");
    }

    // Getters and setters
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    // Main method for testing (optional)
    public static void main(String[] args) {
        Dog dog = new Dog("White", "Snow", "Kally");

        // Accessing states
        System.out.println("Name: " + dog.getName());
        System.out.println("Color: " + dog.getColor());
        System.out.println("Breed: " + dog.getBreed());

        // Calling behaviors
        dog.wagTail();
        dog.bark();
        dog.eat();
    }
}
