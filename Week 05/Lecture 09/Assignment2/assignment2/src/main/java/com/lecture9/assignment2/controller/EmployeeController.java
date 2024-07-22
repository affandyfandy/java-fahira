package com.lecture9.assignment2.controller;

import java.util.List;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lecture9.assignment2.model.Employee;
import com.lecture9.assignment2.service.EmployeeService;
import com.lecture9.assignment2.utils.FileUtils;

import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;



@AllArgsConstructor
@RequestMapping("/employees")
@Controller
public class EmployeeController {
    
    private final EmployeeService employeeService;

    @GetMapping("/list")
    public String listEmployees(Model model) {
        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);
        return "employees/viewall-employee";
    }

    @GetMapping("/{id}")
    public String detailEmployee(@PathVariable("id") String id, Model model){
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        return "employees/view-employee";
    }

    @GetMapping("/{id}/update")
    public String formUpdateEmployee(@PathVariable("id") String id, Model model){
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        return "employees/form-update-employee";
    }

    @PostMapping("/{id}/update")
    public String updateEmployee(@PathVariable("id") String id, Model model, Employee employee){
        Employee findEmployee = employeeService.findById(id);
        findEmployee.setName(employee.getName());
        findEmployee.setAddress(employee.getAddress());
        findEmployee.setDob(employee.getDob());
        findEmployee.setSalary(employee.getSalary());
        findEmployee.setDepartment(employee.getDepartment());
        employeeService.save(findEmployee);
        model.addAttribute("employee", findEmployee);
        return "redirect:/employees/list";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id) {
        employeeService.deleteById(id);
        return "redirect:/employees/list";
    }

    @GetMapping("/add")
    public String formAddEmployee(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "employees/form-add-employee";
    }

    @PostMapping("/add")
    public String addEmployee(Employee employee, Model model) {
        employeeService.save(employee);
        return "redirect:/employees/list";
    }

    @GetMapping("/upload")
    public String formUploadCsv(Model model) {
        return "employees/form-upload-employee";
    }

    @PostMapping("/upload")
    public String uploadData(@RequestParam("file") MultipartFile file) throws IOException{
        List<Employee> listEmployee = FileUtils.readEmployeeFromCSV(file);
        employeeService.saveAll(listEmployee);
        return "redirect:/employees/list";
    }
}
