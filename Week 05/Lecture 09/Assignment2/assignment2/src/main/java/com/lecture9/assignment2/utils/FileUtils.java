package com.lecture9.assignment2.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.lecture9.assignment2.model.Employee;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class FileUtils {

	private static File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

    public static List<Employee> readEmployeeFromCSV(MultipartFile multipartFile) throws IOException{
        File file = convertMultiPartToFile(multipartFile);
        List<Employee> employees = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            String[] values;
            boolean isFirstLine = true; 
            while ((values = reader.readNext()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                if (values.length == 6) { 
                    var employee = new Employee();
                    employee.setId(values[0]);
                    employee.setName(values[1]);
                    employee.setDob(DateUtils.parseDate(values[2]));
                    employee.setAddress(values[3]);
                    employee.setDepartment(values[4]);
                    employee.setSalary(Integer.parseInt(values[5]));
                    employees.add(employee);
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.out.println("Error reading CSV file with OpenCSV: " + e.getMessage());
        }
        return employees;
    }
}