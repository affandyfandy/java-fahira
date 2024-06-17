package org.example.control;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.example.model.Employee;
import org.example.utils.DateUtils;
import org.example.utils.FileUtils;

public class AppManager {
    private static AppManager instance;
    private static Scanner input;
    private List<Employee> employees;
    
    private AppManager(){
        employees = new ArrayList<>();
        input = new Scanner(System.in); 
    }

    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    private void menu(){
        System.out.println("===============================================");
        System.out.println("Select menu:");
        System.out.println("0 - Exit");
        System.out.println("1 - Select File, Import Data");
        System.out.println("2 - Add New Employee");
        System.out.println("3 - Filter Employee");
        System.out.println("4 - Export Filtered Employees");
        System.out.print("Choose an option: ");
    }

    private void importData(){
        System.out.println("Select CSV reading method:");
        System.out.println("1 - Manual");
        System.out.println("2 - OpenCSV");
        System.out.print("Choose an option: ");
        try{
            int opt = Integer.parseInt(input.nextLine());
            if (opt == 1){
                System.out.print("Enter the file path: ");
                String filePath = input.nextLine();
                manualImportData(filePath);
            }
            else if (opt == 2){
                System.out.print("Enter the file path: ");
                String filePath = input.nextLine();
                openCSVImportData(filePath);
            }
            else{
                System.out.println("Invalid input! Please try again.");
            }
        }
        catch (NumberFormatException e){
            System.out.println("Invalid input! Please try again.");
        }
    }

    private void manualImportData(String path){
        if (path.isEmpty()) {
            System.out.println("File path can't be empty. Please try again.");
            return;
        }
        List<Employee> importedEmployees = FileUtils.readEmployeeManual(path);
        employees.addAll(importedEmployees);
        if (importedEmployees.size() == 0){
            System.out.println("No employee imported from the file.");
        }
        else if (importedEmployees.size() == 1){
            System.out.println("Imported " + importedEmployees.size() + " employee successfully.");
        } 
        else {
            System.out.println("Imported " + importedEmployees.size() + " employees successfully.");
        }
    }

    private void openCSVImportData(String path){
        if (path.isEmpty()) {
            System.out.println("File path can't be empty. Please try again.");
            return;
        }
        List<Employee> importedEmployees = FileUtils.readEmployeeFromCSV(path);
        employees.addAll(importedEmployees);
        if (importedEmployees.size() == 0){
            System.out.println("No employee imported from the file.");
        }
        else if (importedEmployees.size() == 1){
            System.out.println("Imported " + importedEmployees.size() + " employee successfully.");
        } 
        else {
            System.out.println("Imported " + importedEmployees.size() + " employees successfully.");
        }
    }

    private void addEmployee(){
        System.out.print("Enter ID: ");
        String id = input.nextLine();

        if (!checkUniqueness(id)){
            System.out.println("Employee with ID " + id + " already exists.");
            return;
        }
    
        System.out.print("Enter Name: ");
        String name = input.nextLine();
    
        System.out.print("Enter Date of Birth (d/M/yyyy): ");
        String dob = input.nextLine();

        LocalDate dateOfBirth;
        try {
            dateOfBirth = DateUtils.parseDate(dob);
        }
        catch (DateTimeParseException e){
            System.out.println("Insert new employee failed. Please try again.");
            return;
        }

        System.out.print("Enter Address: ");
        String address = input.nextLine();
    
        System.out.print("Enter Department: ");
        String department = input.nextLine();

        Employee newEmployee = new Employee(id, name, dateOfBirth, address, department);
        employees.add(newEmployee);
        System.out.println("Employee inserted successfully!");
    }

    private boolean checkUniqueness(String id){
        for (Employee employee : employees){
            if (employee.getId().equalsIgnoreCase(id)){
                return false;
            }
        }
        return true;
    }

    private void filterEmployee() {
        System.out.println("Filter by:");
        System.out.println("0 - All Data");
        System.out.println("1 - Name");
        System.out.println("2 - ID");
        System.out.println("3 - Date of Birth (year)");
        System.out.println("4 - Department");
        System.out.print("Choose an option: ");
        try {
            int cmd = Integer.parseInt(input.nextLine());
            List<Employee> filteredEmployees = new ArrayList<>();

            if (cmd == 0){
                filteredEmployees = employees;
            }
            else if (cmd == 1){
                System.out.print("Enter name pattern: ");
                String filteredByName = input.nextLine();
                for (Employee employee : employees){
                    if (employee.getName().contains(filteredByName)){
                        filteredEmployees.add(employee);
                    }
                }
            }
            else if (cmd == 2){
                System.out.print("Enter ID pattern: ");
                String filteredId = input.nextLine();
                for (Employee employee : employees){
                    if (employee.getId().contains(filteredId)){
                        filteredEmployees.add(employee);
                    }
                }
            }
            else if (cmd == 3){
                System.out.print("Enter year of birth pattern: ");
                int filteredYear = input.nextInt();
                for (Employee employee : employees){
                    if (employee.getDateOfBirth().getYear() == filteredYear){
                        filteredEmployees.add(employee);
                    }
                }
            }
            else if (cmd == 4){
                System.out.print("Enter department pattern: ");
                String filteredDept = input.nextLine();
                for (Employee employee : employees){
                    if (employee.getDepartment().equalsIgnoreCase(filteredDept)){
                        filteredEmployees.add(employee);
                    }
                }
            }
            else{
                System.out.println("Invalid input! Please try again.");
                return;
            }
            for (Employee employee : filteredEmployees) {
                System.out.println(employee.print());
            }
            System.out.println("Showed " + filteredEmployees.size() + " employees successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please try again.");
        }
    }

    private void exportFilteredEmployee() {
        FileUtils.writeEmployeesToCSV(employees);
    }

    private void systemExit(){
        System.out.println("Thank you for using the console menu.");
        System.exit(0);
    }

    public void start(){
        while (true) {
            menu();
            try {
                int cmd = Integer.parseInt(input.nextLine());
                if (cmd == 0){
                    systemExit();
                    input.close();
                }
                else if (cmd == 1){
                    importData();
                }
                else if (cmd == 2){
                    addEmployee();
                }
                else if (cmd == 3){
                    filterEmployee();
                }
                else if (cmd == 4){
                    exportFilteredEmployee();
                }
                else{
                    System.out.println("Invalid input! Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please try again.");
            }
        }
    }
}
