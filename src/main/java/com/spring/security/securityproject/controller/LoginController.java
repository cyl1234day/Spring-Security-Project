package com.spring.security.securityproject.controller;

import com.spring.security.securityproject.pojo.LoginResponse;
import com.spring.security.securityproject.pojo.config.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自己配置的登录请求处理，可以根据需求返回JSON信息给前端
 * @author chengyl
 * @create 2019-03-16-15:42
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    /**
     * Spring框架会把引发跳转的请求暂时存放到session里，
     * 方法如果要用到请求可以从这个对象中取
     */
    private RequestCache requestCache = new HttpSessionRequestCache();

    /**
     * Spring框架用于重定向的类
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Autowired
    private SecurityProperties securityProperties;



    /**
     * 需要在 WebSecurityConfigurerAdapter 中配置，认证是请求 HTML 还是 访问这个controller
     * 当需要身份认证的情况，请求这个Controller
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/authentication")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)//返回一个未授权的401状态码
    public LoginResponse login(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //可以从缓存中取出原来的引发跳转请求
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if(savedRequest != null) {
            //获取原来引发跳转的URL
            String targetUrl = savedRequest.getRedirectUrl();
            log.info("引发跳转的请求是：{}", targetUrl);
            //如果一开始的请求是以html结尾的
            if(StringUtils.endsWithIgnoreCase(targetUrl,".html")) {
                //跳转到自己配置的登录页
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
            }

        }

        return new LoginResponse("请先登录");
    }

}
