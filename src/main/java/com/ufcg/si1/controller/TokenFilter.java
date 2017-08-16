package com.ufcg.si1.controller;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class TokenFilter extends GenericFilterBean {

	// TODO: como fazer para que essa chave seja variável de ambiente?
	// de maneira que ela será ignorada no .gitignore, mas existirá no heroku?
	public static String mykey = "hightechcursos"; // System.getenv("myKey"); 
	private static int tokenPosition = "Bearer ".length();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		String header = req.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer ")) {
			throw new ServletException("Usuário inválido. Faça login para continuar!");
		}

		// Extraindo somente a string do Token sem o Bearer
		String token = header.substring(tokenPosition);

		// verificar se o token é valido
		try {
			Jwts.parser().setSigningKey(TokenFilter.mykey).parseClaimsJws(token).getBody();
		} catch (SignatureException e) {
			throw new ServletException("Usuário inválido. Faça login para continuar!");
		}

		chain.doFilter(request, response);
	}
}

/*
Properties p = new Properties();
FileInputStream fis = new FileInputStream("LOCAL_ARQUIVO");
p.load(fis);
p.getPropertie("NOME_PROPERTIE");
*/
