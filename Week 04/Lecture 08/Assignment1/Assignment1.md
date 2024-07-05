<h2>Create a Spring project</h2>

### What we'll need
 - A favorite text editor or IDE
 - [JDK 6][jdk] or later
 - [Maven 3.0][mvn] or later

[jdk]: http://www.oracle.com/technetwork/java/javase/downloads/index.html
[mvn]: http://maven.apache.org/download.cgi

### Getting started
Go to [start.spring.io](https://start.spring.io).

### Add maven dependencies
See [pom.xml](/Assignment1/assignment1/pom.xml) in project.

### Project structure
```bash
assignment1
├── .mvn/wrapper/
│   └── maven-wrapper.properties
├── src/main/
│   ├── java/com/lecture8/assignment1/
│   │   ├── controller/
│   │       └── BaseController.java
│   │   └── Assignment1Application.java
│   └── resources/
│       └── application.properties
├── .gitignore
├── mvnw
├── mvnw.cmd
└── pom.xml
```

### Create a simple controller
Now we can create a web controller for a simple Spring project.
```java
@Controller
public class BaseController {
    
    @GetMapping("/")
    public String home() {
        return "Welcome!";
    }

}
```

### Check the application class
Here we need to check the main method is detected.
```java
@SpringBootApplication
public class Assignment1Application {

	public static void main(String[] args) {
		SpringApplication.run(Assignment1Application.class, args);
	}

}
```
- The `main()` method uses Spring Boot's `SpringApplication.run()` method to launch an application.

- The `run()` method returns an `ApplicationContext` and this application then retrieves all the beans that were created either by the app or were automatically added.

### Run the project
To run the application, execute:
```sh
$ mvn package spring-boot:run
```
The output shown on the terminal will be like this.
```sh
[INFO] Scanning for projects...
[INFO] 
[INFO] ----------------------< com.lecture8:assignment1 >----------------------
[INFO] Building assignment1 0.0.1-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ assignment1 ---
[INFO] Copying 1 resource from src/main/resources to target/classes
[INFO] Copying 0 resource from src/main/resources to target/classes
[INFO] 
[INFO] --- compiler:3.13.0:compile (default-compile) @ assignment1 ---
[INFO] Recompiling the module because of added or removed source files.
[INFO] Compiling 2 source files with javac [debug parameters release 17] to target/classes
...
2024-07-05T12:42:50.041+07:00  INFO 9452 --- [assignment1] [  restartedMain] c.l.assignment1.Assignment1Application   : Started Assignment1Application in 3.429 seconds (process running for 4.114)
```
To access the application, we can go through [http://localhost:8080](http://localhost:8080).
/localhost:8080](http://localhost:8080).
