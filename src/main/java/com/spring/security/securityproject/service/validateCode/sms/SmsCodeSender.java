package com.spring.security.securityproject.service.validateCode.sms;

/**
 * 用于 实现不同运营商 发送短信实现的接口
 * @author chengyl
 * @create 2019-03-17-22:50
 */
public interface SmsCodeSender {

    void send(String mobile, String code);
}
