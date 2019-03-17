package com.spring.security.securityproject.pojo.config;

import com.spring.security.securityproject.pojo.ValidateCode;
import lombok.Data;

/**
 * 验证码配置类：图形验证码 和 之后的短信验证码
 * @author chengyl
 * @create 2019-03-17-10:12
 */
@Data
public class ValidateCodeProperties {

    private SmsCodeProperties sms = new SmsCodeProperties();

    private ImageCodeProperties image = new ImageCodeProperties();

}
