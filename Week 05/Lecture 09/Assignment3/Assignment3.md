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
See [pom.xml](/Week%2005/Assignment3/assignment3/pom.xml) in the project.

### Configure properties
See [application.properties](/Week%2005/Assignment3/assignment3/src/main/resources/application.properties) in the project.

### Create Employee rest controller
See [EmployeeRestController.java](/Week%2005/Assignment3/assignment3/src/main/java/com/lacture9/assignment3/controller/EmployeeRestController.java) to manage PDF downloader.

### Result
1. **View all employees**
![list-employee.png](/Week%2005/Assignment3/assignment3/image/list-employee.png)

2. **Generate PDF**
![pdf.png](/Week%2005/Assignment3/assignment3/image/pdf.png)

