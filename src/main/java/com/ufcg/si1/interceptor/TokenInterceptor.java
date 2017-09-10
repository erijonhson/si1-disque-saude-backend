package com.ufcg.si1.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ufcg.si1.exception.LoginRuntimeException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service(value = "tokenService")
public class TokenInterceptor implements HandlerInterceptor {

	private static final String AUTHORIZATION = "Authorization";

	public static final long dayInMillis = 86400000;
	public static String mykey = "hightechcursos"; // System.getenv("myKey"); 
	private static int tokenPosition = "Bearer ".length();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		LoginRequired loginRequired = handlerMethod.getMethod().getAnnotation(LoginRequired.class);
		if (loginRequired != null) {
			String header = request.getHeader(AUTHORIZATION);
			if (header == null || !header.startsWith("Bearer ")) {
				throw new LoginRuntimeException();
			}
			String token = header.substring(tokenPosition);
			try {
				Jwts.parser().setSigningKey(TokenInterceptor.mykey).parseClaimsJws(token).getBody();
			} catch (Exception e) {
				throw new LoginRuntimeException();
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {}

	public static String buildToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.signWith(SignatureAlgorithm.HS512, TokenInterceptor.mykey)
				.setExpiration(new Date(System.currentTimeMillis() + TokenInterceptor.dayInMillis))
				.compact();
	}

}
