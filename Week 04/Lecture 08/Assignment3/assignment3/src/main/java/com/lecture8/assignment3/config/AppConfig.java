package com.lecture8.assignment3.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${datasource1.url}")
    private String datasource1Url;

    @Value("${datasource1.username}")
    private String datasource1Username;

    @Value("${datasource1.password}")
    private String datasource1Password;

    @Value("${datasource2.url}")
    private String datasource2Url;

    @Value("${datasource2.username}")
    private String datasource2Username;

    @Value("${datasource2.password}")
    private String datasource2Password;

    @Bean
    @Qualifier("dataSource1")
    public DataSource dataSource1() {
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.jdbc.Driver")
                .url(datasource1Url)
                .username(datasource1Username)
                .password(datasource1Password)
                .build();
    }

    @Bean
    @Qualifier("dataSource2")
    public DataSource dataSource2() {
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.jdbc.Driver")
                .url(datasource2Url)
                .username(datasource2Username)
                .password(datasource2Password)
                .build();
    }

}