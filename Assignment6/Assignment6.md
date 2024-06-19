<h2>Paralel Streams</h2>
A stream in Java is simply a wrapper around a information source, permitting us to perform bulk operations on the information in a helpful way. It doesn't store information or make any changes to the fundamental information source. Rather, it adds support for functional-style operations on data pipelines. With paralel streams, it enable us to execute code in parallel on separate cores. The final result is the combination of each individual outcome.

### When to use paralel streams
- <b>Large Data Sets</b>: When we have a large collection or data set and performing operations sequentially would be very complex.
- <b>Operations on Independent Elements</b>: Operations where each element can be processed independently of others are suitable for parallelization, example include filtering, mapping, and sorting.
- <b>Performance Improvement</b>: When we expect a performance gain by utilizing multiple threads for processing.

### Consideration and notice
- <b>Overhead</b>: Parallel streams have overhead associated with thread management, data partitioning, and merging results. For small data sets or simple operations, this overhead might outweigh the benefits of parallelism.
- <b>Ordering</b>: Operations that rely on the order of elements may not behave as expected in parallel streams unless the stream is explicitly ordered.
- <b>Thread Safety</b>: Ensure that the operations applied to elements in parallel streams are thread-safe, especially if the stream source or operations involve mutable state.

### Example
```
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
// Sequential stream
long countSequential = numbers.stream()
                                .filter(n -> n % 2 == 0)
                                .mapToInt(n -> n * 2)
                                .count();

System.out.println("Sequential Stream Result: " + countSequential);

// Parallel stream
long countParallel = numbers.parallelStream()
                            .filter(n -> n % 2 == 0)
                            .mapToInt(n -> n * 2)
                            .count();

System.out.println("Parallel Stream Result: " + countParallel);
```
The final result is the combination of each individual outcome.

#### Explanation
1. `numbers.stream()` creates a sequential stream. Operations `filter and mapToInt)` are executed sequentially on each element of the stream.
2. `numbers.parallelStream()` creates a parallel stream. Operations `filter and mapToInt` can be executed concurrently on multiple threads, potentially improving performance for large datasets.
3. Running this example with a large dataset could demonstrate the performance difference between sequential and parallel streams.