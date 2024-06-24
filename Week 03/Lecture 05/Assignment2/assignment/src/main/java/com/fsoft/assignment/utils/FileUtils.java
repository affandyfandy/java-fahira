package com.fsoft.assignment.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fsoft.assignment.model.Employee;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class FileUtils {
    public static List<Employee> readEmployeeManual(String filePath){
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true; 
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(",");
                if (values.length == 5) {
                    var employee = new Employee();
                    employee.setId(values[0]);
                    employee.setName(values[1]);
                    employee.setDateOfBirth(DateUtils.parseDate(values[2]));
                    employee.setAddress(values[3]);
                    employee.setDepartment(values[4]);
                    employees.add(employee);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
        return employees;
    }

    public static List<Employee> readEmployeeFromCSV(String filePath){
        List<Employee> employees = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] values;
            boolean isFirstLine = true; 
            while ((values = reader.readNext()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                if (values.length == 5) { 
                    var employee = new Employee();
                    employee.setId(values[0]);
                    employee.setName(values[1]);
                    employee.setDateOfBirth(DateUtils.parseDate(values[2]));
                    employee.setAddress(values[3]);
                    employee.setDepartment(values[4]);
                    employees.add(employee);
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.out.println("Error reading CSV file with OpenCSV: " + e.getMessage());
        }
        return employees;
    }

    // public static void writeEmployeesToCSV(List<Employee> employees) {
    //     try (FileWriter writer = new FileWriter("employees.csv")) {
    //         writer.append("ID,Name,DateOfBirth,Address,Department\n");
    //         for (Employee employee : employees) {
    //             writer.append(employee.print()).append("\n");
    //         }
    //         System.out.println("Employees exported to CSV successfully.");
    //     } catch (IOException e) {
    //         System.out.println("Error writing to CSV file: " + e.getMessage());
    //     }
    // }
}