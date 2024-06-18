package lab2;
public class Teacher {
    String name;
    int age;
    Subject subject;

    public Teacher(String name, int age, Subject subject){
        this.name = name;
        this.age = age;
        this.subject = subject;
    }

    public String getName(){
        return this.name;
    }

    public int getAge(){
        return this.age;
    }

    public Subject getSubject(){
        return subject;
    }
    
    public String teaching() {
        return String.format( "Teacher %s teaching %s for %s",
                            this.getName(), this.getSubject().getName(), this.getSubject().getClassId());
    }
    
}
