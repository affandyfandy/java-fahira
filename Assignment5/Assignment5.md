<h2>ArrayList vs LinkedList</h2>

### ArrayList
- <b>Access</b>. ArrayList is generally faster in accessing elements by index because it implements an array internally. The complexity of indexing is O(1).
- <b>Search</b>. ArrayList is suitable for process searching based on index (such as indexOf, lastIndexOf). The complexity of the operations is O(n).
- <b>Memory</b>. ArrayList uses less memory thank LinkedList for sorting elements.
- <b>Iteration</b>. Iterating over an ArrayList is typically faster than LinkedList because it uses sequential access.

### LinkedList
- <b>Memory</b>. Each element in a LinkedList contains a reference to the next and previous elements, so it consume more memory than ArrayList.
- <b>Access</b>. LinkedList does not provide random access to elements by index.
- <b>Insertion and Deletion</b>. LinkedList performs insert and delete operations efficiently (O(1) time complexity), compared to ArrayList which may require resizing the internal array (O(n) time complexity).

### When to use LinkedList vs. ArrayList
ArrayList: Use to the application that need fast access and iteration, and when the list size is relatively fixed or changes infrequently. Also, prefer ArrayList if memory usage is a concern or if it will perform a lot of index-based operations.

LinkedList: Use when frequent insertions and deletions are required, especially if they are near the beginning or middle of the list. Also, consider LinkedList if memory consumption is not a critical issue and if when it do not need frequent index-based access.

<h2>HashSet vs TreeSet vs LinkedHashSet</h2>

| Feature             | HashSet                                    | TreeSet                                   | LinkedHashSet                             |
|---------------------|--------------------------------------------|-------------------------------------------|-------------------------------------------|
| **Order**           | No specific order (based on hash codes)     | Sorted order (natural or custom)          | Insertion order                           |
| **Null element**    | Allows one `null` element                   | Does not allow `null` elements            | Allows one `null` element                 |
| **Performance**     | O(1) average for add, remove, contains     | O(log n) for add, remove, contains        | O(1) average for add, remove, contains    |
|                     | O(n) in worst-case due to collisions       | operations due to balanced tree structure |                                           |
| **Synchronized**    | Not synchronized                           | Not synchronized                          | Not synchronized                          |
| **Fail-Fast/Safe**  | Fail-Fast                                  | Fail-Fast                                 | Fail-Fast                                 |
| **When to Use**     | Fast access and uniqueness, order not important      | Sorting elements, efficient smallest/largest element retrieval      | Maintain insertion order with uniqueness |

In summary, HashSet is efficient for general-purpose use with fast add, remove, and contains operations, but does not maintain any specific order. TreeSet maintains elements in sorted order, suitable when elements need to be sorted and efficient retrieval of smallest/largest elements is required. LinkedHashSet maintains insertion order of elements while providing fast access times similar to HashSet, suitable when it need to maintain the order of insertion and uniqueness.

<h2>Shallow Copy of HashMap</h2>

To get a shallow copy of a `HashMap`, we can use the `clone()` method or create a new HashMap instance and initialize it with the existing HashMap. However, it's important to note that these methods create a shallow copy, meaning that the keys and values themselves are not cloned; rather, the references to the same key-value pairs are copied into the new map.

1. **Using the clone() method**
```java
public static void main(String[] args) {
    // Original HashMap
    HashMap<Integer, String> originalMap = new HashMap<>();
    originalMap.put(1, "One");
    originalMap.put(2, "Two");
    originalMap.put(3, "Three");

    // Shallow copy using clone()
    HashMap<Integer, String> shallowCopyMap = (HashMap<Integer, String>) originalMap.clone();

    // Modify the shallow copy and original map to see the effect
    shallowCopyMap.put(4, "Four");

    // Print original and shallow copy maps
    System.out.println("Original Map: " + originalMap);
    System.out.println("Shallow Copy Map: " + shallowCopyMap);
}
```

2. **Using the `HashMap` copy constructor**
```java
public static void main(String[] args) {
    // Original HashMap
    HashMap<Integer, String> originalMap = new HashMap<>();
    originalMap.put(1, "One");
    originalMap.put(2, "Two");
    originalMap.put(3, "Three");

    // Shallow copy using copy constructor
    HashMap<Integer, String> shallowCopyMap = new HashMap<>(originalMap);

    // Modify the shallow copy and original map to see the effect
    shallowCopyMap.put(4, "Four");

    // Print original and shallow copy maps
    System.out.println("Original Map: " + originalMap);
    System.out.println("Shallow Copy Map: " + shallowCopyMap);
}
```

<h2>equals() and hashCode()</h2>

### equals()
The equals method is used to compare two objects. The default equals method is defined in the object class. That implementation is similar to the == operator. The two object references are equal only if they are pointing to the same object. It is possible to override the equals method.

```java
public class Student{
    private String name;
    private String major;

    public Student(String name, String major){
        this.name = name;
        this.major = major;
    }

    public static void main(String[] args){
        Student s1 = new Student("Fahira", "CS");
        Student s2 = new Student ("Fahira, "Engineering");
        Student s3 = s1;

        System.out.println(s1.equals(s2));
        System.out.println(s1.equals(s3));
    }
}
```
The statement System.out.println(s1.equals(s2)) will return `false` because s2 doesn't referring to the same object as s1. In contrast, the statement System.out.println(s1.equals(s3)) will return `true` because s1 and s3 are referring to the same object.

If we look at the class, there is no equals() method defined inside the Student class. However, the program doesn't error because equals() method is automatically instantiated when we defined an object class. The equals() method also can be overriden. It could be define the equals() method as below.

```java
    public boolean equals(Object std){
        Student student = (Student) std;
        if (name != std.name){
            return false;
        }
        return true;
    }

```

### hashCode()
The hashCode is used in hashing to decide to which group an object should be categorized into. A group of objects can share the same hashCode. This method returns a hashCode() value as an Integer. If a class overrides the equal() method, it must implement the hashCode() method as well.

```java
public class Student{
    ...
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Student)) {
            return false;
        }
        Student other = (Student) obj;
        return this.name.equals(other.name) && this.major.equals(other.major);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, major);
    }
}

public class Main{

    public static void main(String[] args){
        Student s1 = new Student("Fahira", "CS");
        Student s2 = new Student ("Fahira, "CS");

        System.out.println(s1.equals(s2)); // print true

        System.out.println(s1.hashCode());
        System.out.println(s2.hashCode());
    }
}
```

The Student class contains the equals and hashCode methods. The equals method in the Student class will receive an object. If the object is `null`, it will return `false`. If the classes of the objects are not the same, it will return false. The name and major values are checked in both objects. If they are similar, it will return `true`, else it will return `false`.

In the main program, objects s1 and s2 are created. When calling s1.equals(s2) will give `true` because the equals method is overridden and it checks the `name` and `major` values of the two objects. Even though they are referring to two objects, the answer is `true` because the `name` and `major` values of s1 and s2 are the same. As the s1.equals(s2) is `true`, the hashCode of s1 and s2 should be equal. Printing the hashCode of s1 and s2 gives the same value. The hashCode method can be used with Collections such as HashMap.

In summary, the differences between hashCode() and equals().

| equals()             | hashCode()                                 |
|----------------------|--------------------------------------------|
equals is a method in Java that acts similar to the == operator, which is to test for object identity rather than object equality. | hashCode is a method by which a class implicitly or explicitly break down the data stored in an instance of the class into a single hash value. |
The method equals is used to compare two objects. | The method is used in hashing to decide which group an object should be placed into. | 


<h2>Duplicate Employee by ID in HashSet</h2>

HashSet is a collection that doesn't allow duplicate elements. To recognize duplicates in a HashSet, the collection relies on two main methods of the objects it contains: `hashCode()` and `equals(Object obj)`. 

### How HashSet Recognizes Duplicates 
1. **Hash code calculation**, when an object is added to a HashSet, the HashSet computes the hash code of the object using the hashCode() method. The hash code determines which bucket in the internal array of the HashSet the object should be placed in.

2. **Collision handling**, if multiple objects produce the same hash code (hash code collision), HashSet uses the equals() method to determine if the objects are actually equal. It checks if any object already in the set has the same hash code and if equals() returns true when comparing the new object with objects in the same bucket.

### Example
Refer to Employee class, we want to add new employee and add it to HashSet<> employees.
```java
public class Main{
    public static void main(String[] args){
        static Set<Employee> employees = new HashSet<>();

        Employee e1 = new Employee("MNG_02", "Mikha");
        Employee e2 = new Employee("SPV_02", "Nardo");
        Employee e3 = new Employee("MNG_02", "Jessie);

        employees.add(e1);
        employees.add(e2);
        employees.add(e3);

        System.out.println(employees.size());
    }
}
```
The statement `employees.add(e3)` won't return add e3 to the set employees because there is already employee with same id as e3. So, the main method will print out 2 as the employees stored only e1 and e2.


<h2>Find and Fix Issues on the Code</h2>

```java
    public static void demo1(){
        List<String> data = new ArrayList<>();
        data.add("Joe");
        data.add("Helen");
        data.add("Test");
        data.add("Rien");
        data.add("Ruby");

        for (String d : data){
            if (d.equals("Test")){
                data.remove(d);
            }
        }
    }
```

Inside the for loop, we are attempting to modify the ArrayList data using `data.remove(d)` while iterating over it using an enhanced for loop `(for (String d : data))`. This will result in a `**ConcurrentModificationException**` because we cannot modify the list directly while iterating over it.

To safely remove the data while iterating the list, we can implement several ways:
1. Using `Iterator` to replace the for loop
```java
Iterator<String> iterator = data.iterator();
while (iterator.hasNext()) {
    String d = iterator.next();
    if (d.equals("Test")) {
        iterator.remove();
    }
}
```
2. Using `ListIterator` to replace the for loop
```java
ListIterator<String> listIterator = data.listIterator();
while (listIterator.hasNext()) {
    String d = listIterator.next();
    if (d.equals("Test")) {
        listIterator.remove();
    }
}
```
Basically, both `ListIterator` and `Iterator` are similar in some aspects. However, key differences of both are `Iterator` only moves forward, whereas `ListIterator` can move both forward and backward and `ListIterator` is specifically designed for lists and hence provides more control and functionality over list operations compared to `Iterator`.

<h2>Concurrent Access of Shared Collection</h2>

When multiple threads access and modify a shared collection concurrently without proper synchronization, it can lead to various issues, including the `ConcurrentModificationException`.

1. **ConcurrentModificationException**

Collections like ArrayList, HashMap, etc., are not inherently thread-safe. This means they do not guarantee safe concurrent access and modification by multiple threads without additional synchronization.

2. **Unsafe concurrent modifications**

Concurrent modifications can corrupt the internal state of the collection or cause the iterator to lose track of its position.
For example, if `Thread A` is iterating over a list and `Thread B` modifies (adds or removes) an element from the same list, Thread A's iterator may detect that the collection has been modified unexpectedly, resulting in a `ConcurrentModificationException`.

### Preventing ConcurrentModificationException
1. **Synchronization**

Use synchronized blocks or methods to ensure that only one thread accesses or modifies the collection at a time.
```java
public static void main(String[] args) {
    List<Integer> numbers = new ArrayList<>();

    // Adding elements to the ArrayList
    for (int i = 0; i < 10; i++) {
        numbers.add(i);
    }

    // Create a shared object to synchronize access to the list
    Object lock = new Object();

    // Runnable task to modify the list safely
    Runnable task = () -> {
        synchronized (lock) {
            // Modify the list inside synchronized block
            for (int i = 0; i < numbers.size(); i++) {
                // Simulating some modification operation
                Integer number = numbers.get(i);
                if (number % 2 == 0) {
                    numbers.remove(number);
                }
            }
        }
    };

    // Create multiple threads to execute the task concurrently
    Thread thread1 = new Thread(task);
    Thread thread2 = new Thread(task);

    thread1.start();
    thread2.start();

    // Wait for threads to complete
    try {
        thread1.join();
        thread2.join();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    // Print the modified list
    System.out.println("Modified List: " + numbers);
}
```

2. **Thread-safe collections**

Use collections from `java.util.concurrent` package (like ConcurrentHashMap, CopyOnWriteArrayList, etc.), which are designed for concurrent access.
```java
List<Integer> numbers = new CopyOnWriteArrayList<>();

for (int i = 0; i < 10; i++) {
    numbers.add(i);
}
```

3. **Use Iterator**

If iterating over a collection while allowing modifications, use concurrent collections or ensure that modifications are done through the iterator's own methods (remove() or add() for ListIterator).
```java
ListIterator<String> listIterator = data.listIterator();
while (listIterator.hasNext()) {
    String d = listIterator.next();
    if (d.equals("Test")) {
        listIterator.remove();
    }
}
```