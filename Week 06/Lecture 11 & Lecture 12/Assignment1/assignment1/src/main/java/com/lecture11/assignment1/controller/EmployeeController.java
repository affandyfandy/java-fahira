package com.lecture11.assignment1.controller;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RestController;

import com.lecture11.assignment1.dto.EmployeeMapper;
import com.lecture11.assignment1.dto.SalaryMapper;
import com.lecture11.assignment1.dto.TitleMapper;
import com.lecture11.assignment1.dto.request.CreateEmployeeDto;
import com.lecture11.assignment1.dto.response.ReadEmployeeDto;
import com.lecture11.assignment1.dto.response.SalaryDto;
import com.lecture11.assignment1.dto.response.TitleDto;
import com.lecture11.assignment1.model.Employee;
import com.lecture11.assignment1.model.Salary;
import com.lecture11.assignment1.model.Title;
import com.lecture11.assignment1.service.EmployeeService;
import com.lecture11.assignment1.specification.SearchCriteria;
import com.lecture11.assignment1.specification.SearchOperation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private SalaryMapper salaryMapper;

    @Autowired
    private TitleMapper titleMapper;
    
    private Sort.Direction getSortDirection(String direction) {
        if (direction.equalsIgnoreCase("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    @GetMapping
    public ResponseEntity<List<ReadEmployeeDto>> getAllEmployees(@RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size,
                @RequestParam(defaultValue = "employeeNo,asc") String sort) {
        List<Order> orders = new ArrayList<Order>();
        String[] sortArr = sort.split(",");
        orders.add(new Order(getSortDirection(sortArr[1]), sortArr[0]));
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        Page<Employee> findEmployees = employeeService.findAll(pagingSort);
        List<ReadEmployeeDto> listEmployee = employeeMapper.toListDto(findEmployees.getContent());
        return ResponseEntity.status(HttpStatus.OK).body(listEmployee);
    }
    
    @PostMapping
    public ResponseEntity<Employee> addNewEmployee(@RequestBody CreateEmployeeDto employeeDto){
        Employee newEmployee = employeeMapper.toEntity(employeeDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeService.add(newEmployee)); 
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ReadEmployeeDto> updateEmployee(@PathVariable int id, @RequestBody CreateEmployeeDto employeeDto) {
        Employee updatedEmployee = employeeMapper.toEntity(employeeDto);
        ReadEmployeeDto employee = employeeMapper.toDto(employeeService.update(updatedEmployee, id));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id){
        Employee employee = employeeService.findById(id);
        if (employee != null){
            employeeService.delete(employee);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Delete successful!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't find the employee");
    }

    @PostMapping("/{id}/salary")
    public ResponseEntity<?> updateEmployeeSalary(@PathVariable("id") int id, @RequestBody SalaryDto salaryDto){
        if (Integer.toString(id).equals(Integer.toString(salaryDto.getEmployeeNo())) == false){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Employee employee = employeeService.findById(id);
        Salary salary = salaryMapper.toEntity(salaryDto);
        if (employee != null){
            employeeService.updateSalary(employee, salary);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeMapper.toDto(employee));
    }

    @PostMapping("/{id}/title")
    public ResponseEntity<?> updateEmployeeTitle(@PathVariable("id") int id, @RequestBody TitleDto titleDto){
        if (Integer.toString(id).equals(Integer.toString(titleDto.getEmployeeNo())) == false){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Employee employee = employeeService.findById(id);
        Title title = titleMapper.toEntity(titleDto);
        if (employee != null){
            employeeService.updateTitle(employee, title);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeMapper.toDto(employee));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ReadEmployeeDto>> searchEmployees(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) LocalDate hireDate,
            @RequestParam(required = false) LocalDate birthDate,
            @RequestParam(defaultValue = "EQUAL") String operation,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "employeeNo,asc") String sort) {

        List<SearchCriteria> params = new ArrayList<>();
        if (firstName != null) params.add(new SearchCriteria("firstName", determineOperation(operation, "FIRST_NAME"), firstName));
        if (lastName != null) params.add(new SearchCriteria("lastName", determineOperation(operation, "LAST_NAME"), lastName));
        if (gender != null) params.add(new SearchCriteria("gender", determineOperation(operation, "GENDER"), gender));
        if (hireDate != null) params.add(new SearchCriteria("hireDate", determineOperation(operation, "HIRE_DATE"), hireDate));
        if (birthDate != null) params.add(new SearchCriteria("birthDate", determineOperation(operation, "BIRTH_DATE"), birthDate));

        List<Order> orders = new ArrayList<Order>();
        String[] sortArr = sort.split(",");
        orders.add(new Order(getSortDirection(sortArr[1]), sortArr[0]));
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Page<Employee> employees = employeeService.searchEmployees(params, pageable);
        List<ReadEmployeeDto> employeeDtos = employeeMapper.toListDto(employees.getContent());
        
        return ResponseEntity.ok(employeeDtos);
    }

    private SearchOperation determineOperation(String operation, String key) {
        String formattedKey = key.toUpperCase();
        String operationKey = operation.toUpperCase();
    
        switch (formattedKey) {
            case "FIRST_NAME":
                return SearchOperation.valueOf(operationKey + "_FIRST_NAME");
            case "LAST_NAME":
                return SearchOperation.valueOf(operationKey + "_LAST_NAME");
            case "GENDER":
                return SearchOperation.valueOf(operationKey + "_GENDER");
            case "HIRE_DATE":
                return SearchOperation.valueOf(operationKey + "_HIRE_DATE");
            case "BIRTH_DATE":
                return SearchOperation.valueOf(operationKey + "_BIRTH_DATE");
            default:
                throw new IllegalArgumentException("Invalid search key: " + key);
        }
    }
}
