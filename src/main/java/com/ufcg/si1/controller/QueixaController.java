package com.ufcg.si1.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.si1.model.Queixa;
import com.ufcg.si1.service.GenericService;
import com.ufcg.si1.service.QueixaService;
import com.ufcg.si1.service.QueixaServiceImpl;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class QueixaController {

	@Autowired
	GenericService<Queixa> queixaService = new QueixaServiceImpl();

	@RequestMapping(
			value = "/queixa/", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Queixa>> listAllQueixas() {

		Collection<Queixa> queixas = queixaService.buscarTodos();

		if (queixas.isEmpty()) {
			return new ResponseEntity(
					new Error("Queixa não encontrada"), 
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Collection<Queixa>>(queixas, HttpStatus.OK);
	}

	@RequestMapping(
			value = "/queixa/", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Queixa> abrirQueixa(@RequestBody Queixa queixa) {

		try {
			QueixaFachadaSingleton.getInstance().preparaQueixa(queixa);
			Queixa queixaCadastrada = queixaService.cadastrar(queixa);
			return new ResponseEntity<Queixa>(queixaCadastrada, HttpStatus.CREATED);
		} catch (RuntimeException re) {
			return new ResponseEntity(HttpStatus.CONFLICT);
		}

	}

	@RequestMapping(
			value = "/queixa/{id}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Queixa> consultarQueixa(@PathVariable("id") long id) {

		Queixa queixa = queixaService.buscarPorId(id);

		if (queixa == null) {
			return new ResponseEntity(
					new Error("Queixa não encontrada"), 
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Queixa>(queixa, HttpStatus.OK);
	}

	@RequestMapping(
			value = "/queixa/{id}", 
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Queixa> updateQueixa(@PathVariable("id") long id, @RequestBody Queixa queixa) {

		Queixa currentQueixa = queixaService.buscarPorId(id);

		if (currentQueixa == null) {
			return new ResponseEntity(
					new Error("Não é possível atualizar. Queixa não encontrada."),
					HttpStatus.NOT_FOUND);
		}

		currentQueixa.setDescricao(queixa.getDescricao());
		// TODO: como fica a situação de colocar Queixa em andamento?
		// currentQueixa.setSituacao(queixa.getSituacao());

		try {
			queixaService.atualizar(currentQueixa);
		} catch(RuntimeException re) {
			return new ResponseEntity(
					new Error("Não é possível atualizar. Erro interno no sistema."),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Queixa>(currentQueixa, HttpStatus.OK);
	}

	@RequestMapping(
			value = "/queixa/{id}", 
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Queixa> deleteQueixa(@PathVariable("id") long id) {

		//TODO: bad smell de nome de variavel, que estava user
		Queixa queixaEncontrada = queixaService.buscarPorId(id);
		if (queixaEncontrada == null) {
			return new ResponseEntity(
					new Error("Não é possível deletar. Queixa não encontrada."),
					HttpStatus.NOT_FOUND);
		}
		queixaService.deletar(queixaEncontrada);

		//TODO badsmel not content
		return new ResponseEntity<Queixa>(HttpStatus.OK);
	}

	@RequestMapping(
			value = "/queixa/fechamento", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Queixa> fecharQueixa(@RequestBody Queixa queixa) {

		// TODO: bad smell: variavel public queixaAFechar.situacao
		Queixa queixaFechada = null;

		try {
			queixaFechada = ((QueixaService) queixaService).fecharQueixa(queixa);
			return new ResponseEntity<Queixa>(queixaFechada, HttpStatus.OK);
		} catch (RuntimeException re) {
			return new ResponseEntity(
					new Error("Não é possível fechar Queixa. Erro interno no sistema."),
					HttpStatus.NOT_MODIFIED);
		}

	}

}
