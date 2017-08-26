package com.ufcg.si1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.ufcg.si1.controller.TokenFilter;

@SpringBootApplication(scanBasePackages={"com.ufcg"})
public class SpringBootRestApiApp extends SpringBootServletInitializer {

	@Bean
	public FilterRegistrationBean filtroJwt() {
		FilterRegistrationBean frb = new FilterRegistrationBean();
		frb.setFilter(new TokenFilter());
		frb.addUrlPatterns("/api/administrador/*");
		frb.addUrlPatterns("/api/*/deletar");
		return frb;
	}

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootRestApiApp.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApiApp.class, args);
	}

}
