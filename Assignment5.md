<h2>Thread-safe Singleton</h2>

Singleton is a pattern that ensures the class instantiable only once throughout the code while providing a global point of access to that instance.

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
    // The static final instance variable holds the single instance of the EagerSingleton class.
    private static final EagerSingleton instance = new EagerSingleton();

    private EagerSingleton() { }

    // Returns the single instance of the EagerSingleton class.
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

    // This is synchronized to ensure thread safety during instance creation.
    public static synchronized LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
```
A thread-safe singleton means only one instance of a class is created and accessed safely by multiple threads simultaneously. There are few ways to create a thread-safe singleton. One of the effective way to create thread-safe singleton is using synchronize block inside the if loop.
```
/**
public class Singleton {
    private Singleton() {}

    // If the instance is not already created, it will create one and return it.
    public static Singleton getInstance() {
        // Double-check if the instance is null to avoid unnecessary synchronization once the instance is created.
        if (instance == null) {
            // Synchronize on the class object to ensure only one thread enters this block at a time.
            synchronized (Singleton.class) {
                // Double-check again if the instance is still null within the synchronized block.
                if (instance == null) {
                    // Create a new instance of the Singleton class.
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```
In this approach, <b>double-check locking</b> is used inside the getInstance() method. The volatile keyword ensures that changes made to the instance variable are immediately visible to other threads, preventing any potential issues with thread caching.
