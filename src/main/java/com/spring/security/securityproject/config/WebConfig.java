package com.spring.security.securityproject.config;

import com.spring.security.securityproject.filter.MyFilter;
import com.spring.security.securityproject.interceptor.MyInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author chengyl
 * @create 2019-03-15-14:08
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Bean
    public FilterRegistrationBean<MyFilter> MyFilter() {
        //把自定义的filter注册到容器中，并可以指定过滤的url
        FilterRegistrationBean register = new FilterRegistrationBean();
        register.setFilter(new MyFilter());
        register.addUrlPatterns("/user/1",
                                "/user/2",
                                "/user/3");
        return register;
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.registerCallableInterceptors();
        configurer.registerDeferredResultInterceptors();
    }

    //    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new MyInterceptor()).excludePathPatterns("");
//
//    }
}
