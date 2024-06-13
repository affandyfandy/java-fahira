<h2>Passing Reference Types</h2>

For the better understanding, we will add new attribute named `name` of type `String` to class `MyClass`.

```
class MyClass {
    int value;
    String name;
}
```

#### Modify the object that the reference points to
```
public class Main {
    public static void main(String[] args) {
        MyClass obj = new MyClass();
        obj.value = 5;
        obj.name = "Old Name";
        modifyObject(obj);
        System.out.println("obj.value and obj.name after modifyObject: " + obj.value + " and " + obj.name);
    }

    public static void modifyObject(MyClass x) {
        x.value = 10;
        x.name = "New Name";
    }
}
```
When the main function is executed, it becomes the first item on the stack. Then, an instance of MyClass named obj is created. The value of obj is set to 5 and the name is set to "Old Name" by executing the next line. The modifyObject function is then called, pushing it onto the stack. Inside modifyObject, the value of obj is changed to 10, and the name is changed to "New Name". After modifyObject completes its execution, it's removed from the stack. Finally, the program prints:
`obj.value and obj.name after modifyObject: 10 and New Name`

![alt text](https://ibb.co.com/18WgHmP)

#### Cannot change the reference itself to point to a different object
```
public class Main {
    public static void changeReference(MyClass x) {
        x = new MyClass();  // This change the local reference, not the original reference
        x.value = 10;
        x.name = "New Name";
    }

    public static void main(String[] args) {
        MyClass obj = new MyClass();
        obj.value = 5;
        obj.name = "Old Name";
        changeReference(obj);
        System.out.println("obj.value and obj.name after changeReference: " + obj.value + " and " + obj.name);
    }
}
```
Firstly, the main function is executed, and it creates a new instance of MyClass. The instance's reference is assigned to obj. Next, the value attribute of obj is set to 5, and name is set to "Old Name". By calling the changeReference method, it passes the reference of obj. In the changeReference() method, the reference of the object is passed to the method parameter x. Then, a new object of MyClass is assigned to the local reference x. This change affects only the local variable x and does not modify the original reference obj in the main method. Then, the attributes of the new object referred to by x are modified. Finally, the program output is: `obj.value and obj.name after changeReference: 5 and New Name`

![alt text](https://ibb.co.com/SQxjW9n)