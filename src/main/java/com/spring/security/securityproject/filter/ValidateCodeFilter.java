package com.spring.security.securityproject.filter;

import com.spring.security.securityproject.Enum.ValidateCodeType;
import com.spring.security.securityproject.constant.SecurityConstants;
import com.spring.security.securityproject.exception.ValidateCodeException;
import com.spring.security.securityproject.pojo.config.SecurityProperties;
import com.spring.security.securityproject.service.validateCode.ValidateCodeProcessor;
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
import java.util.HashMap;
import java.util.Map;
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

    @Autowired
    private Map<String, ValidateCodeProcessor> processors;

    //Spring 提供用于匹配 URL 的工具类
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    //用于存储 URL 和 需要进行的验证码类型
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();


    /**
     * 实现InitializingBean接口，重写该方法会在创建Bean之后调用
     */
    @Override
    public void afterPropertiesSet() {
        //加入配置的 URL 路径
        String[] urls = StringUtils.split(securityProperties.getCode().getImage().getUrl(), ",");
        if (urls != null) {
            for (String url : urls) {
                urlMap.put(url, ValidateCodeType.IMAGE);
            }
        }

        urls = StringUtils.split(securityProperties.getCode().getSms().getUrl(), ",");
        if (urls != null) {
            for (String url : urls) {
                urlMap.put(url, ValidateCodeType.SMS);
            }
        }
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_SMS, ValidateCodeType.SMS);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //获取请求URI
        String uri = request.getRequestURI();
        log.info("ValidateCodeFilter   uri: {}", uri);

        //验证的类型
        ValidateCodeType type = null;

        boolean isMatch = false;

//        if (urlMap.containsKey(uri)) {
//            type = urlMap.get(uri);
//            isMatch = true;
//        }
        //更加严谨的写法
        Set<String> urls = urlMap.keySet();
        for(String url : urls) {
            if(pathMatcher.match(url, uri)) {
                type = urlMap.get(url);
                isMatch = true;
            }
        }

        //判断是否是表单提交的登录请求以及请求方式
        if (isMatch) {
            log.info("图形验证码校验");
            System.out.println(failureHandler);
            try {
                checkCode(request, type);
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
     *
     * @param request
     * @throws ValidateCodeException
     */
    private void checkCode(HttpServletRequest request, ValidateCodeType type) throws ValidateCodeException {
        ValidateCodeProcessor processor = processors.get(type.toString().toLowerCase() + "CodeProcessor");
        if (processor == null) {
            throw new ValidateCodeException("验证码校验器不存在");
        }
        processor.validate(request);
    }
}