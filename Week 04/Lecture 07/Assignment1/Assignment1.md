<h2>Advantages and drawbacks of Dependency Injection</h2>

Dependency injection is a programming technique that makes one object supplies the dependencies (object that can be used or a service) of another object. In short, dependency injection is a way of passing the dependencies of an object to that object, rather than having the object create its own dependencies.

### Benefits
1. **Loose coupling**. Loose coupling means that the classes are independent of each other. The only knowledge one class has about the other class is what the other class has exposed through its interfaces.
2. **Flexibility**. DI separates the creation and consumption of dependencies, allowing for code to be organized into smaller units with clear responsibilities and interfaces.
3. **Maintainability**. DI decouples code from specific implementations, making it easy to swap or update dependencies without affecting the rest of the system.

### Drawbacks
1. **Increased complexity**. Implementing DI has the potential to introduce additional complexity to codebase. The need to define dependencies, manage their lifecycles, and configure the injection can make the code harder to understand.
2. **Performance overhead**. DI can introduce some level of performance overhead due to the dynamic resolution of dependencies at runtime. Although modern DI frameworks and toolkits are optimized for efficiency, there can still be a slight impact on the application performance.
3. **Increased learning curve**. Adopting DI requires developers to understand its concepts, principles, and recommended practices. This might come with some initial time overhead and potentially limit development at first as a result of the learning curve.

<h2>Convert bean declaration from xml to Java config</h2>

In the original form, the beans are declared in an XML file as shown below, or you can refer to this [code](code/src/main/java/beans.xml).

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans 
                           http://www.springframework.org/schema/beans/spring-beans.xsd 
                           http://www.springframework.org/schema/context 
                           http://www.springframework.org/schema/context/spring-context.xsd"> 

    <bean id="employee" class="com.lecture7.code.Employee">
        <constructor-arg name="id" value="MNG_001"/>
        <constructor-arg name="name" value="Keiko"/>
        <constructor-arg name="age" value="35"/>
        <constructor-arg>
            <bean class="com.lecture7.code.EmployeeWork"/>
        </constructor-arg>
    </bean>
</beans>
```
The `beans.xml` configures and instantiates the employeeWork bean from the `EmployeeWork.java` class and employee bean from the `Employee.java` class. When instantiating the employee bean, an Employee object is created with predefined properties. To utilize this configuration, `beans.xml` is called inside the main method.

```java
public static void main(String[] args) {
    SpringApplication.run(CodeApplication.class, args);
    ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
    Employee employee = context.getBean(Employee.class);
    employee.working();
}
```
The program outputs the following to the terminal:
```
My Name is: Keiko
Working...
``` 

If we want to change from XML bean declaration to Java configuration, we can create new file (example AppConfig) to set the configuration of the beans. Before stated the class, we use annotation `@Configuration`. `@Configuration` is a class-level annotation indicating that an object is a source of beans definitions. `@Configuration` classes declare beans through `@Beans` annotated methods.

The `@Bean` annotation is used to indicate that a method instantiates, configures, and initializes a new object to be managed by the Spring IoC container. In this case, `@Bean` will plays same role as </bean/> in XML configuration.

Hereby the implementation of Java configuration for employee and employee work.

<h3>1. Create a new Java class annotated with `@Configuration` inside the config folder</h3>

```java
@Configuration
public class AppConfig {

    @Bean
    public Employee employee() {
        return new Employee("MNG_001", "Keiko", 35, employeeWork());
    }

    @Bean
    public EmployeeWork employeeWork() {
        return new EmployeeWork();
    }
}
```

`AppConfig` is the application configuration class. It is decorated with the `@Configuration` annotation, which is a specialization of the `@Component`. The `AppConfig.java` will replace bean declaration on `beans.xml`.

<h3>2. Adjust the declaration ApplicationContext in main method</h3>

Previously, it declared like this.
```java
ApplicationContext context = new AnnotationConfigApplicationContext("beans.xml");
```
Since we no longer use XML, we can replace the `beans.xml` to the new class AppConfig.
```java
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
```
In summary, creating a `@Configuration` class (AppConfig) to define beans is a more modern and flexible approach compared to XML-based configuration (beans.xml). It provides type safety, easier refactoring, and better integration with Java-based Spring features.

<h2>Setter based dependency injection and field dependency for Employee app</h2>

### Setter Injection
Setter injection can be implemented by creating a setter method inside the Employee class and adding a new constructor for Employee with no parameter.
```java
public void Employee(){}

public void setEmployeeWork(EmployeeWork employeeWork){
    this.employeeWork = employeeWork;
}
...

```

### Field Injection
To implement field injection in Employee class, we can add `@Autowired` above the EmployeeWork field in the Employee class.
```java
public class Employee {

    private String id;
    private String name;
    private int age;

    @Autowired
    private EmployeeWork employeeWork;
...
}
```