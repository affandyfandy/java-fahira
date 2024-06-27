package lab3;

import java.util.ArrayList;
import java.util.List;

import lab2.Subject;

public class Student {
    
    String name;
    int age;
    List<Subject> enroll;

    public Student(String name, int age){
        this.name = name;
        this.age = age;
        this.enroll = new ArrayList<>();
    }

    public void setEnroll(Subject subject){
        enroll.add(subject);
    }
    

    private String getEnrolledSubject(){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i < enroll.size(); i++){
            if (i != enroll.size()-1){
                sb.append(i+1+". "+ enroll.get(i).getName()+"\n");
            }
            else {
                sb.append(i+1+". "+ enroll.get(i).getName());
            }
        }
        return sb.toString();
    }

    public String learning(){
        return String.format("The student %s will learning some Subject %s:\n%s",
                            this.name, this.enroll, this.getEnrolledSubject());        
    }
}

