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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.si1.model.UnidadeDeSaude;
import com.ufcg.si1.service.GenericService;
import com.ufcg.si1.service.UnidadeDeSaudeService;
import com.ufcg.si1.service.UnidadeDeSaudeServiceImpl;

import exceptions.Erro;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UnidadeDeSaudeController {

	@Autowired
	GenericService<UnidadeDeSaude> unidadeSaudeService = new UnidadeDeSaudeServiceImpl();

	@RequestMapping(
			value = "/unidade/", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<UnidadeDeSaude>> getAllUnidades() {

		List<UnidadeDeSaude> unidades = unidadeSaudeService.buscarTodos();
		if (unidades.isEmpty()) {
			return new ResponseEntity(new Erro("Unidade de Saúde não encontrada."),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Collection<UnidadeDeSaude>>(unidades, HttpStatus.OK);
	}

	@RequestMapping(
			value = "/unidade/", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UnidadeDeSaude> incluirUnidadeSaude(@RequestBody UnidadeDeSaude unidadeDeSaude) {

		try {
			unidadeSaudeService.cadastrar(unidadeDeSaude);
		} catch (RuntimeException re) {
			return new ResponseEntity(new Erro("Unidade de Saúde não encontrada."),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<UnidadeDeSaude>(unidadeDeSaude, HttpStatus.CREATED);
	}

	@RequestMapping(
			value = "/unidade/{id}", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UnidadeDeSaude> consultarUnidadeSaude(@PathVariable("id") long id) {

		UnidadeDeSaude unidadeDeSaude = unidadeSaudeService.buscarPorId(id);
		if (unidadeDeSaude == null) {
			return new ResponseEntity(new Erro("Unidade de Saúde não encontrada."),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<UnidadeDeSaude>(unidadeDeSaude, HttpStatus.OK);
	}

	@RequestMapping(
			value = "/unidade/busca", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> consultarUnidadeSaudePorBairro(
			@RequestParam(value = "bairro", required = true) String bairro) {

		try {
			Collection<UnidadeDeSaude> unidadeDeSaude = ((UnidadeDeSaudeService) unidadeSaudeService).buscaPorBairro(bairro);
			return new ResponseEntity<UnidadeDeSaude>((UnidadeDeSaude) unidadeDeSaude, HttpStatus.OK);
		} catch(RuntimeException re) {
			return new ResponseEntity(new Erro("Unidade de Saúde não encontrada."),
					HttpStatus.NOT_FOUND);
		}

	}

}
