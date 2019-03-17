package com.spring.security.securityproject.controller;

import com.spring.security.securityproject.pojo.ImageCode;
import com.spring.security.securityproject.pojo.ValidateCode;
import com.spring.security.securityproject.service.validateCode.MyImageCodeGenerator;
import com.spring.security.securityproject.service.validateCode.MySmsCodeGenerator;
import com.spring.security.securityproject.service.validateCode.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chengyl
 * @create 2019-03-16-22:13
 */
@RestController
@RequestMapping("/image")
public class ImageCodeController {

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    @Autowired
    private MyImageCodeGenerator imageCodeGenerator;
    @Autowired
    private MySmsCodeGenerator smsCodeGenerator;
    @Autowired
    private SmsCodeSender sender;

    /***
     * 生成验证码有三个步骤：
     * 1、根据随机数生成图片
     * 2、把随机数存到 Session 域中
     * 3、把生成的图片写到接口的响应中
     * @param request
     * @param response
     * @return
     */
    @GetMapping //注意：这个请求的 URL 要在配置类中放行
    public void getImage(HttpServletRequest request, HttpServletResponse response) {

        ImageCode imageCode = imageCodeGenerator.generateCode(request);

        request.getSession().setAttribute(SESSION_KEY, imageCode);
        //使用 Spring框架 的工具类操作session
        //需要引入 social 依赖

        try {
            ImageIO.write(imageCode.getImage(), "jpeg", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @GetMapping("/sms") //注意：这个请求的 URL 要在配置类中放行
    public void getSms(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException {

        ValidateCode validateCode = smsCodeGenerator.generateCode(request);

        request.getSession().setAttribute(SESSION_KEY, validateCode);
        //使用 Spring框架 的工具类操作session
        //需要引入 social 依赖

        //必须要从session中取出值，否则抛异常
        String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");

        sender.send(mobile, validateCode.getCode());

    }

}
