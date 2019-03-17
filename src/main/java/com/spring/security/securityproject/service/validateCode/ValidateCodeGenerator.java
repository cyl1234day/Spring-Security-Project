package com.spring.security.securityproject.service.validateCode;

import com.spring.security.securityproject.pojo.ValidateCode;

import javax.servlet.http.HttpServletRequest;

/**
 * 实现验证码校验逻辑的接口
 * @author chengyl
 * @create 2019-03-17-13:56
 */
public interface ValidateCodeGenerator {

    ValidateCode generateCode(HttpServletRequest request);

}
