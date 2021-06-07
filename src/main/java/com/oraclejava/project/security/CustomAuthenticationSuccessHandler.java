package com.oraclejava.project.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	// security AuthenticationSuccessHandler method에서 인자로 넘겼던 변수
	private String username;
	private String defaultUrl;
	private RequestCache requestCache = new HttpSessionRequestCache();
	private RedirectStrategy redirectStragtegy = new DefaultRedirectStrategy();
	
	// 로그를 남겨줌
	private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDefaultUrl() {
		return defaultUrl;
	}

	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}

	public CustomAuthenticationSuccessHandler(String username, String defaultUrl) {
		this.username = username;
		this.defaultUrl = defaultUrl;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
			Authentication authentication) throws IOException, ServletException {
				
		// 에러세션 지우기
		clearAuthenticationAttributes(request);
		// Redirect URL 작업.
		resultRedirectStrategy(request, response, authentication);
		
	}
	
	protected void resultRedirectStrategy(HttpServletRequest request, HttpServletResponse response, 
			Authentication authentication) throws IOException, ServletException {
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		/* 세션에 있는 SavedRequest의 객체 정보가 있는지 확인하여 있다면 세션에 담긴 이전 페이지에 대한 정보를 
		getRedirectUrl() 메서드를 통해서 받아올 것 */
		
		if ( savedRequest != null ) {
			String targetUrl = savedRequest.getRedirectUrl();
			logger.info( " GO !!! savedRequest.getRedirectUrl : " + targetUrl );
			redirectStragtegy.sendRedirect(request, response, targetUrl);
		}else {
			logger.info( " GO !! savedRequest.getRedirectUrl : " + defaultUrl );
			redirectStragtegy.sendRedirect(request, response, defaultUrl);
		}
	}
	
	// 남아있는 에러세션이 있다면 지워준다.
	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if( session == null ) return ;
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
}
