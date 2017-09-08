package com.ufcg.si1;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import exception.ConflictRuntimeException;
import exception.Erro;
import exception.LoginRuntimeException;
import exception.NoContentRuntimeException;

/**
 * De acordo com:
 * https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
 * https://github.com/craftercms/social/blob/master/server/src/main/java/org/craftercms/social/controllers/rest/v3/GlobalDefaultExceptionHandler.java
 * 
 * @author Eri
 */
@ControllerAdvice(basePackages = { "com.ufcg.si1.controller" })
public class GlobalDefaultExceptionHandler {

	public GlobalDefaultExceptionHandler() {}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = Throwable.class)
	public Erro defaultErrorHandler(HttpServletRequest req, Throwable t) {
		if (StringUtils.isEmpty(t.getMessage()))
			return new Erro("Erro desconhecido!");
		return new Erro(t.getMessage());
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(value = LoginRuntimeException.class)
	public Erro loginErrorHandler(HttpServletRequest req, LoginRuntimeException lre) {
		return new Erro(lre.getMessage());
	}

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(value = ConflictRuntimeException.class)
	public Erro conflictErrorHandler(HttpServletRequest req, ConflictRuntimeException cre) {
		return new Erro(cre.getMessage());
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler(value = NoContentRuntimeException.class)
	public Erro noContentErrorHandler(HttpServletRequest req, NoContentRuntimeException ncre) {
		return new Erro(ncre.getMessage());
	}

}
