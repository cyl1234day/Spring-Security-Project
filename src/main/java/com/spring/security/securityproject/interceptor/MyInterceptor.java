package com.spring.security.securityproject.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chengyl
 * @create 2019-03-15-14:27
 */
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在进入Controller之前被调用

        System.out.println("preHandle...");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //输出 Controller的类名
        System.out.println(handlerMethod.getBean().getClass().getName());
        //输出 具体执行的方法名
        System.out.println(handlerMethod.getMethod().getName());

        //如果想要在两个方法之间传递参数，把值设置到request域中
        request.setAttribute("time", System.currentTimeMillis());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //执行完Controller之后被调用，如果抛出异常则不会被执行

        System.out.println("postHandle...");
        long start = (long) request.getAttribute("time");
        System.out.println("time use : " + (System.currentTimeMillis()-start));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //执行完Controller之后被调用，无论是否抛出异常都会被执行
        System.out.println("afterCompletion...");
    }
}
