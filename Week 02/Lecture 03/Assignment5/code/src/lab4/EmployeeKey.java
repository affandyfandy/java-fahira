package lab4;

public class EmployeeKey {
    String department;
    String id;

    public EmployeeKey(String id, String department){
        this.id = id;
        this.department = department;
    }

    @Override
    public String toString(){
        return "EmployeeKey{" +
                "id=" + id +
                ", department='" + department + '\'' +
                '}';
    }
}
