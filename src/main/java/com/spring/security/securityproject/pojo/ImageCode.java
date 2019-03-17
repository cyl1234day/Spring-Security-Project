package com.spring.security.securityproject.pojo;

import lombok.Data;

import java.awt.image.BufferedImage;

/**
 * 图形验证码
 * @author chengyl
 * @create 2019-03-16-22:03
 */
@Data
public class ImageCode extends ValidateCode {

    /** 图片 */
    private BufferedImage image;

    /**
     * 构造方法传入有效时间间隔
     * @param image
     * @param code
     * @param interval
     */
    public ImageCode(BufferedImage image, String code, int interval) {
        super(code, interval);
        this.image = image;
    }

}
