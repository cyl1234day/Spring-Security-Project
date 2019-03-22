package com.spring.security.securityproject.pojo.config;

import lombok.Data;

/**
 * OAuth2Client 相关的配置项
 * @author chengyl
 * @create 2019-03-21-12:40
 */
@Data
public class OAuth2ClientProperties {

    private String clientId;
    private String clientSecret;
    private int accessTokenValiditySeconds = 60*60*2;

}
