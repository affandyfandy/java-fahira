<h2>Comparison types of dependency injection</h2>

### Constructor Injection
The constructor injection means passing the dependencies as parameters to the constructor of the class that needs them.
#### Pros
- Explicit dependencies are clearly defined in the constructor
- Objects are guareanteed to be in valid state after construction
- Clearly definition where dependencies of the class are came from
- Enables to use final fields which can prevent accidental change and improve performance
#### Cons
- Make constructors too long and complex, especillah if there are many dependencies or nested dependencies
- Does not support optional or dynamic dependencies, as we can't change or remove the once the class is created

### Setter Injection
Setter injection provides setter methods for the dependencies of a class, and calling them after class is instantiated.
#### Pros
- Have more flexibility and control on how and when to inject the dependencies
- Allow have dynamic dependencies as we can change or remove them at any time
#### Cons
- Increased coupling between classes because it exposes the internal state of the object and make it accessible for modification from outside
- There is no guarantee that all required dependencies will be set befor the object is used so it will less suitable for immutable object

### Field Injection
Field injection involves injecting dependencies directly into class fields.
#### Pros
- We can avoid writing constructors or setters for the dependencies, and let a framework or a container handle the injection for you.
- Make code concise and simple as we only need to declare the fields and annotate them
#### Cons
- Leads to tight coupling between classes, making it harder to refactor or modify dependencies.
- Unable to create immutable classes

Based on those pros and cons of field injection, setter injection, and constructor injection, **constructor injection** is the best practice among them. In the first look, field injection might look like the best choice, but it will be immature. Setter injection makes the class mutable after instantiation, which can lead to issues with state consistency if setters are called at inappropriate times. In contrast, constructor injection might be looking old school, but promises a more testable and fail-proof way of injecting dependency.

<h2>Circular Dependency Injection</h2>

A circular dependency happens when a bean A depends on bean B and vice versa. The common condition of dependency injection must be like this:

<h3>Bean A → Bean B → Bean C</h3>

Spring will create bean C, then create bean B (and inject bean C into it), then create bean A (and inject bean B into it). But, when it comes to circular dependency injection, the condition will be like this.

<h3>Bean A → Bean B → Bean A</h3>

Spring can't decide which of the beans should be created first since they depend on one another. In these cases, Spring will raise a `BeanCurrentlyInCreationException` while loading context. It happens in Spring when using **constructor injection**. If we use other types of injections, we shouldn’t have this problem since the dependencies will be injected when they are needed and not on the context loading. Importantly, we must avoid this condition happened while designing code because it can make things very complicated and difficult to handle.

### Example
```java
@Component
public class DependencyA {

    private DependencyB circB;

    @Autowired
    public DependencyA(DependencyB circB) {
        this.circB = circB;
    }
}
```
```java
@Component
public class DependencyB {

    private DependencyA circA;

    @Autowired
    public DependencyB(DependencyA circA) {
        this.circA = circA;
    }
}
```
If we run the program, it will return like this.
```java
BeanCurrentlyInCreationException: Error creating bean with name 'dependencyA':
Requested bean is currently in creation: Is there an unresolvable circular reference?
```

### Solution
We can implement several ways to deal with circular dependency problem.
#### 1. Use @Lazy
A simple way to break the cycle is by telling Spring to initialize one of the beans lazily. So, instead of fully initializing the bean, it will create a proxy to inject it into the other bean. The injected bean will only be fully created when it’s first needed.
```java
@Component
public class DependencyA {

    private DependencyB circB;

    @Autowired
    public DependencyA(@Lazy DependencyB circB) {
        this.circB = circB;
    }
}
```

#### 2. Use setter/field injection
Eventhough the construction injection shows the best practice in injection, the setter and field injection is better to handle circular dependency injection. With setter/field injection, Spring create the beans but the dependencies aren’t injected until they are needed.
```java
@Component
public class DependencyA {

    private DependencyB circB;

    @Autowired
    public void setCircB(DependencyB circB) {
        this.circB = circB;
    }

    public DependencyB getCircB() {
        return circB;
    }
}
```
```java
@Component
public class DependencyB {

    private DependencyA circA;

    @Autowired
    public void setCircA(DependencyA circA) {
        this.circA = circA;
    }
}
```

#### 3. Use @PostConstruct
Another way to break the cycle is by injecting a dependency using @Autowired on one of the beans and then using a method annotated with @PostConstruct to set the other dependency.

```java
@Component
public class DependencyA {

    @Autowired
    private DependencyB circB;

    @PostConstruct
    public void init() {
        circB.setCircA(this);
    }

    public DependencyB getCircB() {
        return circB;
    }
}
```

```java
@Component
public class DependencyB {

    private DependencyA circA;

    public void setCircA(DependencyA circA) {
        this.circA = circA;
    }
}
```

<h2>Annotations</h2>

<h3>@Configuration</h3>

**Purpose:** Indicates that a class declares one or more @Bean methods and may be processed by the Spring container to generate bean definitions and service requests for those beans at runtime.

**Example:** 
```java
@Configuration
public class AppConfig {
    @Bean
    public EmailService emailService() {
        return new EmailServiceImpl();
    }
}
```
<h3>@Bean</h3>

**Purpose:** To specify a method returns a bean to be managed by Spring context. Spring Bean annotation is usually declared in Configuration classes methods.

**Example:**
```java
    @Bean
    public EmailService emailService() {
        return new EmailServiceImpl();
    }
```
<h3>@ComponentScan</h3>

**Purpose:** To specify the packages that we want to be scanned and create beans.

**Example:**
```java
@Configuration
@ComponentScan(basePackages = "com.lecture7.assignment2")
public class AppConfig {}
```

For a Spring Boot application, the `@SpringBootApplication` annotation used in the main class is a combination of these three annotations:
- @Configuration
- @EnableAutoConfiguration
- @ComponentScan

Therefore, by default, Spring Boot will scan all classes in the same package as the main class and all its sub-packages to discover and initialize the specified beans.

#### Example
We have the project structure as below.

    .
    ├── ...
    ├── com.fsoft.assignment/
    │   ├── AssignmentApplication.java
    │   ├── MyConfig.java
    │   ├── MyBean.java
    │   ├── MyService.java
    │   └── ...
    └── ...

```java
package com.fsoft.assignment;

public class MyBean {

    public String getName() {
        return "My Bean";
    }
}
```
```java
package com.fsoft.assignment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean
    public MyBean myBean() {
        return new MyBean();
    }
}
```
```java
package com.fsoft.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    @Autowired
    private MyBean myBean;

    public void getMyBeanName() {
        System.out.println(myBean.getName());
    }
}
```
```java
package com.fsoft.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class AssignmentApplication {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext =  SpringApplication.run(AssignmentApplication.class, args);

        System.out.println("Contains My Bean: " + applicationContext.containsBean("myBean"));
        System.out.println("Contains My Service Bean: " + applicationContext.containsBean("myService"));
        System.out.println("Contains My Config Bean: " + applicationContext.containsBean("myConfig"));
    }
}
```
The output will be as below.
```
Contains My Bean: true
Contains My Service Bean: true
Contains My Config Bean: true
```

This happened because the @SpringBootApplication already scanned the @Configuration, @EnableAutoConfiguration, and @ComponentScan in every file inside the package as the main class.

But, what if we change the project structure to be like this.

    .
    ├── ...
    ├── com.fsoft
    │   ├── assignment/
    │       ├── AssignmentApplication.java
    │       ├── MyConfig.java
    │       ├── MyBean.java
    │       ├── MyService.java
    │   └── test/
    │       └── TestingService.java
    └── ...

```java
package com.fsoft.test;

@Service
public class TestingService {
}
```
To ensure that the bean TestingService appears in the IoC (Inversion of Control) container, we need to specify it in the `@ComponentScan` so that Spring can recognize it. In `@ComponentScan`, we can use the **basePackages** property to specify a list of Spring packages to scan.

If we use `@ComponentScan` in ComponentScanApplication, we override the default `@ComponentScan` provided by `@SpringBootApplication`. Therefore, we need to specify all the packages that contain the beans that have been defined previously.

```java
@SpringBootApplication
@ComponentScan(basePackages = {"com.fsoft.test", "com.fsoft.assignment"})
public class AssignmentApplication {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext =  SpringApplication.run(AssignmentApplication.class, args);

        System.out.println("Contains My Bean: " + applicationContext.containsBean("myBean"));
        System.out.println("Contains My Service Bean: " + applicationContext.containsBean("myService"));
        System.out.println("Contains My Config Bean: " + applicationContext.containsBean("myConfig"));
        System.out.println("Contains Testing Service Bean: " + applicationContext.containsBean("testingService"));
    }
}
```

Another way than change the `@ComponentScan` from `@SpringBootApplication` is using `@ComponentScan` in `@Configuration` file (MyConfig.java).

```java
@Configuration
@ComponentScan(basePackages = {"com.fsoft.test"})
public class MyConfig {

    @Bean
    public MyBean myBean() {
        return new MyBean();
    }
}
```

<h3>@Component</h3>

**Purpose:** It is used to denote a class as a Component. It is allows Spring to detect and create beans to the class with annotation @Component automatically. Spring framework provides three other specific annotations to be used when marking a class as a Component.
1. `@Service` to specifies that the class holds some business logic or services in it. 
2. `@Repository` to specifies that the class is dealing with CRUD operations.
3. `@Entity` to specifies that the class is responsible to handle user requests and returning the appropriate responses to user.

**Example:**
```java
@Component
public class Employee {
}
```

<h3>@Service</h3>

**Purpose:** It is used with classes that provide some businees functionalities and it can be applied only to classes.

**Example:**
```java
@Service
public class EmailServiceImpl implements EmailService{
    void send(Email email){
        ...
    }
}
```
<h3>@Repository</h3>

**Purpose:** It is used to indicate that the class provides the mechanism for storage, retrieval, update, delete, and search operation on objects.

**Example:**
```java
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>{
}
```
<h3>@Autowired</h3>

**Purpose:** It marks a Constructor, Setter, Propertis, or Config() method as to be autowired that is injecting beans at runtime by Spring Dependency Injection mechanism.

**Example:**
```java
public class EmployeeService {  
    @Autowired
    private EmailService emailService;
}
```

<h3>@Scope</h3>

**Purpose:** It is used to define a bean's scope. It can only be used on the concrete bean class (for annotated components) or the factory method (for @Bean methods).

**Example:**
```java
@Bean
@Scope("singleton")
public Person personSingleton() {
    return new Person();
}
```
<h3>@Qualifier</h3>

**Purpose:** It is useed to eliminate the issue of which bean needs to be injected when Spring finds multiple beans of the same type.

**Example:**
```java
public class EmailService {
    @Autowired
    @Qualifier("emailFormatter")
    private Formatter formatter;
}
...
@Component
@Qualifier("emailFormatter")
public class EmailFormatter implements Formatter {
}
```

<h3>@PropertySource, @Value</h3>

**Purpose:** @PropertySource is used to provide properties file to Spring Environment. This annotation is used with @Configuration classes. Meanwhile, @Value can be used for injecting values into fields in Spring-managed beans, and it can be applied at the field or constructor/method parameter level.

**Example:**
```java
@Configuration
@PropertySource("classpath:db.properties")
@PropertySource("classpath:root.properties")
public class DBConfiguration {

    @Autowired
    Environment env;

    @Value("${APP_NAME}")
    private String appName;

    @Value("${DB_DRIVER_CLASS}")
    private String dbDriverClass;

    @Value("${DB_URL}")
    private String dbUrl;

    @Value("${DB_USERNAME}")
    private String dbUsername;

    @Value("${DB_PASSWORD}")
    private String dbPassword;
}
```
Using the @PropertySource annotation allows us to work with values from properties files with the @Value annotation.

<h3>@PreDestroy, @PostConstruct</h3>

**Purpose:** @PostConstruct is called just after the initialization of bean properties to populating a database (most common purpose). @PreDestroy is called just before Spring removes our bean from the application context to release resources or perform other cleanup tasks, such as closing a database connection, before the bean gets destroyed. Basically, those are another use of Bean lifecycle management. Apart from using initMethod and destroyMethod, we can use @PreDestroy and @PostConstruct for the same purpose.

**Example:**
```java
public class Television {
    @PostConstruct
    public void turnOn(){
        System.out.println("Turn on the television...");
    }

    @PreDestroy
    public void turnOff(){
        System.out.println("Close all programs...");
    }
}
