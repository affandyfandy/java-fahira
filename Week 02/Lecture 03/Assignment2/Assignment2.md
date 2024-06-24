<h2>1. What happen if implement 2 interface have same default method? How to solve? Demo in code.</h2>
When we implement 2 interfaces that have the same default method (same method name and parameters), it can lead to a <b>multiple inheritance problem</b> conflict. This is because the compiler cannot determine which interface's default method implementation should be used by default in the implementing class.

#### Example
```java
public interface FirstInterface{
    void firstMethod(String string);
    default void log(String string){
        System.out.println("This method is default implementation " + string);
    }
}

public interface SecondInterface{
    void secondMethod(String string);
    default void log(String str){
        System.out.println("This method is default implementation " + str);
    }
}

public class MyClass implements FirstInterface, SecondInterface{
    @Override
    public void firstMethod(String string) {
        System.out.println("Implementation of firstMethod: " + string);
    }
    
    @Override
    public void secondMethod(String string) {
        System.out.println("Implementation of secondMethod: " + string);
    }
    
    // Resolving the conflict by explicitly overriding the log() method
    // Choose which default method to use
    @Override
    public void log(String string) {
        FirstInterface.super.log(string); // Choose which default method to use
    }
}
```

#### Explanation
Both interfaces (FirstInterface and SecondInterface) have a default method log(String string) with the same signature which leads to the conflict. To resolve the conflict, we have to override the log() method explicitly. By the example above, we choose to use the log() implementation from FirstInterface using FirstInterface.super.log(string).

<h2>2. Explain the difference between abstract class and interface (Syntax and Purpose)</h2>

#### Abstract Class
- An abstract class is abstract, in the sense that we can't concretize it (that is, we cannot instantiate any object).
- However (and this is the reason abstract classes can be useful), we can create a class extending an abstract class.
- An abstract class may have abstract methods (beside the standard ones), while an abstract method must be in an abstract class.
- A class extending an abstract class must implement all the abstract
methods.

#### Interface
- Interfaces define a contract: <b>What a class can do, but (generally) not the how</b>
- As opposed to (abstract) classes, a class may "extends" multiple interfaces. Well, it is now called "implements", not "extends".
- Methods in interfaces are implicitly abstract and public.
- Interfaces can't have constructors.
- Interfaces are like features, for example features for smartphone like GPS-featured and radio-featured.

#### Differences between abstract class and interface
| Feature                  | Abstract Class                                              | Interface                                                     |
|--------------------------|-------------------------------------------------------------|---------------------------------------------------------------|
| **Declaration**          | Declared with `abstract` keyword                             | Declared with `interface` keyword                              |                  |
| **Constructor**          | Can have constructors                                       | Cannot have constructors                                       |
| **Accessibility**        | Can have different access modifiers                          | All methods are implicitly `public`                            |
| **Fields**               | Can have instance variables and constants                   | Can only have `public static final` constants (constants)       |
| **Extends/Implements**   | Extended by a class (single inheritance)                     | Implemented by a class (multiple inheritance)                   |
| **Purpose**              | Provides a base implementation and structure for subclasses | Defines a contract for what classes can do (promotes polymorphism) |
| **Usage**                | Represents "is-a" relationship                              | Represents "can-do" relationship                               |      