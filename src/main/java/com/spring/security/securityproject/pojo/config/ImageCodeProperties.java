package com.spring.security.securityproject.pojo.config;

import lombok.Data;

/**
 * 图形验证码的参数配置项
 * @author chengyl
 * @create 2019-03-17-10:09
 */
@Data
public class ImageCodeProperties extends SmsCodeProperties{
    /** 图片宽度 */
    private int width = 67;
    /** 图片高度 */
    private int height = 23;

    public ImageCodeProperties() {
        //设置默认的图形验证码为4为
        this.setLength(4);
    }
}
