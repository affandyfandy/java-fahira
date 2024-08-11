# Snapshots of unit testing

Unit testing in Spring involves testing individual components of spring application in isolation to ensure they work as expected. In this project, unit tests are focused on:

### Service Layer
Testing business logic and service methods. Mocks are often used for dependencies to ensure tests focus solely on the service being tested.

### Repository Layer
Testing data access logic and repository methods. Mocking or an in-memory database can be used to simulate interactions with the database.

### Controller Layer
Testing web controllers to ensure they handle HTTP requests and responses correctly. MockMvc is commonly used to simulate HTTP requests and validate responses.

### Utilities and Helpers
Testing utility classes and helper methods to ensure they perform their intended functions correctly.

Unit tests in Spring are typically written using **JUnit and Mockito**, allowing for easy mocking and verification of dependencies. Test annotations such as `@Mock`, `@InjectMocks`, `@Test` are commonly used to set up and run tests effectively.

## JaCoCo Analysis

The JaCoCo coverage report provides insights into the percentage of code covered by unit tests.

Ensure that all critical paths and edge cases are covered to achieve high test coverage.

![image.png](/Week%2009/Lecture%2017%20&%20Lecture%2018/img/Screen%20Shot%202024-08-10%20at%2023.59.09.png)

From what I have achieved, I reached **77%** code coverage with unit tests. The main focus was on testing the service or business side of the application, with only a small portion dedicated to utility testing.

## SonarQube Analysis
SonarQube provides an analysis of code quality, identifying bugs, vulnerabilities, and code smells.

Review and address the issues reported to maintain clean and maintainable code.

![image.png](/Week%2009/Lecture%2017%20&%20Lecture%2018/img/Screen%20Shot%202024-08-11%20at%2001.04.40.png)

The SonarQube results showed only **73%** total code coverage. Noteworthy is the **reliability score of 17%** and **304 open issues in maintainability**, indicating that further improvements are needed.

