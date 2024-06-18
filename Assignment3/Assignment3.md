<h2>1. Research and explain try-with-resources</h2>
Try-with-resources is a Java feature introduced in Java 7 that simplifies the management of resources that require closing, such as streams, files, sockets, and database connections. It ensures that each resource is closed at the end of the statement, even if an exception occurs, using a more concise and readable syntax.

```
try (ResourceType1 resource1 = initializeResource1();
     ResourceType2 resource2 = initializeResource2();
     // ... more resources as needed
) {
    // Use the resources here
    // Resources are automatically closed at the end of the block
} catch (ExceptionType e) {
    // Exception handling code
}
```
### Purpose

The purpose of try-with-resources is to eliminates the need for explicit finally blocks to close resources, resulting in cleaner and more readable code. It also reduces common coding errors related to resource management, such as forgetting to close resources.

### Key Points
#### Resource Initialization
Inside the parentheses after try, declare and initialize the resources that need to be closed after their use. Each resource must implement the AutoCloseable interface or its predecessor Closeable.

#### Automatic Closing
The try-with-resources statement ensures that all declared resources are automatically closed at the end of the try block, regardless of whether the code completes normally or throws an exception.

#### Exception Handling
If an exception is thrown during the execution of the try block, the resources are closed in the reverse order of their declaration. Any exception that occurs during closing is suppressed if there is an exception thrown from the try block. We can access these suppressed exceptions using Throwable.getSuppressed().

<h2>2. Throw vs throws</h2>

### throw
Throw is used to explicitly throw an exception from the code. It is used inside a method or block to throw an exception object explicitly when a certain condition occurs.

```
throw new ExceptionType("error message");
```
Throw is followed by an instance of an exception (new ExceptionType) that is thrown during runtime to signal an exceptional condition. Itallows custom handling of exceptional situations within methods.

### throws
Throws uses to specifies a method may throw one or more types of exceptions.It is used in method declarations to indicate that the method can throw exceptions of specific types.
```
void methodName() throws ExceptionType1, ExceptionType2, ... { ... }
```
Throws is used in the method signature to declare that a method may throw exceptions of a specified type. It does not throw an exception itself but rather declares the type(s) of exceptions that might be thrown by the method. It also allows callers of the method to handle or propagate exceptions.

In summary, `throw` is used for throwing an exception, while `throws` is used to declare that a method might throw one or more exceptions.



