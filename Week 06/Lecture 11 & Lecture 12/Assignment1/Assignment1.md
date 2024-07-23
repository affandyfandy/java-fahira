# Spring Data JPA

## Lecture 11 - Implementation of Model, JPA, Repositories, Services, and REST APIs
In this assignment, I have to design a project with features as illustrated in the diagram below.

![erd.png](/image/erd.png)

<h3>Create entites</h3>
First, I need to create the department, dept_emp, employee, dept_manager, title, and salary entities and manage their relationships.

- [Department.java](/assignment1/src/main/java/com/lecture11/assignment1/model/Department.java)
- [Employee.java](/assignment1/src/main/java/com/lecture11/assignment1/model/Employee.java)
- [DepartmentManager.java](/assignment1/src/main/java/com/lecture11/assignment1/model/DepartmentManager.java)
- [DepartmentEmployee.java](/assignment1/src/main/java/com/lecture11/assignment1/model/DepartmentEmployee.java)
- [Salary.java](/assignment1/src/main/java/com/lecture11/assignment1/model/Salary.java)
- [Title.java](/assignment1/src/main/java/com/lecture11/assignment1/model/Title.java)

Notably, dept_emp, dept_manager, salary, title are using composite keys. Therefore, I created different classes for each ID.

- [DepartmentManagerId.java](/assignment1/src/main/java/com/lecture11/assignment1/model/composite/DepartmentManagerId.java)
- [DepartmentEmployeeId.java](/assignment1/src/main/java/com/lecture11/assignment1/model/composite/DepartmentEmployeeId.java)
- [SalaryId.java](/assignment1/src/main/java/com/lecture11/assignment1/model/composite/SalaryId.java)
- [TitleId.java](/assignment1/src/main/java/com/lecture11/assignment1/model/composite/TitleId.java)

The key is linked to the entity by implementing the @IdClass annotation.

<h3>Design REST APIs</h3>
I implemented REST APIs for each entity's controller. To manage the source at runtime, I used pagination and DTO objects for several queries.

- [EmployeeController.java](/assignment1/src/main/java/com/lecture11/assignment1/controller/EmployeeController.java)
- [DepartmentController.java](/assignment1/src/main/java/com/lecture11/assignment1/controller/DepartmentController.java)
- [SalaryController.java](/assignment1/src/main/java/com/lecture11/assignment1/controller/SalaryController.java)
- [TitleController.java](/assignment1/src/main/java/com/lecture11/assignment1/controller/TitleController.java)

To enhance control and improve maintainability, I created DTOs and mappers, which I separated into two groups: requests for create and update operations, and responses for read operations.

#### Request DTOs

- [CreateDepartmentDto.java](/assignment1/src/main/java/com/lecture11/assignment1/dto/request/CreateDepartmentDto.java)
- [CreateDepartmentEmployeeDto.java](/assignment1/src/main/java/com/lecture11/assignment1/dto/request/CreateDepartmentEmployeeDto.java)
- [CreateDepartmentManagerDto.java](/assignment1/src/main/java/com/lecture11/assignment1/dto/request/CreateDepartmentManagerDto.java)
- [CreateEmployeeDto.java](/assignment1/src/main/java/com/lecture11/assignment1/dto/request/CreateEmployeeDto.java)

#### Response DTOs

- [ReadDepartmentDto.java](/assignment1/src/main/java/com/lecture11/assignment1/dto/response/ReadDepartmentDto.java)
- [ReadDepartmentEmployeeDto.java](/assignment1/src/main/java/com/lecture11/assignment1/dto/response/ReadDepartmentEmployeeDto.java)
- [ReadDepartmentManagerDto.java](/assignment1/src/main/java/com/lecture11/assignment1/dto/response/ReadDepartmentManagerDto.java)
- [ReadEmployeeDto.java](/assignment1/src/main/java/com/lecture11/assignment1/dto/response/ReadEmployeeDto.java)
- [SalaryDto.java](/assignment1/src/main/java/com/lecture11/assignment1/dto/response/SalaryDto.java)
- [TitleDto.java](/assignment1/src/main/java/com/lecture11/assignment1/dto/response/TitleDto.java)

<h3>API Endpoints</h3>

#### Employee Management

| **Endpoint**                          | **Method** | **Description**                                              | **Request Body**       | **Query Parameters**                                                   | **Responses**                          |
|---------------------------------------|------------|--------------------------------------------------------------|------------------------|-----------------------------------------------------------------------|---------------------------------------|
| `/api/v1/employee`                    | GET        | Retrieve all employees with pagination and sorting.         | None                   | `page` (default: `0`), `size` (default: `10`), `sort` (default: `employeeNo,asc`) | 200 OK: List of employees              |
| `/api/v1/employee`                    | POST       | Add a new employee.                                         | `CreateEmployeeDto`    | None                                                                  | 202 Accepted: Newly created employee   |
| `/api/v1/employee/{id}`               | PUT        | Update an existing employee by ID.                          | `CreateEmployeeDto`    | None                                                                  | 202 Accepted: Updated employee         |
| `/api/v1/employee/{id}`               | DELETE     | Delete an employee by ID.                                   | None                   | None                                                                  | 202 Accepted: Success message          |
| `/api/v1/employee/{id}/salary`        | POST       | Update the salary of an employee.                           | `SalaryDto`            | None                                                                  | 202 Accepted: Employee with updated salary |
| `/api/v1/employee/{id}/title`         | POST       | Update the title of an employee.                            | `TitleDto`             | None                                                                  | 202 Accepted: Employee with updated title |
| `/api/v1/employee/search`             | GET        | Search for employees based on various criteria.             | None                   | `firstName`, `lastName`, `gender`, `hireDate`, `birthDate`, `operation` (default: `EQUAL`), `page` (default: `0`), `size` (default: `10`), `sort` (default: `employeeNo,asc`) | 200 OK: List of employees matching criteria |

#### Salary Management

| **Endpoint**                          | **Method** | **Description**                                              | **Request Body**       | **Query Parameters** | **Responses**                          |
|---------------------------------------|------------|--------------------------------------------------------------|------------------------|----------------------|---------------------------------------|
| `/api/v1/salary`                      | POST       | Create a new salary entry.                                 | `SalaryDto`            | None                 | 202 Accepted: Newly created salary    |
| `/api/v1/salary`                      | GET        | Retrieve all salary entries.                                | None                   | None                 | 200 OK: List of salaries              |

#### Title Management

| **Endpoint**                          | **Method** | **Description**                                              | **Request Body**       | **Query Parameters** | **Responses**                          |
|---------------------------------------|------------|--------------------------------------------------------------|------------------------|----------------------|---------------------------------------|
| `/api/v1/title`                       | POST       | Create a new title entry.                                  | `TitleDto`             | None                 | 202 Accepted: Newly created title     |
| `/api/v1/title`                       | GET        | Retrieve all title entries.                                 | None                   | None                 | 200 OK: List of titles                |


## Lecture 12 - Dynamic Filter for Employee Search

### Overview

The Employee Search feature allows users to query employee records using various dynamic filters.

### Endpoints
`GET /search`

This endpoint retrieves a list of employees based on the specified search criteria.

### Query Parameters

The searchEmployees endpoint accepts the following query parameters:

- <b>firstName (optional)</b>: Filter employees by their first name.
- <b>lastName (optional)</b>: Filter employees by their last name.
- <b>gender (optional)</b>: Filter employees by their gender.
- <b>hireDate (optional)</b>: Filter employees by their hire date. The date should be in the yyyy-MM-dd format.
- <b>birthDate (optional)</b>: Filter employees by their birth date (yyyy-MM-dd).
- <b>operation (optional)</b>: Specifies the type of search operation to perform. Default is EQUAL. Other values include:

```
    EQUAL
    GREATER_THAN
    LESS_THAN
    LIKE
```
- <b>page (optional)</b>: The page number to retrieve. Default is 0.
- <b>size (optional)</b>: The number of records per page. Default is 10.
- <b>sort (optional)</b>: Sorting criteria in the format `field,direction`. Default is employeeNo,asc. direction can be asc for ascending or desc for descending

### Implementation
<h4>1. Search Criteria Construction</h4>

The `searchEmployees` method constructs a list of SearchCriteria objects. Each object represents a filter condition and includes:

- key: The field to be filtered (e.g., firstName, lastName).
- operation: The type of filter operation (e.g., EQUAL, LIKE).
- value: The value used for filtering.

<h4>2. Pagination and Sorting</h4>

Pagination and sorting used to manage large returns and provide sorting options.

- Pagination: Controlled by the page and size parameters. Defaults to the first page with 10 records per page.
- Sorting: Determined by the sort parameter. The parameter is split into field and direction (e.g., hireDate,desc).

<h4>3. Query Execution</h4>

The employeeService.searchEmployees method processes the list of SearchCriteria and applies pagination and sorting. It returns a `Page<Employee>` object containing the filtered, sorted, and paginated employee records.

<h4>4. DTO Mapping</h4>

The `Page<Employee>` is mapped to a list of ReadEmployeeDto objects using employeeMapper.toListDto to convert data into a format suitable for the response.

### Example Requests

<h4>Example 1: Search by First Name</h4>

```
GET /search?firstName=John
```
Explanation: Retrieves all employees with the first name "John".

<h4>Example 2: Search by Last Name and Hire Date</h4>

```
GET /search?lastName=Doe&hireDate=2023-01-15
```
Explanation: Retrieves all employees with the last name "Doe" hired on January 15, 2023.
