package com.ufcg.si1.controller;

import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.si1.TokenFilter;
import com.ufcg.si1.model.Administrador;
import com.ufcg.si1.service.AdministradorService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AdministradorController {

	@Resource(name = "administradorService")
	AdministradorService administradorService;

	@RequestMapping(
			method = RequestMethod.POST, 
			value = "/admin/login", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Login login(@RequestBody Administrador administrador) {

		Administrador administradorAutenticado = administradorService.login(administrador);

		String token = Jwts.builder()
				.setSubject(administradorAutenticado.getEmail())
				.signWith(SignatureAlgorithm.HS512, TokenFilter.mykey)
				.setExpiration(new Date(System.currentTimeMillis() + TokenFilter.oneMillisDay))
				.compact();

		return new Login(token, administradorAutenticado);

	}

	@RequestMapping(
			method = RequestMethod.POST, 
			value = "/admin/cadastrar", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Administrador cadastrar(@RequestBody Administrador administrador) {
		return administradorService.cadastrar(administrador);
	}

	private class Login {

		public String token;
		public Administrador administrador;

		public Login(String token, Administrador administrador) {
			this.token = token;
			this.administrador = administrador;
		}

	}

	// TODO: para efeito de testes. A versão final não deve ter o serviço abaixo.
	@RequestMapping(
			value = "/administrador/", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Administrador> listAllAdministradores() {
		return administradorService.buscarTodos();
	}

}
