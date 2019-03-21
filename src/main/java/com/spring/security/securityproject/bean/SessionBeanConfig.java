package com.spring.security.securityproject.bean;

import com.spring.security.securityproject.pojo.config.SecurityProperties;
import com.spring.security.securityproject.service.session.MyExpiredSessionStrategy;
import com.spring.security.securityproject.service.session.MyInvalidSessionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * @author chengyl
 * @create 2019-03-17-14:12
 */
@Configuration
public class SessionBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public MyExpiredSessionStrategy expiredSessionStrategy() {
        System.out.println(securityProperties.getBrowser().getSession());
        return new MyExpiredSessionStrategy(securityProperties.getBrowser().getSession().getInvalidSessionUrl());
    }


    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public MyInvalidSessionStrategy invalidSessionStrategy() {
        return new MyInvalidSessionStrategy(securityProperties.getBrowser().getSession().getInvalidSessionUrl());
    }

}
