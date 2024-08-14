# Microservice with gateway

APIs facilitate communication between applications in a microservice architecture. An API Gateway acts as an intermediary between the client and the services to manage all API requests. The benefits of implementing an API Gateway include:

1. **Reduced Complexity**: Simplifies the client’s interaction with the system.
2. **Centralized Control**: Manages and enforces policies across services.
3. **Simplified Troubleshooting**: Eases the process of diagnosing issues.

Various API Gateways can be implemented in a Spring project, including Spring Cloud Gateway, Zuul API Gateway, APIGee, and others. In this project, Spring Cloud Gateway is implemented.

## Modules

- **`book`**: Responsible for core book-related functionality. This module covers CRUD operations for book entities.
- **`writer`**: Provides functionality related to writers. This module covers CRUD operations for writer entities.
- **`api-gateway`**: Acts as the API gateway, handling routing and service discovery.

Modules are automatically included in the [pom.xml](/Week%2010/Lecture%2019/Assignment1/pom.xml) located at the root directory:


```xml
    <modules>
        <module>book</module>
        <module>writer</module>
        <module>api-gateway</module>
    </modules>
```

While we are install the application in the root directory, it'll automatically install the modules included on the [pom.xml](/Week%2010/Lecture%2019/Assignment1/pom.xml).

## Route

To set up the API Gateway, configure routes in the [ApiGatewayApplication.java](/Week%2010/Lecture%2019/Assignment1/api-gateway/src/main/java/com/assignment1/api_gateway/ApiGatewayApplication.java) file:

```java
@Bean
    public RouteLocator routerBuilder(RouteLocatorBuilder routeLocatorBuilder){ 
        return routeLocatorBuilder.routes() 
                        .route("Book",r->r.path("/api/v1/book/**") 
                                .uri("http://localhost:8081/")) 
                        .route("Writer",r->r.path("/api/v1/writer/**") 
                                .uri("http://localhost:8082/")).build(); 
    } 
```

After setting up the gateway, create [application.yaml](/Week%2010/Lecture%2019/Assignment1/api-gateway/src/main/resources/application.yaml) to set up the routing.

## Getting started

To build and run the project, follow these steps:

1. **Build the Project**

   Navigate to the root of the project and run:
   
```bash
   mvn clean package
```

2. **Run Individual Modules**

    Run each module by navigating to its directory and running:
    
```bash
    mvn spring-boot:run
```

3. **Run API Gateway**

    To start the API gateway, navigate to the api-gateway directory and run:
    
```bash
    mvn spring-boot:run
```

## Result

### Post a writer
![res1.png](/Week%2010/Lecture%2019/Assignment1/image/Screen%20Shot%202024-08-13%20at%2010.34.53.png)

### Retrieve all books
![res2.png](/Week%2010/Lecture%2019/Assignment1/image/Screen%20Shot%202024-08-13%20at%2010.37.25.png)

### Retrieve all writers
![res3.png](/Week%2010/Lecture%2019/Assignment1/image/Screen%20Shot%202024-08-13%20at%2010.36.35.png)

### Post a book
![res4.png](/Week%2010/Lecture%2019/Assignment1/image/Screen%20Shot%202024-08-13%20at%2013.30.06.png)

### Search book
![res5.png](/Week%2010/Lecture%2019/Assignment1/image/Screen%20Shot%202024-08-14%20at%2010.56.10.png)

### Update writer
![res6.png](/Week%2010/Lecture%2019/Assignment1/image/Screen%20Shot%202024-08-14%20at%2010.56.16.png)

### Update book
![res7.png](/Week%2010/Lecture%2019/Assignment1/image/Screen%20Shot%202024-08-14%20at%2010.56.25.png)