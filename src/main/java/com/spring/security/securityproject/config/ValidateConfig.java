package com.spring.security.securityproject.config;

import com.spring.security.securityproject.filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 配置验证码验证过滤器
 * @author chengyl
 * @create 2019-03-19-20:02
 */
@Configuration
public class ValidateConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Override
    public void configure(HttpSecurity http) {
        //把自定义的验证码校验过滤器加到 UsernamePasswordAuthenticationFilter 之前
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
