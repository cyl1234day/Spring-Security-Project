package com.spring.security.securityproject.config;

import com.spring.security.securityproject.filter.ValidateCodeFilter;
import com.spring.security.securityproject.pojo.config.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author chengyl
 * @create 2019-03-16-13:16
 */
@Configuration
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
    private ValidateCodeFilter validateCodeFilter;
    @Autowired
    private DataSource dataSource;


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
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

/*    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()//表单登录
            .loginPage("")
            .and()
            .authorizeRequests()//需要授权
            .anyRequest()//所有请求
            .authenticated();//都要验证
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        如果ValidateCodeFilter没有声明为 @Component 则要手动创建
//        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
//        validateCodeFilter.setFailureHandler(failureHandler);

        //把自定义的验证码校验过滤器加到 UsernamePasswordAuthenticationFilter 之前
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
             .formLogin()//表单登录
            .successHandler(successHandler)//登录成功后的处理实现类
            .failureHandler(failureHandler)//登录失败后的处理实现类
            .loginPage("/index.html")
//            .loginPage("/login/authentication")//如果需要身份认证，跳转到url的Controller方法
            .loginProcessingUrl("/authentication/form")//这个url的请求会传给过滤器进行用户校验
            .and()
            .rememberMe()//开始配置 RememberMe 功能
            .tokenRepository(persistentTokenRepository())//配置数据源
            .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())//配置有效时间
            .userDetailsService(userDetailsService)//配置调用的UserDetails服务
            .and()
            .authorizeRequests()//需要授权登录
            .antMatchers("/index.html", "/login/authentication", securityProperties.getBrowser().getLoginPage(),
                        "/image").permitAll()//这个url的请求放行
            .anyRequest()//所有请求
            .authenticated()//都要身份认证
            .and()
            .csrf().disable();//关闭csrf
    }
}
