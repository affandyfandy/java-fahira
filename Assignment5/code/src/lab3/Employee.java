package lab3;

public class Employee {
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
