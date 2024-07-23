package com.lecture11.assignment1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lecture11.assignment1.dto.DepartmentEmployeeMapper;
import com.lecture11.assignment1.dto.DepartmentManagerMapper;
import com.lecture11.assignment1.dto.DepartmentMapper;
import com.lecture11.assignment1.dto.request.CreateDepartmentDto;
import com.lecture11.assignment1.dto.request.CreateDepartmentEmployeeDto;
import com.lecture11.assignment1.dto.request.CreateDepartmentManagerDto;
import com.lecture11.assignment1.dto.response.ReadDepartmentDto;
import com.lecture11.assignment1.model.Department;
import com.lecture11.assignment1.model.DepartmentEmployee;
import com.lecture11.assignment1.model.DepartmentManager;
import com.lecture11.assignment1.service.DepartmentEmployeeService;
import com.lecture11.assignment1.service.DepartmentManagerService;
import com.lecture11.assignment1.service.DepartmentService;
import com.lecture11.assignment1.service.EmployeeService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentManagerService departmentManagerService;

    @Autowired
    private DepartmentEmployeeService departmentEmployeeService;

    @Autowired
    private DepartmentEmployeeMapper departmentEmployeeMapper;

    @Autowired
    private DepartmentManagerMapper departmentManagerMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @GetMapping
    public ResponseEntity<List<ReadDepartmentDto>> getAllDepartment() {
        List<Department> depts = departmentService.findAll();
        List<ReadDepartmentDto> listDepartment = departmentMapper.toListDto(depts);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(listDepartment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadDepartmentDto> getDepartmentById(@PathVariable String id) {
        Department dept = departmentService.findById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(departmentMapper.toDto(dept));
    }
    
    @PostMapping
    public ResponseEntity<ReadDepartmentDto> addNewDepartment(@RequestBody CreateDepartmentDto departmentDto){
        Department department = departmentMapper.toEntity(departmentDto);
        department = departmentService.add(department);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(departmentMapper.toDto(department));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReadDepartmentDto> updateDepartment(@PathVariable String id, @RequestBody CreateDepartmentDto departmentDto) {
        Department department = departmentMapper.toEntity(departmentDto);
        department = departmentService.update(department, id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(departmentMapper.toDto(department));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Delete successful!");
    }

    @PostMapping("/{id}/employee")
    public ResponseEntity<?> addDepartmentEmployee(@PathVariable String id, @RequestBody CreateDepartmentEmployeeDto deptEmployeeDto){
        if (id.toString().equals(deptEmployeeDto.getDepartment()) == false){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        DepartmentEmployee deptEmpl = departmentEmployeeMapper.toEntity(deptEmployeeDto);
        deptEmpl.setDepartment(departmentService.findById(deptEmployeeDto.getDepartment()));
        deptEmpl.setEmployee(employeeService.findById(deptEmployeeDto.getEmployee()));
        departmentEmployeeService.create(deptEmpl);
        Department dept = departmentService.findById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(departmentMapper.toDto(dept));
    }

    @PostMapping("/{id}/manager")
    public ResponseEntity<?> addDepartmentManager(@PathVariable String id, @RequestBody CreateDepartmentManagerDto deptManagerDto){
        if (id.toString().equals(deptManagerDto.getDepartment()) == false){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        DepartmentManager mappedDeptEmpl = departmentManagerMapper.toEntity(deptManagerDto);
        mappedDeptEmpl.setDepartment(departmentService.findById(deptManagerDto.getDepartment()));
        mappedDeptEmpl.setEmployee(employeeService.findById(deptManagerDto.getEmployee()));
        departmentManagerService.create(mappedDeptEmpl);
        Department dept = departmentService.findById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(departmentMapper.toDto(dept));
    }

}
