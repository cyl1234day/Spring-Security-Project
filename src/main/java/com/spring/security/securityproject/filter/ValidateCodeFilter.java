package com.spring.security.securityproject.filter;

import com.spring.security.securityproject.controller.ValidateCodeController;
import com.spring.security.securityproject.exception.ValidateCodeException;
import com.spring.security.securityproject.pojo.ValidateCode;
import com.spring.security.securityproject.pojo.config.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 用于验证输入的图形验证码是否正确的过滤器
 * OncePerRequestFilter 是 一次请求只执行一次过滤的过滤器
 *
 * @author chengyl
 * @create 2019-03-16-23:39
 */
@Slf4j
@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    private SecurityProperties securityProperties;

    //Spring 提供用于匹配 URL 的工具类
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    private Set<String> urlSet = new HashSet<>();


    /**
     * 实现InitializingBean接口，重写该方法会在创建Bean之后调用
     */
    @Override
    public void afterPropertiesSet(){
        String[] urls = StringUtils.split(securityProperties.getCode().getImage().getUrl(), ",");
        if(urls != null) {
            for(String url : urls) {
                urlSet.add(url);
            }
        }
        urlSet.add("/authentication/form");
        urlSet.add("/authentication/sms");
    }

//    public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
//        this.failureHandler = failureHandler;
//    }

    public ValidateCodeFilter() {
        System.out.println("ValidateCodeFilter 222222222222222222222222222222");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //获取请求URI
        String uri = request.getRequestURI();
        log.info("ValidateCodeFilter   uri: {}", uri);

        boolean isMatch = false;

        for(String url : urlSet) {
            if(pathMatcher.match(url, uri)) {
                isMatch = true;
                break;
            }
        }

        //判断是否是表单提交的登录请求以及请求方式
        if(isMatch) {
            log.info("图形验证码校验");
            System.out.println(failureHandler);
            try {
                checkImageCode(request);
            } catch (ValidateCodeException e) {
                //使用自定义的登录失败处理类来处理错误信息
                failureHandler.onAuthenticationFailure(request, response, e);
                //校验失败就返回
                return;
            }

        }
        //继续执行后续的过滤器
        filterChain.doFilter(request, response);
    }


    /**
     * 校验验证码
     * @param request
     * @throws ValidateCodeException
     */
    private void checkImageCode(HttpServletRequest request) throws ValidateCodeException {
        ValidateCode validateCode = (ValidateCode) request.getSession().getAttribute(ValidateCodeController.SESSION_KEY);

//        使用工具类获取request中的参数值
//        String imageCode = ServletRequestUtils.getStringParameter(request, "imageCode");

        if(validateCode == null) {
            throw new ValidateCodeException("验证码不存在！");
        }
        if(StringUtils.isEmpty(request.getParameter("imageCode"))) {
            throw new ValidateCodeException("验证码为空！");
        }
        if(!validateCode.isValid()) {
            //要把过期的验证码从Session域中删掉
            request.getSession().removeAttribute(ValidateCodeController.SESSION_KEY);
            throw new ValidateCodeException("验证码过期！");
        }
        if(!StringUtils.equalsIgnoreCase(validateCode.getCode(), request.getParameter("imageCode"))) {
            //要把错误的验证码从Session域中删掉
            request.getSession().removeAttribute(ValidateCodeController.SESSION_KEY);
            throw new ValidateCodeException("验证码错误！");
        }
        //使用过的验证码从Session域中删掉
        request.getSession().removeAttribute(ValidateCodeController.SESSION_KEY);
    }


}
