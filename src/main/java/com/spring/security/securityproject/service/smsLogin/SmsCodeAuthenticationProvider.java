package com.spring.security.securityproject.service.smsLogin;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * AuthenticationManager 根据传递过来的Token类型来获取合适的 AuthenticationProvider
 * @author chengyl
 * @create 2019-03-18-15:35
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //得到传过来的Token
        SmsCodeAuthenticationToken smsCodeAuthenticationToken = (SmsCodeAuthenticationToken) authentication;
        //获取手机号
        String mobile = (String) smsCodeAuthenticationToken.getPrincipal();
        //根据手机号查找用户
        UserDetails user = userDetailsService.loadUserByUsername(mobile);

        if(user == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        //找到用户后，返回登录成功信息
        SmsCodeAuthenticationToken result = new SmsCodeAuthenticationToken(mobile, user.getAuthorities());
        //把之前的认证信息重新记录一下
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {

        /**
         * a.class.isAssignableFrom(b.class)
         * a 是否是 b 的父类或同类
         * 是则返回true
         */
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
