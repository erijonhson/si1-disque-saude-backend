package com.ufcg.si1;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.ufcg.si1.queixa.state.QueixaAberta;
import com.ufcg.si1.queixa.state.QueixaAndamento;
import com.ufcg.si1.queixa.state.QueixaFechada;
import com.ufcg.si1.repository.QueixaStateRepository;

@SpringBootApplication(scanBasePackages={"com.ufcg"})
public class SpringBootRestApiApp extends SpringBootServletInitializer {

	@Bean
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedOrigin("https://disquesaudesi.herokuapp.com");
		config.addAllowedOrigin("http://localhost:8000");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}

	@Bean
	public FilterRegistrationBean filtroJwt() {
		FilterRegistrationBean frb = new FilterRegistrationBean();
		frb.setFilter(new TokenFilter());
		frb.addUrlPatterns("/api/administrador/*");
		frb.addUrlPatterns("/api/*/deletar");
		return frb;
	}

	@Resource(name = "queixaStateRepository")
	QueixaStateRepository queixaStateRepository;

	@Bean
	InitializingBean sendDatabase() {
		return () -> {
			queixaStateRepository.save(new QueixaAberta());
			queixaStateRepository.save(new QueixaAndamento());
			queixaStateRepository.save(new QueixaFechada());
		};
	}

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootRestApiApp.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApiApp.class, args);
	}

}
