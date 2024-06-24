package lab4;

import java.util.Map;
import java.util.HashMap;

import lab3.Employee;

public class Main {
    public static void main(String[] args) {
        Map<EmployeeKey, Employee> employees = new HashMap<>();

        Employee e1 = new Employee("S02", "Erik");
        EmployeeKey ek1 = new EmployeeKey(e1.getId(), "Sales");

        Employee e2 = new Employee("M08", "Haag");
        EmployeeKey ek2 = new EmployeeKey(e2.getId(), "Manager");

        employees.put(ek2, e2);
        employees.put(ek1, e1);
    }
}
