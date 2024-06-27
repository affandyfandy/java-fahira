<h2>Remove duplicated items for any object and any duplicated fields</h2>

To remove duplicated items based on any object and any duplicated fields, we need to define equality criteria that specify when two objects should be considered duplicates. This involves overriding the `equals()` and `hashCode()` methods.

1. **Use a Set**

HashSet automatically removes duplicates when elements are added due to its use of the `equals()` and `hashCode()` methods for equality checking.

2. **Use Generics**

Using generics allows us to create reusable code that can work with different types of objects while maintaining type safety. To remove duplicate items from a list using generics, we can leverage a Set to filter out duplicates based on the criteria defined by the `equals()` and `hashCode()` methods of the objects.
```java
public static void main(String[] args) {
    List<String> stringList = Arrays.asList("apple", "banana", "apple", "orange", "banana", "kiwi");
    List<Integer> integerList = Arrays.asList(1, 2, 3, 1, 4, 2, 5, 3);

    List<String> uniqueStrings = removeDuplicates(stringList);
    List<Integer> uniqueIntegers = removeDuplicates(integerList);

    System.out.println("Original String List: " + stringList);
    System.out.println("Unique Strings List: " + uniqueStrings);

    System.out.println("Original Integer List: " + integerList);
    System.out.println("Unique Integers List: " + uniqueIntegers);
}

public static <T> List<T> removeDuplicates(List<T> list) {
    Set<T> set = new LinkedHashSet<>(list);
    return new ArrayList<>(set);
}
```
