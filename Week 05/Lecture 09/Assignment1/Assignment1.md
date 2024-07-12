<h2>Create a Spring MVC project</h2>

### Project structure
```bash
assignment1
├── .mvn/wrapper/
│   └── maven-wrapper.properties
├── src/main/
│   ├── java/com/lecture9/assignment1/
│   │   ├── controller/
│   │   │   └── EmployeeController.java
│   │   ├── model/
│   │   │   └── Employee.java
│   │   ├── repository/
│   │   │   └── EmployeeRepository.java
│   │   ├── service/
│   │   │   ├── impl/
│   │   │   │   └─ EmployeeServiceImpl.java
│   │   │   └── EmployeeService.java
│   │   └── Assignment1Application.java
│   └── resources/
│       ├── static/
│       │   └── index.html
│       ├── templates/employees/
│       │   ├── employee-form.html
│       │   └── list-employees.html
│       └── application.properties
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
├── run.bat
└── run.sh
```

### Init database
```sql
CREATE TABLE `employee` (
    `id` int NOT NULL AUTO_INCREMENT,
    `first_name` varchar(45) DEFAULT NULL,
    `last_name` varchar(45) DEFAULT NULL,
    `email` varchar(45) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `employee` VALUES
(1,'Leslie','Andrews','leslie@luv2code.com'),
(2,'Emma','Baumgarten','emma@luv2code.com'),
(3,'Avani','Gupta','avani@luv2code.com'),
(4,'Yuri','Petrov','yuri@luv2code.com'),
(5,'Juan','Vega','juan@luv2code.com');
```

### Configure properties
See [applicaiton.properties](/Week%2005/Assignment1/assignment1/src/main/resources/application.properties).

### Run the application
We can run the application through the **Run** on the main function or by running these commands.

Run mvn clean install to build the application.
```sh
$ mvn clean install
```
Run the Spring Boot application.
```sh
$ mvn spring-boot:run
```

### Snapshots
1. List Employees
![list-employees.png](/Week%2005/Assignment1/assignment1/image/list-employees.png)

2. Add New Employee
![add-employee.png](/Week%2005/Assignment1/assignment1/image/add-employee.png)

3. Update Employee
![update.png](/Week%2005/Assignment1/assignment1/image/update.png)

4. Delete Employee
![delete.png](/Week%2005/Assignment1/assignment1/image/delete.png)