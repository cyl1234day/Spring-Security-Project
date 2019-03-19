package com.spring.security.securityproject.pojo.config;

import com.spring.security.securityproject.Enum.LoginType;
import lombok.Data;

/**
 * 同样是读取 yml 配置文件的配置类，如果不指定 prefix
 * 会自动根据 属性名称 和 yml 中的配置项进行匹配
 * 名字不一致则无法读取
 * @author chengyl
 * @create 2019-03-16-16:11
 */
@Data
public class BrowserProperties {

    //指定一个默认跳转的页面，如果用户没有指定跳转页面
    private String loginPage = "index.html";

    private LoginType loginType = LoginType.JSON;

    private int rememberMeSeconds = 3600;
}
