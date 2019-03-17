package com.spring.security.securityproject.service.validateCode.sms;

/**
 * 默认的短信发送实现类
 * @author chengyl
 * @create 2019-03-17-22:52
 */
public class DefaultSmsCodeSender implements SmsCodeSender {
    @Override
    public void send(String mobile, String code) {
        System.out.println("向手机号  " + mobile + "  发送验证码  " + code);
    }
}
