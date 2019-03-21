package com.spring.security.securityproject.service.session;

import com.alibaba.fastjson.JSON;
import com.spring.security.securityproject.pojo.LoginResponse;
import org.springframework.http.MediaType;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * session 过期的处理类 (主要处理 多次登录的情况)
 * @author chengyl
 * @create 2019-03-21-12:13
 */
public class MyExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {


    public MyExpiredSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }


    @Override
    protected boolean isConcurrency() {
        return true;
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        onSessionInvalid(event.getRequest(), event.getResponse());
    }

}
