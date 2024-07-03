<h2>Add beans scope for Employee app (refer to assignment 2)</h2>

First, we need to create the configuration class to configure all the beans. I've defined this in ![beans.xml](/Assignment3/assignment3/src/main/java/Beans.xml). In this configuration, I've declared two beans with different scopes.

The first bean is EmailService, which uses the default bean scope (singleton). The next bean is EmployeeService, configured with a prototype scope. For this project, I've chosen EmployeeServiceConstructor from a previous assignment to serve as the EmployeeService.

After defining the beans, I proceeded to the main method and called the services.

The differences between singleton and prototype scopes are reflected in the hashCode of each instantiated object. When a bean is set to singleton scope, the Spring IoC container creates exactly one instance of the defined object. This single instance is stored in a cache, and all subsequent requests for that bean return the cached object.

In contrast, a prototype-scoped bean results in the Spring IoC container creating a new instance of the object every time it's requested. As a guideline, prototype scope is suitable for stateful beans, whereas singleton scope is preferred for stateless beans.

![result](/Assignment3/assignment3/image/result.png)

<h2>Create controller and test it (refer to previous assignment)</h2>

In this part, we will dive into **request-scoped** bean. Request scope is a bean definition to an HTTP request. Only valid in the context of a web-aware Spring ApplicationContext. We can define the bean with the request scope using the @Scope annotation.

<h2>BeanFactory vs. ApplicationContext</h2>

### BeanFactory
- Basic IoC container: Provides fundamental functionalities for Inversion of Control (IoC) and Dependency Injection (DI).
- Bean creation and retrieval: We can use getBean() method to retrieve a bean instance from the factory.
- Limited features: Supports basic functionalities like bean creation, retrieval, and configuration. Lacks advanced features like event handling, internationalization, etc.
- Lazy initialization: Beans are created only when requested (getBean() is called). This can be memory-efficient but may introduce a slight delay when first accessing a bean.
- Use cases: Ideal for simple applications or scenarios where memory usage is a critical concern.

### ApplicationContext

- Advanced IoC container: Extends BeanFactory and offers additional features.
- Inherits functionalities: Provides all functionalities of BeanFactory along with its own capabilities.
- Advanced features: Supports functionalities like event handling, message publishing, internationalization (i18n), resource management, etc.
- Eager initialization (default): By default, ApplicationContext creates singleton beans during startup, making them readily available. This can impact initial startup time but avoids delays on first access.
- Use cases: Generally recommended for most Spring applications due to its rich set of features and simplified development experience.

In most cases, **ApplicationContext is the preferred choice** due to its comprehensive features and ease of use. We can use BeanFactory only if we need a very lightweight container with minimal memory footprint and we have a specific reason to avoid eager initialization and prefer lazy loading.