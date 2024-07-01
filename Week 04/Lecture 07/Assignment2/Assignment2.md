<h2>Comparison types of dependency injection</h2>

### Constructor Injection
The constructor injection means passing the dependencies as parameters to the constructor of the class that needs them.
#### Pros
- Explicit dependencies are clearly defined in the constructor
- Objects are guareanteed to be in valid state after construction
- Clearly definition where dependencies of the class are came from
- Enables to use final fields which can prevent accidental change and improve performance
#### Cons
- Make constructors too long and complex, especillah if there are many dependencies or nested dependencies
- Does not support optional or dynamic dependencies, as we can't change or remove the once the class is created

### Setter Injection
Setter injection provides setter methods for the dependencies of a class, and calling them after class is instantiated.
#### Pros
- Have more flexibility and control on how and when to inject the dependencies
- Allow have dynamic dependencies as we can change or remove them at any time
#### Cons
- Increased coupling between classes because it exposes the internal state of the object and make it accessible for modification from outside
- There is no guarantee that all required dependencies will be set befor the object is used so it will less suitable for immutable object

### Field Injection
Field injection involves using annotations, such as @Autowired, to mark the field that need to be injected with dependencies. 
#### Pros
- We can avoid writing constructors or setters for the dependencies, and let a framework or a container handle the injection for you.
- Make code concise and simple as we only need to declare the fields and annotate them
#### Cons
- Creates a risk of `NullPointerExceptio` if dependencies aren’t correctly initialized
- Unable to create immutable classes

<h2>Annotations</h2>

1. **@Configuration**
2. **@Bean**
3. **@ComponentScan**
4. **@Component**
5. **@Service**
6. **@Repository**
7. **@Autowired**
8. **@Scope**
9. **@Qualifier**
10. **@PropertySource, @Value**
11. **@PreDetroy, @PostConstruct**