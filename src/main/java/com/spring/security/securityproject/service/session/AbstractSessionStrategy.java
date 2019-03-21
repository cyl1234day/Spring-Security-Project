package com.spring.security.securityproject.service.session;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.spring.security.securityproject.pojo.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

/**
 * Session 失效处理的抽象类
 */
@Slf4j
public class AbstractSessionStrategy {

	/**
	 * 跳转的url
	 */
	private String destinationUrl;
	/**
	 * 重定向策略
	 */
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public AbstractSessionStrategy(String invalidSessionUrl) {
		Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl), "url must start with '/' or with 'http(s)'");
		this.destinationUrl = invalidSessionUrl;
	}


	protected void onSessionInvalid(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String sourceUrl = request.getRequestURI();
		String targetUrl;

		/**
		 * Returns the current session associated with this request, or if the
		 * request does not have a session, creates one.
		 * 这句话很重要，如果session失效了，会创建一个新的session
		 * 否则会不停地跳转到这里，因为session一直是失效的
		 */
		request.getSession();

		if (StringUtils.endsWithIgnoreCase(sourceUrl, ".html")) {
			targetUrl = destinationUrl;
			log.info("session失效,跳转到"+targetUrl);
			redirectStrategy.sendRedirect(request, response, targetUrl);
		}else{
			String message = "session已失效";
			if(isConcurrency()){
				message = message + "，有可能是并发登录导致的";
			}
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			response.getWriter().write(JSON.toJSONString(new LoginResponse(message)));
		}
		
	}

	/**
	 * session失效是否是并发导致的
	 * 并发登录的子类需要覆盖这个方法
	 * @return
	 */
	protected boolean isConcurrency() {
		return false;
	}

}
