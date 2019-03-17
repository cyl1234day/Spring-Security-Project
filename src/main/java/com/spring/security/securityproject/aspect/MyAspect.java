package com.spring.security.securityproject.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author chengyl
 * @create 2019-03-15-14:55
 */
@Aspect
//@Component
public class MyAspect {

    @Around("execution(* com.spring.security.securityproject.controller.UserController.*(..))")
    public Object controllerAspect(ProceedingJoinPoint pjp) {

        System.out.println("before execution....");

        //获取方法的参数
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            System.out.println(arg);
        }
        //方法名
        System.out.println(pjp.getSignature().getName());
        //下面两个都是类名
        System.out.println(pjp.getSignature().getDeclaringTypeName());
        System.out.println(pjp.getSignature().getDeclaringType().getName());

        Object o = null;
        try {
            o = pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("catch a exception...");
        }

        System.out.println("after execution...");

        return o;
    }


}
