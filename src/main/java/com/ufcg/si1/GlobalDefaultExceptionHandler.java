package com.ufcg.si1;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ufcg.si1.exception.ConflictRuntimeException;
import com.ufcg.si1.exception.Erro;
import com.ufcg.si1.exception.LoginRuntimeException;
import com.ufcg.si1.exception.NotFoundRuntimeException;

/**
 * De acordo com:
 * https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
 * 
 * @author Eri
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	public GlobalDefaultExceptionHandler() {}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = {Throwable.class, Exception.class})
	@ResponseBody
	public Erro defaultErrorHandler(HttpServletRequest req, Throwable t) {
		if (StringUtils.isEmpty(t.getMessage()))
			return new Erro("Erro desconhecido!");
		return new Erro(t.getMessage());
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(value = LoginRuntimeException.class)
	@ResponseBody
	public Erro loginErrorHandler(HttpServletRequest req, LoginRuntimeException lre) {
		return new Erro(lre.getMessage());
	}

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(value = ConflictRuntimeException.class)
	@ResponseBody
	public Erro conflictErrorHandler(HttpServletRequest req, ConflictRuntimeException cre) {
		return new Erro(cre.getMessage());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = NotFoundRuntimeException.class)
	@ResponseBody
	public Erro noContentErrorHandler(HttpServletRequest req, NotFoundRuntimeException nfre) {
		return new Erro(nfre.getMessage());
	}

}
