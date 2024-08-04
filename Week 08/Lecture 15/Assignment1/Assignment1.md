## `OncePerRequestFilter` in Spring

### Overview

`OncePerRequestFilter` is a convenient abstract base class for filters in Spring that ensures a **filter is executed only once per request**. This is particularly useful for scenarios where a filter needs to perform a task only once per request, even if multiple requests are processed by the application.

### Key Concepts

**Purpose:** Guarantees that the filter's doFilter method is invoked only once per request, even if the filter chain is invoked multiple times.

**Usage:** Ideal for tasks like request logging, security checks, or modifying request attributes that should only happen once.

### How It Works

- Inheritance: Extend `OncePerRequestFilter` to create a custom filter.
- Implementation: Override the `doFilterInternal` method to define the filter’s logic.
- Configuration: Register the custom filter in the Spring application context.

### Advantages

1. Simplicity: Simplifies the process of ensuring a filter is applied only once per request.
2. Consistency: Provides a consistent way to handle request processing and avoid duplicate operations.

### When to Use

- Request Logging: Log details of each request only once.
- Security: Apply security checks that need to be executed a single time per request.
- Request Modifications: Modify request attributes or headers in a manner that only affects the current request.

### Example

Please refer to [these codes](/Assignment1/assignment1/src/main/java/com/lecture15/assignment1/) for the example.

#### Explanation:

When a request reaches a controller endpoint (e.g., /will-redirect or /will-forward), the OncePerRequestImpl filter is applied. It processes the request before it reaches the controller and logs information after the request has been processed by the controller and before the response is sent back to the client.

#### Test result
![result.png](/Week%2008/Lecture%2015/Assignment1/assignment1/img/Screen%20Shot%202024-07-30%20at%2009.51.22.png)
- Send a request to the `/will-forward` path.
- The **doFilter** in CustomFilter log appears 2 times.
- The **doFilter** in OncePerRequestImpl log appears 1 time.

The doFilterInternal method is called once per request. This method passes the request and response to the next filter or target resource in the chain using `chain.doFilter(request, response)`. After the request has been processed by the subsequent filters or the target resource (like a controller), the doFilterInternal method logs a message "doFilter in OncePerRequestImpl". This log entry helps in tracing the request processing flow.

### Conclusion
`OncePerRequestFilter` provides an effective mechanism for ensuring filters are processed only once per request, streamlining the development of filters that need to handle tasks uniquely for each request.
