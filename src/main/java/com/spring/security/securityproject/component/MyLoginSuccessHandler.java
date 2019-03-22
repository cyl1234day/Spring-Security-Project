package com.spring.security.securityproject.component;

import com.alibaba.fastjson.JSON;
import com.spring.security.securityproject.Enum.LoginType;
import com.spring.security.securityproject.pojo.config.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;

/**
 * 登录成功处理类
 * @author chengyl
 * @create 2019-03-16-18:36
 */
@Slf4j
@Component
public class MyLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices tokenServices;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("AuthenticationSuccess.....");


        String header = request.getHeader("Authorization");
        //如果字段为空 或 不是 basic 开头
        if (header == null || !header.toLowerCase().startsWith("basic ")) {
            throw new UnapprovedClientAuthenticationException("请求头中无Client信息");
        }

        String[] tokens = extractAndDecodeHeader(header);
        assert tokens.length == 2;

        String clientId = tokens[0];
        String clientSecret = tokens[1];

        //由 clientDetailsService 获取 ClientDetails
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if(clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在 " + clientId);
        }
        //校验 密码 是否一致
        if(!StringUtils.equals(clientSecret, clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配 " + clientId);
        }

        /**
         * 构建TokenRequest
         * Map<String, String> requestParameters, String clientId, Collection<String> scope, String grantType
         *
         */
        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");
        //由 tokenRequest 和 clientDetails 创建 oAuth2Request
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        //由 oAuth2Request 和 authentication 创建 oAuth2Authentication
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        //创建 OAuth2AccessToken
        OAuth2AccessToken accessToken = tokenServices.createAccessToken(oAuth2Authentication);

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String result = JSON.toJSONString(accessToken);
        PrintWriter writer = response.getWriter();
        writer.write(result);


/*原先判断返回JSON还是HTML的逻辑
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
*/
    }


    /**
     * 对请求头中的信息进行解码
     */
    private String[] extractAndDecodeHeader(String header)
            throws IOException {

        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        }
        catch (IllegalArgumentException e) {
            throw new BadCredentialsException(
                    "Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");
        //编码的时候是按照 Username:Password 组合进行编码
        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[] { token.substring(0, delim), token.substring(delim + 1) };
    }
}
