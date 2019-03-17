package com.spring.security.securityproject.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 短信验证码
 * @author chengyl
 * @create 2019-03-17-21:57
 */
@Data
public class ValidateCode {

    /** 验证码值 */
    private String code;
    /** 过期时间 */
    private LocalDateTime expireTime;


    /**
     * 构造方法传入有效时间间隔
     * @param code
     * @param interval
     */
    public ValidateCode(String code, int interval) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(interval);
    }

    /**
     * 判断验证码是否还有效
     * @return
     */
    public boolean isValid() {
        //现在的时间早于过期时间 即 有效
        return LocalDateTime.now().isBefore(expireTime);
    }

}
