<h2>Console Menu</h2>

### Project Definition
The project is to create an employee management system with features to import and export data from CSV file, add new employee, and filter employee's details.

### Project Structure
```
app
├── src/main/java/org/example
│   ├── App.java
│   ├── control/
│   │   └── AppManager.java
│   ├── model/
│   │   └── Employee.java
│   └── utils/
│       ├── DateUtils.java
│       └── FileUtils.java
└── build.gradle
```

### Run the Application
1. Go to the root directory
```
$ cd code
```
2. Build the project
```
$ ./gradlew build
```
3. Run the project
```
$ ./gradlew run
```