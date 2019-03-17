package com.spring.security.securityproject.config;

import com.spring.security.securityproject.pojo.config.SecurityProperties;
import com.spring.security.securityproject.service.MyImageCodeGenerator;
import com.spring.security.securityproject.service.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chengyl
 * @create 2019-03-17-14:12
 */
@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")//如果不存在一个叫做 imageCodeGenerator 的bean，则创建
    public ValidateCodeGenerator imageCodeGenerator() {
        ValidateCodeGenerator generator = new MyImageCodeGenerator();
        ((MyImageCodeGenerator) generator).setSecurityProperties(securityProperties);
        return generator;
    }

}
