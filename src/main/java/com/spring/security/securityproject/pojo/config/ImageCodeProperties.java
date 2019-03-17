package com.spring.security.securityproject.pojo.config;

import lombok.Data;

/**
 * 图形验证码的参数配置项
 * @author chengyl
 * @create 2019-03-17-10:09
 */
@Data
public class ImageCodeProperties {
    /** 图片宽度 */
    private int width = 67;
    /** 图片高度 */
    private int height = 23;
    /** 图片验证码位数 */
    private int length = 4;
    /** 图片过期时间 */
    private int expireTime = 60;
    /** url */
    private String url;
}
