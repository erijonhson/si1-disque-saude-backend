package com.ufcg.si1;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.GenericFilterBean;

import com.ufcg.si1.exception.LoginRuntimeException;

import io.jsonwebtoken.Jwts;

@Component
@CrossOrigin
public class TokenFilter extends GenericFilterBean {

	public static final long oneMillisDay = 86400000;
	public static String mykey = "hightechcursos"; // System.getenv("myKey"); 
	private static int tokenPosition = "Bearer ".length();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		// HttpServletResponse res = (HttpServletResponse) response;

		String header = req.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer ")) {
			throw new LoginRuntimeException();
		}

		// Extraindo somente a string do Token sem o Bearer
		String token = header.substring(tokenPosition);

		// verificar se o token é valido
		try {
			Jwts.parser().setSigningKey(TokenFilter.mykey).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			throw new LoginRuntimeException();
			// res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuário inválido. Faça login para continuar!");
			// return ;
		}

		chain.doFilter(request, response);
	}
}

