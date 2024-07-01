import com.lecture7.code.Employee;
import com.lecture7.code.EmployeeWork;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Employee employee() {
        return new Employee("MNG_001", "Keiko", 35, employeeWork());
    }

    @Bean
    public EmployeeWork employeeWork() {
        return new EmployeeWork();
    }
}
