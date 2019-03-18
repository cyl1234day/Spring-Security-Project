package com.spring.security.securityproject.service.validateCode.sms;

import com.spring.security.securityproject.pojo.ValidateCode;
import com.spring.security.securityproject.service.validateCode.AbstractValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chengyl
 * @create 2019-03-18-12:25
 */
@Component
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    @Autowired
    private SmsCodeSender sender;

    @Override
    protected void send(HttpServletRequest request, HttpServletResponse response, ValidateCode code) throws Exception {

        String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");

        sender.send(mobile, code.getCode());
    }
}
