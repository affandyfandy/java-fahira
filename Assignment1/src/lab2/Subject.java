package lab2;

public class Subject {
    
    String name;
    String classId;

    public Subject(String name, String classId){
        this.name = name;
        this.classId = classId;
    }

    public String getName(){
        return this.name;
    }

    public String getClassId(){
        return this.classId;
    }

}
