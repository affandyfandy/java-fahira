package lab3;

public class Employee {
    private String name;
    private String id;
    private String department;

    public Employee(String id, String name, String department){
        this.name = name;
        this.id = id;
        this.department = department;
    }
    
    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getDepartment(){
        return this.department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
