package com.lecture10.assignment1.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lecture10.assignment1.dto.EmployeeDTO;
import com.lecture10.assignment1.service.EmployeeService;
import com.lecture10.assignment1.utils.FileUtils;

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
    private EmployeeService employeeService;

    // retrieve all employees
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> listAllEmployee(){
        return ResponseEntity.ok(employeeService.listAllEmployee());
    }

    // add new employee
    @PostMapping
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.save(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("New employee has been added");
    }

    // get employee by id
    @GetMapping(value = "/{id}")
    public ResponseEntity<EmployeeDTO> findEmployee(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(employeeService.findById(id));
    }

    // update employee data
    @PutMapping(value = "/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable(value = "id") String id, @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeService.update(employeeDTO, id));
    }

    // delete employee by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable(value = "id") String id){
        employeeService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Employee has been deleted");
    }

    // upload employee
    @PostMapping("/upload")
    public ResponseEntity<String> uploadData(@RequestPart("file") MultipartFile file) throws IOException{
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<EmployeeDTO> listEmployee = FileUtils.readEmployeeFromCSV(file);
        employeeService.saveAll(listEmployee);
        return ResponseEntity.status(HttpStatus.CREATED).body("New employees has been added");
    }

    // query department
    @GetMapping("/department")
    public ResponseEntity<List<EmployeeDTO>> filterByDepartmnet(@RequestParam("query") String dept) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.findByDepartment(dept));
    }
}