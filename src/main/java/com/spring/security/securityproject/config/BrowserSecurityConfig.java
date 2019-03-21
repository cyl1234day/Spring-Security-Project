package com.spring.security.securityproject.config;

import com.spring.security.securityproject.constant.SecurityConstants;
import com.spring.security.securityproject.filter.ValidateCodeFilter;
import com.spring.security.securityproject.pojo.config.SecurityProperties;
import com.spring.security.securityproject.service.session.MyExpiredSessionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * 用来配置 浏览器 相关的配置项
 * @author chengyl
 * @create 2019-03-18-16:16
 */
@Component
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    private AuthenticationFailureHandler failureHandler;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsConfig;
    @Autowired
    private ValidateCodeFilter validateCodeFilter;
    @Autowired
    private SessionInformationExpiredStrategy expiredStrategy;
    @Autowired
    private InvalidSessionStrategy invalidStrategy;
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers("/**").permitAll();
//        http.csrf().disable();
//    }

    /**
     * 实现 RememberMe 功能 所需要的 DAO
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {

        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        //设置数据源
        jdbcTokenRepository.setDataSource(dataSource);
        //第一次启动创建表，之后要注释掉，否则会报错
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //登录校验加密算法配置
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {

//        如果ValidateCodeFilter没有声明为 @Component 则要手动创建
//        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
//        validateCodeFilter.setFailureHandler(failureHandler);

        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin()//表单登录
            .successHandler(successHandler)//登录成功后的处理实现类
            .failureHandler(failureHandler)//登录失败后的处理实现类
//            .loginPage(SecurityConstants.DEFAULT_LOGIN_PAGE_URL)
          .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)//如果需要身份认证，跳转到url的Controller方法
            .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)//这个url的请求会传给过滤器进行用户校验
//          .loginProcessingUrl("/authentication/sms")
            .and()
            .sessionManagement()//配置Session管理
            .invalidSessionStrategy(invalidStrategy)
//            .invalidSessionUrl(securityProperties.getBrowser().getSession().getInvalidSessionUrl())//session失效后跳转的URL
            .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())//最多只允许有一个session
            .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())//阻止后面的用户登录
            .expiredSessionStrategy(expiredStrategy)//Session过期策略
            .and()
            .and()
            .rememberMe()//开始配置 RememberMe 功能
            .tokenRepository(persistentTokenRepository())//配置数据源
            .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())//配置有效时间
            .userDetailsService(userDetailsService)//配置调用的UserDetails服务
            .and()
            .logout()//退出登录相关配置
            .logoutUrl("/checkout")//这个URL就表示退出，默认的是/logout，退出后跳转到登录页（loginPage）
            .logoutSuccessHandler(logoutSuccessHandler)//成功退出处理器
            .deleteCookies("JSESSIONID")//退出成功后要清除的cookie
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

