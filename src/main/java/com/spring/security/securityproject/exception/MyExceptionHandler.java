package com.spring.security.securityproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chengyl
 * @create 2019-03-15-12:11
 */
@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class) //要处理的异常类型
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) //指定响应码
    public Map<String, Object> handlerException(UsernameNotFoundException ex) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", ex.getMessage());
        return result;
    }

}
