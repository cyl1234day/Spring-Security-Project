package com.spring.security.securityproject.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author chengyl
 * @create 2019-03-15-13:59
 */
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("myfilter init....");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getServletPath();
        System.out.println(path);
        //执行对应的请求
        chain.doFilter(request, response);
        System.out.println("after filter");
    }

    @Override
    public void destroy() {
        System.out.println("myfilter destrory....");
    }
}
