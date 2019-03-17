package com.spring.security.securityproject.service.validateCode;

import com.spring.security.securityproject.pojo.ValidateCode;
import com.spring.security.securityproject.pojo.config.SecurityProperties;
import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 默认的 短信验证码接口 实现
 * @author chengyl
 * @create 2019-03-17-13:58
 */
public class MySmsCodeGenerator implements ValidateCodeGenerator {

    private SecurityProperties securityProperties;

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public ValidateCode generateCode(HttpServletRequest request) {
        String smsCode = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new ValidateCode(smsCode, securityProperties.getCode().getSms().getExpireTime());
    }
}
