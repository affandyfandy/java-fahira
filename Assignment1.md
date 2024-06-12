<h2>Compare: Value types and reference types</h2>
Value types are the primitive data types such as `int`, `float`, `double`, `char`, `boolean`, `byte`, `short`, and `long`. The values of value types are allocated on the stack, which makes their performance faster than reference types. Value types are used for primitive data with simple structure and fixed size. A value type is initialized with direct assignment of the value and passes parameters by copying the data.
<br>Example:<br>

```
int x = 5;
int y = x; // y is a copy of x
y = 10; // y is updated to 10, but x is still 5
System.out.println("x = " + x); // Output: x = 5
System.out.println("y = " + y); // Output: y = 10
```

In contrast to value types, reference types are instances of classes that are stored as references to memory locations. Examples of reference types are objects and arrays. Reference types are used for more complex data structures and objects. They are ideal for data that needs to be shared and modified across different parts of an application. When a reference type is passed to a method, a reference to the memory location is passed, meaning that changes to the parameter within the method will affect the original object.
<br>Example:<br>
```
public static void increment(BigInteger val) {
    // Increment the value by 1 and assign it back to val
    val = val.add(BigInteger.ONE);
}

public static void main(String[] args) {
    BigInteger initVal = new BigInteger("0");
    increment(initVal);
    System.out.println("Current value is " + initVal); // Output: Current value is 0
}
```

In summary, the differences between value and reference types are shown in the table below
#### Value Types vs. Reference Types in Java

| Feature              | Value Types                     | Reference Types                   |
|----------------------|----------------------------------|-----------------------------------|
| **Storage Location** | Stack                            | Heap                              |
| **Contain Data**     | Directly                         | Reference to data                 |
| **Typical Usage**    | Primitive data, simple structures| Complex objects, collections      |
| **Passing Parameters** | By value (copy of data)          | By reference (reference to data)  |
| **Initialization**   | Direct assignment                | Using constructors                |
| **Performance**      | Faster (stack allocation)        | Slower (heap allocation)          |
