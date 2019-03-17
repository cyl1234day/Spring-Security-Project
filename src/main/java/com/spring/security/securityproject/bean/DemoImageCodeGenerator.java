package com.spring.security.securityproject.bean;

import com.spring.security.securityproject.pojo.ImageCode;
import com.spring.security.securityproject.service.ValidateCodeGenerator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义的验证码生成逻辑
 * @author chengyl
 * @create 2019-03-17-14:17
 */
//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {
    @Override
    public ImageCode generateImage(HttpServletRequest request) {

        System.out.println("我是自定义的图形验证码...........");
        return null;
    }
}
