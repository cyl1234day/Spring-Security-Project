package com.spring.security.securityproject.service.validateCode;

import org.springframework.web.bind.ServletRequestBindingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 校验码处理器，封装不同校验码的处理逻辑
 * @author chengyl
 * @create 2019-03-18-11:23
 */
public interface ValidateCodeProcessor {

    /** SESSION 前缀 */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 生成校验码
     * @param request
     */
    void create(HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 验证校验码
     * @param request
     */
    void validate(HttpServletRequest request) throws Exception;

}
