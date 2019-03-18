package com.spring.security.securityproject.config;

import com.spring.security.securityproject.component.MyLoginFailureHandler;
import com.spring.security.securityproject.component.MyLoginSuccessHandler;
import com.spring.security.securityproject.service.smsLogin.SmsAuthenticationFilter;
import com.spring.security.securityproject.service.smsLogin.SmsCodeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 用来配置 SmsAuthenticationFilter 和 SmsCodeAuthenticationProvider
 * @author chengyl
 * @create 2019-03-18-16:16
 */
@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private MyLoginFailureHandler failureHandler;
    @Autowired
    private MyLoginSuccessHandler successHandler;
    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        //先配置SmsAuthenticationFilter

        SmsAuthenticationFilter smsAuthenticationFilter = new SmsAuthenticationFilter();
        //配置AuthenticationManager
        smsAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        //配置成功和失败处理器
        smsAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
        smsAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);

        //再配置SmsAuthenticationProvider

        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);

        //加入到Spring框架中
        http.addFilterAfter(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .authenticationProvider(smsCodeAuthenticationProvider);
    }
}
