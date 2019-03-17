package com.spring.security.securityproject.component;

import com.alibaba.fastjson.JSON;
import com.spring.security.securityproject.Enum.LoginType;
import com.spring.security.securityproject.pojo.config.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
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
@Component
public class MyLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private SecurityProperties securityProperties;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("AuthenticationSuccess.....");

        if(securityProperties.getBrowser().getLoginType().equals(LoginType.JSON)) {
            //返回JSON格式的用户信息
            //设置响应类型
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            String result = JSON.toJSONString(authentication);
            PrintWriter writer = response.getWriter();
            writer.write(result);
            writer.flush();
        } else {
            //跳转到原先访问的请求Controller
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
