package com.ufcg.si1.controller;

import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.si1.TokenFilter;
import com.ufcg.si1.model.Administrador;
import com.ufcg.si1.service.AdministradorService;

import exceptions.Erro;
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
	public ResponseEntity<Login> login(@RequestBody Administrador administrador) {

		try {

			Administrador administradorAutenticado = administradorService.login(administrador);

			String token = Jwts.builder()
					.setSubject(administradorAutenticado.getEmail())
					.signWith(SignatureAlgorithm.HS512, TokenFilter.mykey)
					.setExpiration(new Date(System.currentTimeMillis() + TokenFilter.oneMillisDay))
					.compact();

			return new ResponseEntity<Login>(new Login(token, administradorAutenticado), HttpStatus.OK);

		} catch (Throwable t) {

			return new ResponseEntity(new Erro(t.getMessage()), HttpStatus.UNAUTHORIZED);

		}

	}

	@RequestMapping(
			method = RequestMethod.POST, 
			value = "/admin/cadastrar", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Administrador> cadastrar(@RequestBody Administrador administrador) {

		HttpStatus status;
		Administrador administradorCadastrado = null;
		try {
			administradorCadastrado = administradorService.cadastrar(administrador);
			status = HttpStatus.CREATED;
		} catch (Throwable t) {
			status = HttpStatus.NOT_ACCEPTABLE;
		}

		return new ResponseEntity<Administrador>(administradorCadastrado, status);
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
	public ResponseEntity<Collection<Administrador>> listAllAdministradores() {

		Collection<Administrador> administradores = administradorService.buscarTodos();

		if (administradores.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Collection<Administrador>>(administradores, HttpStatus.OK);
	}

}
