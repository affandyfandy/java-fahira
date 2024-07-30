<h2>Snapshots</h2>

### Getting started
![init-project.png](/Assignment2/assignment2/image/init.png)

### Add maven dependencies
See [pom.xml](/Assignment2/assignment2/pom.xml) in project.

### Init database
Refer to [this](/Assignment2/assignment2/src/main/resources/scheme.sql) sql code.

### Configure properties
See [application.properties](/Assignment2/assignment2/src/main/resources/application.properties) in project.

### Create model
[Employee.java](/Assignment2/assignment2/src/main/java/com/lecture8/assignment2/entity/Employee.java)

### Create jpa repository
[EmployeeRepository.java](/Assignment2/assignment2/src/main/java/com/lecture8/assignment2/repository/EmployeeRepository.java)

### Create controller
[EmployeeController.java](/Assignment2/assignment2/src/main/java/com/lecture8/assignment2/controller/EmployeeController.java)

### Route testing
1. **Add employee**
![add-employee.png](/Assignment2/assignment2/image/add-employee.png)

2. **Get all employees**
![view-all-employee.png](/Assignment2/assignment2/image/view-all-employee.png)

3. **Find employee by Id**
![get-employee-by-id.png](/Assignment2/assignment2/image/get-employee-by-id.png)

4. **Update employee**
![update.png](/Assignment2/assignment2/image/update.png)

5. **Delete employee**
![delete.png](/Assignment2/assignment2/image/delete.png)

<h2> Spring JDBC vs. Spring Data JDBC (update) </h2>

## Spring JDBC

Spring JDBC is the direct approach provided by the Spring Framework. It simplifies database access by providing API, connection management, and exception handling. It leverages the JDBC API but reduces boilerplate code by efficiently managing repetitive tasks. It handled by providing parameterized queries and mapping query results to Java objects.

### Pros
- Direct control over SQL queries. Spring JDBC allows us to write SQL queries expicitly and it makes us a complete control over the database interactions
- Minimal overhead between application and the database whcih lead to the potential better performance
- Offers the simplicity that suitable for small projects without complexity of an ORM

### Cons
- More boilerplate code. Even with the simplifications of JdbcTemplate, we still need to write and manage SQL queries and map results manually
- Higher risk of SQL injection
- Handling relationships and complex queries can become impractical as the complexity of the domain level grows

## Spring Data JDBC

Spring Data JBDC provides the Spring Data abstraction that value simplicity. It offers support for mapping domain objects to database tables using annotations and conventions. Other than mapping Java objects to database tables, it also provides easy-to-use repository interfaces for common CRUD operations.

### Pros
- Reduce boilerplate code by providing repository interfaces that automatically handle commos CRUD operations
- It automates the amapping of domain objects to database tables using annotations
- Spring Data JDBC derives SQL queries from method names and uses query annotations (@Query) for custom queries, enhancing productivity

### Cons
- It may lack support for complex queries involving multiple joins or advanced SQL constructs
- It doesn't support transactions which can be a problem in some cases

### Summary

| Feature                             | Spring JDBC                               | Spring Data JDBC                          |
|-------------------------------------|-------------------------------------------|-------------------------------------------|
| **Model Class**                     | Uses model classes                        | Uses POJO classes                         |
| **Mandatory Getters/Setters**       | Yes                                       | Not mandatory                             |
| **Parameterized Constructor**       | Helpful                                   | May not be as helpful                     |
| **Annotation Requirement**          | No specific annotations required          | Uses `@Table`, `@ID`, `@Column` annotations are helpful|
| **Data Access Layer**               | Interfaces and implementations            | Simplified, omits lazy loading, cache implementation, etc. which is there in JPA             |

### Implementation

Let say we have a model class named Employee with attributes id and name.

**Spring JDBC**
```java
public class Employee {
    int id;
    String name;
}
```
```java
public interface EmployeeService{
    public void findByName(String name); 
    ...
}
```
```java
public class EmployeServiceImpl implements EmployeeService {

    private DataSource dataSource;
 
    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }
 
    public void findByName(Strng name){
        String sql = "SELECT * FROM employee WHERE name = ?";
        Employee employee = jdbcTemplate.queryForObject(sql, new Object[]{name}, new BeanPropertyRowMapper<>(Employee.class));
    }
    ...
}
```

**Spring Data JDBC**
```java
@Data
@Table("employee")
public class Employee {

    @Id
    private Long id;
    private String name;
}
```
```java
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
 
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, int> {
    List<Employee> findByName(@Param("name") String name);
}
```

### Reflection

After doing more research about Spring JDBC and Spring Data JDBC, I think it's better to implement Spring JDBC for assignment 2. In my current implementation, I included the Spring Data JDBC dependency but used it incorrectly. I defined the SQL query directly in the repository file instead of using the interface provided by Spring Data JDBC. In essence, I was using Spring Data JDBC annotations but implementing queries in the way Spring JDBC does. Therefore, it's best to remove the Spring Data JDBC annotations and manage the database connection using Spring JDBC's approach.