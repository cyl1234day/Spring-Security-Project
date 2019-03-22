package com.spring.security.securityproject.pojo.config;

import lombok.Data;

/**
 * OAuth2 相关的配置项
 * @author chengyl
 * @create 2019-03-21-12:40
 */
@Data
public class OAuth2Properties {

    private OAuth2ClientProperties[] clients = {};

}
