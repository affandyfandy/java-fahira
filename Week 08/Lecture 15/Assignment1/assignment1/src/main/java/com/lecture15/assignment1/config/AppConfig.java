package com.lecture15.assignment1.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.lecture15.assignment1.filter.CustomFilter;
import com.lecture15.assignment1.filter.OncePerRequestImpl;
import java.util.EnumSet;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;

@Configuration
public class AppConfig {

    /**
     * Registers a custom filter with the application context.
     *
     * @return a {@link FilterRegistrationBean} configured with {@link CustomFilter} to be applied to all URL patterns.
     * The filter will be triggered for both request and forward dispatcher types.
     */
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));
        return registrationBean;
    }


    /**
     * Provides an instance of {@link OncePerRequestImpl} filter to be managed by Spring.
     *
     * @return an instance of {@link OncePerRequestImpl} filter.
     */
    @Bean(name = "someFilter")
    public Filter someFilter() {
        return new OncePerRequestImpl();
    }
}
