<h2>ArrayList vs LinkedList</h2>

### ArrayList
- <b>Access</b>. ArrayList is generally faster in accessing elements by index because it implements an array internally. The complexity of indexing is O(1).
- <b>Search</b>. ArrayList is suitable for process searching based on index (such as indexOf, lastIndexOf). The complexity of the operations is O(n).
- <b>Memory</b>. ArrayList uses less memory thank LinkedList for sorting elements.
- <b>Iteration</b>. Iterating over an ArrayList is typically faster than LinkedList because it uses sequential access.

### LinkedList
- <b>Memory</b>. Each element in a LinkedList contains a reference to the next and previous elements, so it consume more memory than ArrayList.
- <b>Access</b>. LinkedList does not provide random access to elements by index.
- <b>Insertion and Deletion</b>. LinkedList performs insert and delete operations efficiently (O(1) time complexity), compared to ArrayList which may require resizing the internal array (O(n) time complexity).

### When to use LinkedList vs. ArrayList
ArrayList: Use to the application that need fast access and iteration, and when the list size is relatively fixed or changes infrequently. Also, prefer ArrayList if memory usage is a concern or if it will perform a lot of index-based operations.

LinkedList: Use when frequent insertions and deletions are required, especially if they are near the beginning or middle of the list. Also, consider LinkedList if memory consumption is not a critical issue and if when it do not need frequent index-based access.

<h2>HashSet vs TreeSet vs LinkedHashSet</h2>

| Feature             | HashSet                                    | TreeSet                                   | LinkedHashSet                             |
|---------------------|--------------------------------------------|-------------------------------------------|-------------------------------------------|
| **Order**           | No specific order (based on hash codes)     | Sorted order (natural or custom)          | Insertion order                           |
| **Null element**    | Allows one `null` element                   | Does not allow `null` elements            | Allows one `null` element                 |
| **Performance**     | O(1) average for add, remove, contains     | O(log n) for add, remove, contains        | O(1) average for add, remove, contains    |
|                     | O(n) in worst-case due to collisions       | operations due to balanced tree structure |                                           |
| **Synchronized**    | Not synchronized                           | Not synchronized                          | Not synchronized                          |
| **Fail-Fast/Safe**  | Fail-Fast                                  | Fail-Fast                                 | Fail-Fast                                 |
| **When to Use**     | Fast access and uniqueness, order not important      | Sorting elements, efficient smallest/largest element retrieval      | Maintain insertion order with uniqueness |
| 

In summary, HashSet is efficient for general-purpose use with fast add, remove, and contains operations, but does not maintain any specific order. TreeSet maintains elements in sorted order, suitable when elements need to be sorted and efficient retrieval of smallest/largest elements is required. LinkedHashSet maintains insertion order of elements while providing fast access times similar to HashSet, suitable when it need to maintain the order of insertion and uniqueness.