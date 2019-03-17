package com.spring.security.securityproject.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author chengyl
 * @create 2019-03-16-23:44
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
