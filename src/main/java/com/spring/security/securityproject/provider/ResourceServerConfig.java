package com.spring.security.securityproject.provider;

import com.spring.security.securityproject.config.SmsCodeAuthenticationSecurityConfig;
import com.spring.security.securityproject.constant.SecurityConstants;
import com.spring.security.securityproject.filter.ValidateCodeFilter;
import com.spring.security.securityproject.pojo.config.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author chengyl
 * @create 2019-03-21-19:19
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    private AuthenticationFailureHandler failureHandler;
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsConfig;
    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http//.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin()//表单登录
            .successHandler(successHandler)//登录成功后的处理实现类
            .failureHandler(failureHandler)//登录失败后的处理实现类
            .loginPage(SecurityConstants.DEFAULT_LOGIN_PAGE_URL)
            .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)//这个url的请求会传给过滤器进行用户校验
            .and()
            .authorizeRequests()//需要授权登录
            .antMatchers(SecurityConstants.DEFAULT_LOGIN_PAGE_URL, SecurityConstants.DEFAULT_UNAUTHENTICATION_URL, securityProperties.getBrowser().getLoginPage(),
                        "/code/*", SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_SMS, SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM,
                        "/logout.html", SecurityConstants.DEFAULT_SESSION_INVALID_URL).permitAll()//这个url的请求放行
            .anyRequest()//所有请求
            .authenticated()//都要身份认证
            .and()
            .csrf().disable()//关闭csrf
            .apply(smsConfig);//加入SMS登录配置
    }
}
