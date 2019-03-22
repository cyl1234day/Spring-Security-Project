package com.spring.security.securityproject.provider;

import com.spring.security.securityproject.pojo.config.OAuth2ClientProperties;
import com.spring.security.securityproject.pojo.config.SecurityProperties;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * @author chengyl
 * @create 2019-03-21-16:30
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityProperties securityProperties;


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
    }

    /**
     * 配置第三方应用
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //常见的配置信息
        //clients.inMemory().withClient().secret().accessTokenValiditySeconds().authorizedGrantTypes().scopes();
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        if(ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())) {
            for(OAuth2ClientProperties properties : securityProperties.getOauth2().getClients()) {
                builder.withClient(properties.getClientId())//clientId
                        .secret(properties.getClientSecret())//clientSecret
                        .accessTokenValiditySeconds(properties.getAccessTokenValiditySeconds())//Token过期时间
                        .authorizedGrantTypes("refresh_token", "password")//OAuth模式
                        .scopes("all", "write", "read");//scope
            }
        }
    }

    /**
     * 就是配置 申请令牌 的 Controller
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
                //.tokenStore()//配置Token的存储方式
    }
}
