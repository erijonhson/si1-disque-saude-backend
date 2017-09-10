package com.ufcg.si1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ufcg.si1.interceptor.TokenInterceptor;

/**
 * Classe para configuração da aplicação.
 * 
 * https://github.com/ericbreno/projeto-si1-backend/blob/master/src/main/java/ufcg/si/Config.java
 * @author Eric
 */
@Configuration
public class Config extends WebMvcConfigurerAdapter {

	@Autowired
	private TokenInterceptor interceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor);
	}

}