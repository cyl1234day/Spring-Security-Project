package com.spring.security.securityproject.Enum;

import com.spring.security.securityproject.constant.SecurityConstants;

/**
 * @author chengyl
 * @create 2019-03-18-20:01
 */
public enum ValidateCodeType {
    /**
     * 短信验证码 smsCode
     */
    SMS(SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS),
    /**
     * 图片验证码 imageCode
     */
    IMAGE(SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE);

    /**
     * 校验时从请求中获取的参数的名字
     * @return
     */
    private String name;

    ValidateCodeType(String string) {
        this.name = string;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

//    ValidateCodeType.valueOf("这里面的值就是ENUM的类型（大写的那部分）")


}