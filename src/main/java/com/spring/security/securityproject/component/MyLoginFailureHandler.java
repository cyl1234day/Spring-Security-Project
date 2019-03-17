package com.spring.security.securityproject.component;

import com.alibaba.fastjson.JSON;
import com.spring.security.securityproject.Enum.LoginType;
import com.spring.security.securityproject.pojo.LoginResponse;
import com.spring.security.securityproject.pojo.config.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录成功处理类
 * @author chengyl
 * @create 2019-03-16-18:36
 */
@Slf4j
@Component("myLoginFailureHandler")
public class MyLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public MyLoginFailureHandler() {
        System.out.println("MyLoginFailureHandler 111111111111111111111111111111111");
    }

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("AuthenticationFailure.....");

        if(securityProperties.getBrowser().getLoginType().equals(LoginType.JSON)) {
            //返回JSON格式的错误信息

            LoginResponse res = new LoginResponse(exception.getMessage());
            String result = JSON.toJSONString(res);
            //设置响应类型
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            //设置错误码500
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            PrintWriter writer = response.getWriter();
            writer.write(result);
            writer.flush();
        } else {
            //返回JSON格式的错误信息
            super.onAuthenticationFailure(request, response, exception);
        }
    }

}
