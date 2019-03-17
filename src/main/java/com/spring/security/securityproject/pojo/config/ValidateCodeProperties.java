package com.spring.security.securityproject.pojo.config;

import lombok.Data;

/**
 * 验证码配置类：图形验证码 和 之后的短信验证码
 * @author chengyl
 * @create 2019-03-17-10:12
 */
@Data
public class ValidateCodeProperties {

    private ImageCodeProperties image = new ImageCodeProperties();

}
