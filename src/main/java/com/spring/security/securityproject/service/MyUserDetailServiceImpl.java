package com.spring.security.securityproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author chengyl
 * @create 2019-03-16-13:53
 */
@Service
@Slf4j
public class MyUserDetailServiceImpl implements UserDetailsService {

    /**
     * 手机号登录方式
     * @param mobile
     * @return
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        log.info("手机号{}正在执行登录操作", mobile);
        /**
         * 这里暂时把密码写死，实际情况应该是根据用户名去数据库中查询用户信息
         * 原本这里的密码写的是123456，只要前端输入的密码也是123456就能登录成功
         * 但是 spring security 版本在5.0后就要加个 PasswordEncoder
         * 即必须要对前端传过来的明文密码进行加密，加密后的密码和这里的密码一样就能登录成功
         */
        return new User(mobile, "$2a$10$bcjr0XYCFsr0xyRrjhz28uf23gm.CF2mijWeNxyImqkqG19m.di/O", true, true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin, ROLE_USER"));
    }


    /**
     * 用户名密码登录方式
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("{}正在执行登录操作", username);
        /**
         * 这里暂时把密码写死，实际情况应该是根据用户名去数据库中查询用户信息
         * 原本这里的密码写的是123456，只要前端输入的密码也是123456就能登录成功
         * 但是 spring security 版本在5.0后就要加个 PasswordEncoder
         * 即必须要对前端传过来的明文密码进行加密，加密后的密码和这里的密码一样就能登录成功
         */
        return new User(username, "$2a$10$bcjr0XYCFsr0xyRrjhz28uf23gm.CF2mijWeNxyImqkqG19m.di/O", true, true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
