package lab3;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

class Employee {
    private String name;
    private String id;

    public Employee(String id, String name){
        this.name = name;
        this.id = id;
    }
    
    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }
}


public class Lab3 {
    static List<Employee> lstEmployees = new ArrayList<>();
    static Map<String, String> mapEmployees = new TreeMap<>();

    private static String createEmployee(){
        Employee manager = new Employee("MNG_01", "Alex");
        Employee developer = new Employee("DEV_02", "Kollin");
        Employee admin = new Employee("ADM_10", "Luke");
        Employee qa = new Employee("QA_09", "Amber");

        lstEmployees.add(manager);
        lstEmployees.add(developer);
        lstEmployees.add(admin);
        lstEmployees.add(qa);
        return "Create 3 new employees successfully...";
    }

    private static String convert(){
        for (Employee emp : lstEmployees){
            mapEmployees.put(emp.getId(), emp.getName());
        }
        return "Convert list to map successfully...";
    }
    
    public static void main(String[] args) {
        System.out.println(createEmployee());
        System.out.println(convert());
        System.out.println(mapEmployees);
    }   

}
