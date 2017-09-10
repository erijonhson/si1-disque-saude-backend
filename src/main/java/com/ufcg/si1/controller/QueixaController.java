package com.ufcg.si1.controller;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.si1.interceptor.LoginRequired;
import com.ufcg.si1.model.Comentario;
import com.ufcg.si1.model.Queixa;
import com.ufcg.si1.service.QueixaService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class QueixaController {

	@Resource(name = "queixaService")
	QueixaService queixaService;

	@RequestMapping(
			value = "/queixa/", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Queixa> listAllQueixas() {
		return queixaService.buscarTodos();
	}

	@RequestMapping(
			value = "/queixa/", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Queixa abrirQueixa(@RequestBody Queixa queixa) {
		return queixaService.cadastrar(queixa);
	}

	@RequestMapping(
			value = "/queixa/{id}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Queixa consultarQueixa(@PathVariable("id") long id) {
		return queixaService.buscarPorId(id);
	}

	@LoginRequired
	@RequestMapping(
			value = "/administrador/queixa/", 
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Queixa updateQueixa(@RequestBody Queixa queixa) {
		return queixaService.atualizar(queixa);
	}

	@LoginRequired
	@RequestMapping(
			value = "/administrador/queixa/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteQueixa(@PathVariable("id") long id) {
		queixaService.deletar(id);
	}

	@LoginRequired
	@RequestMapping(
			value = "/administrador/queixa/fechamento", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Queixa mudaStateQueixa(@RequestBody Queixa queixa) {
		return queixaService.mudaStateQueixa(queixa);
	}

	@RequestMapping(
			value = "/queixa/comentario/{idQueixa}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Comentario adicionaComentario(
			@PathVariable("idQueixa") Long idQueixa, 
			@RequestBody Comentario comentario) {
		return queixaService.adicionarComentario(idQueixa, comentario);
	}

	@RequestMapping(
			value = "/queixa/comentario/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Comentario> buscaComentariosDaQueixa(@PathVariable("id") Long idQueixa) {
		return queixaService.buscarComentariosDeQueixa(idQueixa);
	}

}
