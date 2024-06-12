<h2>Thread-safe Singleton</h2>

Singleton is a pattern that ensures the class instantiable only once throughout the code while providing a global point of access to that instance. Meanwhile, a thread-safe is a concept to ensures that only one instance of a class is created and accessed safely by multiple threads simultaneously.

### Properties
- Creational design pattern
- Only one instance of the class should exist
- Other class should be able to get instance of the singleton class

### Implementation
- Constuctor should be private to prevent external instantiation of the class
- Public static method to provide global access to the instance
- Instance type - private static to hold the single instance of the class

### Types
<b>1. Eager Initialization</b><br>
In the eager initialization approach, the Singleton instance is created at the time of class loading. This approach is simple and thread-safe but it may create the instance even if it's not needed.
```
public class EagerSingleton {
    private static final EagerSingleton instance = new EagerSingleton();

    private EagerSingleton() { }

    public static EagerSingleton getInstance() {
        return instance;
    }
}
```
<b>2. Lazy Initialization</b><br>
With lazy initializaiton, the Singleton instance is created only when it's requested for the first time. For the better implementation thread using, it recommend to using synchronization while initiated the lazy singleton. This approach is thread-safe but the synchronization can introduce performance overhead.
```
public class LazySingleton {
    private static LazySingleton instance;

    private LazySingleton() { }

    public static synchronized LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
```
