package com.lecture10.assignment1.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lecture10.assignment1.dto.EmployeeDTO;
import com.lecture10.assignment1.dto.EmployeeMapper;
import com.lecture10.assignment1.exception.EmployeeNotFoundException;
import com.lecture10.assignment1.model.Employee;
import com.lecture10.assignment1.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public void save(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEmployee(employeeDTO);
        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public EmployeeDTO update(EmployeeDTO employeeDTO, String id){
        var findEmployee = employeeRepository.findById(id);
        if (findEmployee.isPresent()){
            Employee employee = findEmployee.get();
            employee.setAddress(employeeDTO.getAddress());
            employee.setDepartment(employeeDTO.getDepartment());
            employee.setDob(employeeDTO.getDob());
            employee.setEmail(employeeDTO.getEmail());
            employee.setName(employeeDTO.getName());
            employee.setPhone(employeeDTO.getPhone());
            employeeRepository.save(employee);
            return employeeMapper.toDTO(employee);
        }
        else{
            throw new EmployeeNotFoundException("Couldn't find employee with id: " + id);
        }
    }

    @Override
    @Transactional
    public EmployeeDTO findById(String id){
        var employee = employeeRepository.findById(id);
        if (employee.isPresent()){
            return employeeMapper.toDTO(employee.get());
        }
        else{
            throw new EmployeeNotFoundException("Couldn't find employee with id: " + id);
        }
    }

    @Override
    @Transactional
    public List<EmployeeDTO> listAllEmployee() {
        return employeeMapper.toListDTO(employeeRepository.findAll());
    }

    @Override
    @Transactional
    public void delete(String id) {
        var employee = employeeRepository.findById(id);
        if (employee.isPresent()){
            employeeRepository.delete(employee.get());
        }
        else{
            throw new EmployeeNotFoundException("Couldn't find employee with id: " + id);
        };
    } 

    @Override
    @Transactional
    public List<EmployeeDTO> findByDepartment(String department) {
        return employeeMapper.toListDTO(employeeRepository.findAllByDepartment(department));
    }

    @Override
    @Transactional
    public void saveAll(List<EmployeeDTO> listEmployee){
        var employees = employeeMapper.toList(listEmployee);
        employeeRepository.saveAll(employees);
    }
    
}