package com.spring.security.securityproject.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 图形验证码
 * @author chengyl
 * @create 2019-03-16-22:03
 */
@Data
@AllArgsConstructor
public class ImageCode {

    /** 图片 */
    private BufferedImage image;
    /** 验证码值 */
    private String code;
    /** 过期时间 */
    private LocalDateTime expireTime;

    /**
     * 构造方法传入有效时间间隔
     * @param image
     * @param code
     * @param interval
     */
    public ImageCode(BufferedImage image, String code, int interval) {
        this.image = image;
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
