package com.spring.security.securityproject;

import com.spring.security.securityproject.pojo.config.SecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityProjectApplication.class, args);
    }


}


