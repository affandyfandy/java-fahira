package com.fsoft.assignment.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fsoft.assignment.model.Employee;
import com.fsoft.assignment.repository.EmployeeRepository;
import com.fsoft.assignment.utils.FileUtils;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/v1/employee")
@AllArgsConstructor
public class EmployeeController {

    @Autowired
    private final EmployeeRepository employeeRepository;

    // retrieve all employees
    @GetMapping
    public ResponseEntity<List<Employee>> listAllEmployee(){
        List<Employee> listEmployee= employeeRepository.findAll();
        if(listEmployee.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listEmployee);
    }

    // add new employee
    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employeeForm) {
        Optional<Employee> employee = employeeRepository.findById(employeeForm.getId());
        if(employee.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(employeeRepository.save(employeeForm));
    }

    // get employee by id
    @GetMapping(value = "/{id}")
    public ResponseEntity<Employee> findEmployee(@PathVariable("id") String id) {
        var employee = employeeRepository.findById(id);
        if(employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        }
        return ResponseEntity.notFound().build();
    }

    // update employee data
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") String id,
                                                 @RequestBody Employee employeeForm) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            employee.setAddress(employeeForm.getAddress());
            employee.setDateOfBirth(employeeForm.getDateOfBirth());
            employee.setDepartment(employeeForm.getDepartment());
            employee.setName(employeeForm.getName());
            Employee updatedEmployee = employeeRepository.save(employee);
            return ResponseEntity.ok(updatedEmployee);
        }
        return ResponseEntity.notFound().build();
    }

    // delete employee by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Employee> uploadData(@PathVariable(value = "id") String id){
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isPresent()){
            Employee employee = employeeOpt.get();
            employeeRepository.delete(employee);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // upload employee
    @PostMapping("/upload")
    public ResponseEntity<List<Employee>> uploadData(@RequestPart("file") MultipartFile file) throws IOException{
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<Employee> listEmployee = FileUtils.readEmployeeFromCSV(file);
        employeeRepository.saveAll(listEmployee);
        return ResponseEntity.ok(listEmployee);
    }

    // query department
    @GetMapping("/department")
    public ResponseEntity<List<Employee>> filterByDepartmnet(@RequestParam("query") String dept) {
        List<Employee> listEmployee = employeeRepository.findAll();
        List<Employee> listEmployeeDept = new ArrayList<>();
        for (Employee e : listEmployee){
            if (e.getDepartment().equals(dept)){
                listEmployeeDept.add(e);
            }
        }
        if (listEmployeeDept.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listEmployeeDept);
    }
    
    
}
