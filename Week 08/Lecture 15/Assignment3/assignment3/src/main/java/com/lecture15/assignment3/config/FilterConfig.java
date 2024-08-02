package com.lecture15.assignment3.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lecture15.assignment3.filter.ApiKeyFilter;
import com.lecture15.assignment3.service.ApiKeyService;


@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<ApiKeyFilter> apiKeyFilterRegistration(ApiKeyService apiKeyService) {
        FilterRegistrationBean<ApiKeyFilter> registrationBean = new FilterRegistrationBean<>();
        ApiKeyFilter apiKeyFilter = new ApiKeyFilter(apiKeyService);
        registrationBean.setFilter(apiKeyFilter);
        return registrationBean;
    }
}
