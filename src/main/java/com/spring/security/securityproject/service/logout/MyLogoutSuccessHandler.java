package com.spring.security.securityproject.service.logout;

import com.alibaba.fastjson.JSON;
import com.spring.security.securityproject.pojo.LoginResponse;
import com.spring.security.securityproject.pojo.config.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录成功处理器
 * @author chengyl
 * @create 2019-03-21-15:20
 */
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    private SecurityProperties securityProperties;

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String logoutUrl = securityProperties.getBrowser().getLogoutPage();
        if(StringUtils.isEmpty(logoutUrl)) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(JSON.toJSONString(new LoginResponse("成功退出")));
        } else {
            response.sendRedirect(logoutUrl);
        }

    }
}
