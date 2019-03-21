package com.spring.security.securityproject.pojo.config;

import com.spring.security.securityproject.constant.SecurityConstants;
import lombok.Data;

/**
 * Session管理相关的配置项
 * @author chengyl
 * @create 2019-03-21-12:40
 */
@Data
public class SessionProperties {

    /** session 失效后跳转的请求路径 */
    private String invalidSessionUrl = SecurityConstants.DEFAULT_SESSION_INVALID_URL;
    /** 同一个用户在系统中的最大Session数 */
    private int maximumSessions = 1;
    /** false表示后登录的会踢掉先登录的；true表示先登录的不允许后面的登录 */
    private boolean maxSessionsPreventsLogin = false;

}
