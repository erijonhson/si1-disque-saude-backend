package com.ufcg.si1.controller;

import java.util.Collection;
import java.util.List;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.ufcg.si1.model.Queixa;
import com.ufcg.si1.repository.CidadaoRepository;
import com.ufcg.si1.repository.EnderecoRepository;
import com.ufcg.si1.service.CidadaoService;
import com.ufcg.si1.service.EnderecoService;
import com.ufcg.si1.service.EnderecoServiceImpl;
import com.ufcg.si1.service.QueixaService;
import com.ufcg.si1.service.QueixaServiceImpl;
import com.ufcg.si1.util.CustomErrorType;

import exceptions.ObjetoInvalidoException;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class QueixaController {

	@Autowired
	QueixaService queixaService;

	@RequestMapping(
			value = "/queixa/", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Queixa>> listAllQueixas() {
		
		Collection<Queixa> queixas = queixaService.findAllQueixas();

		if (queixas.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Collection<Queixa>>(queixas, HttpStatus.OK);
	}

	@RequestMapping(value = "/queixa/", method = RequestMethod.POST)
	public ResponseEntity<?> abrirQueixa(@RequestBody Queixa queixa, UriComponentsBuilder ucBuilder) {

		try {
			queixaService.saveQueixa(queixa);
		} catch (ObjetoInvalidoException e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Queixa>(queixa, HttpStatus.CREATED);
	}

	@RequestMapping(
			value = "/queixa/{id}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Queixa> consultarQueixa(@PathVariable("id") long id) {

		Queixa queixa = queixaService.findById(id);
		if (queixa == null) {
			return new ResponseEntity(new CustomErrorType("Queixa with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Queixa>(queixa, HttpStatus.OK);
	}

	@RequestMapping(value = "/queixa/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Queixa> updateQueixa(@PathVariable("id") long id, @RequestBody Queixa queixa) {

		Queixa currentQueixa = queixaService.findById(id);

		if (currentQueixa == null) {
			return new ResponseEntity(new CustomErrorType("Unable to upate. Queixa with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentQueixa.setDescricao(queixa.getDescricao());
		// TODO: questão de feixar a queixa, como fica? Na minha percepção uma
		// queixa pode ter vários comentários, não apenas um
		// currentQueixa.setComentario(queixa.getComentario());

		queixaService.updateQueixa(currentQueixa);
		return new ResponseEntity<Queixa>(currentQueixa, HttpStatus.OK);
	}

	@RequestMapping(value = "/queixa/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Queixa> deleteQueixa(@PathVariable("id") long id) {

		Queixa user = queixaService.findById(id);
		if (user == null) {
			return new ResponseEntity(new CustomErrorType("Unable to delete. Queixa with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		queixaService.deleteQueixaById(id);
		return new ResponseEntity<Queixa>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/queixa/fechamento", method = RequestMethod.POST)
	public ResponseEntity<?> fecharQueixa(@RequestBody Queixa queixaAFechar) {
		queixaAFechar.situacao = Queixa.FECHADA;
		queixaService.updateQueixa(queixaAFechar);
		return new ResponseEntity<Queixa>(queixaAFechar, HttpStatus.OK);
	}

}
