package lab4;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lab3.Employee;

public class Main {

    static Map<EmployeeKey, Employee> employees = new HashMap<>();
    static List<Employee> listEmployee = new ArrayList<>();

    private static void convert(){
        for (Employee e : listEmployee){
            EmployeeKey ek = new EmployeeKey(e.getId(), e.getDepartment());
            employees.put(ek, e);
        }
    }
    
    public static void main(String[] args) {
        Employee e1 = new Employee("S02", "Erik", "IT");
        Employee e2 = new Employee("M08", "Haag", "Human Resources");
        listEmployee.add(e1);
        listEmployee.add(e2);
        convert();
        System.out.println(employees);
    }
}
