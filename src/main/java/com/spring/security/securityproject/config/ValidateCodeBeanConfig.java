package com.spring.security.securityproject.config;

import com.spring.security.securityproject.pojo.config.SecurityProperties;
import com.spring.security.securityproject.service.validateCode.MyImageCodeGenerator;
import com.spring.security.securityproject.service.validateCode.MySmsCodeGenerator;
import com.spring.security.securityproject.service.validateCode.sms.DefaultSmsCodeSender;
import com.spring.security.securityproject.service.validateCode.sms.SmsCodeSender;
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
    public MyImageCodeGenerator imageCodeGenerator() {
        MyImageCodeGenerator generator = new MyImageCodeGenerator();
        generator.setSecurityProperties(securityProperties);
        return generator;
    }


    @Bean
    @ConditionalOnMissingBean(MySmsCodeGenerator.class)
    public MySmsCodeGenerator smsCodeGenerator() {
        System.out.println("smsCodeGenerator 被初始化了.....................");
        MySmsCodeGenerator generator = new MySmsCodeGenerator();
        generator.setSecurityProperties(securityProperties);
        return generator;
    }


    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public DefaultSmsCodeSender smsCodeSender() {
        System.out.println("DefaultSmsCodeSender 被初始化了.....................");
        DefaultSmsCodeSender sender = new DefaultSmsCodeSender();
        return sender;
    }

}
