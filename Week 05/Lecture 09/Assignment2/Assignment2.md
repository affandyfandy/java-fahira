<h2>Snapshots</h2>

### Init database
```sql
CREATE TABLE employee (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    dob DATE,
    address VARCHAR(255),
    department VARCHAR(255),
    salary int
);
```

### Add maven dependency
See [pom.xml](/Week%2005/Assignment2/assignment2/pom.xml) in the project.

### Configure properties
See [application.properties](/Week%2005/Assignment2/assignment2/src/main/resources/application.properties) in the project.

### Result
1. **Add new employee**
![add](/Week%2005/Assignment2/assignment2/image/add-employee.png)

2. **View employee's detail**
![detail](/Week%2005/Assignment2/assignment2/image/detail-employee.png)

3. **View list employees**
![list-employees](/Week%2005/Assignment2/assignment2/image/list-employee.png)

4. **Delete employee**
![delete](/Week%2005/Assignment2/assignment2/image/delete-employee.png)

5. **Update employee**
![update](/Week%2005/Assignment2/assignment2/image/update-employee.png)