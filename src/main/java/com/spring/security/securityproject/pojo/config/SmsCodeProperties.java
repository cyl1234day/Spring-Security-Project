package com.spring.security.securityproject.pojo.config;

import lombok.Data;

/**
 * @author chengyl
 * @create 2019-03-17-22:05
 */
@Data
public class SmsCodeProperties {

    /** 验证码位数 */
    private int length = 4;
    /** 过期时间 */
    private int expireTime = 60;
    /** url */
    private String url;

}
