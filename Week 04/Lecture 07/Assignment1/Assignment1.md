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
<beans
    xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans 
                        https://www.springframework.org/schema/beans/spring-beans.xsd 
                        http://www.springframework.org/schema/context 
                        http://www.springframework.org/schema/context/spring-context.xsd"> 
    <bean id="employeeWork" class="com.lecture7.code.EmployeeWork"/>
    <bean name="employee" class="com.lecture7.code.Employee">
        <property name="id">
            <value>MNG_01</value>
        </property>
        <property name="name">
            <value>Keiko</value>
        </property>
        <property name="age">
            <value>25</value>
        </property>
        <property name="employeeWork" ref="employeeWork"/>
    </bean>
</beans>
```
The `beans.xml` configures and instantiates the employeeWork bean from the `EmployeeWork.java` class and employee bean from the `Employee.java` class. When instantiating the employee bean, an Employee object is created with predefined properties. To utilize this configuration, beans.xml is called inside the main method.

```java
public static void main(String[] args) {
    SpringApplication.run(CodeApplication.class, args);
    ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
    Employee employee = context.getBean("employee", Employee.class);
    employee.working();
}
```
The program outputs the following to the terminal:
```
My Name is: Keiko
Working...
``` 

If we change from XML-based bean declaration to Java-based configuration, the program's output remains unchanged, but we implement it differently.
1. Create a new Java class annotated with `@Configuration`. This class will serve as the replacement for `beans.xml` file.

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

The `AppConfig.java` will replace bean declaration on `beans.xml`. The class will instantiated new employee object and employee work.

2. Adjust the declaration of employee object in main method.

Previously, it retrieved the beans like this.
```java
Employee employee = context.getBean("employee", Employee.class);
```
Since we no longer use XML, we can either eliminate the "employee" bean identifier or modify the syntax as follows:
```java
Employee employee = context.getBean(Employee.class);
```
In summary, creating a `@Configuration` class (AppConfig) to define beans is a more modern and flexible approach compared to XML-based configuration (beans.xml). It provides type safety, easier refactoring, and better integration with Java-based Spring features.

