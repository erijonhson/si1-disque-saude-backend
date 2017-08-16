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

import com.ufcg.si1.model.Especialidade;
import com.ufcg.si1.model.UnidadeDeSaude;
import com.ufcg.si1.service.EspecialidadeService;
import com.ufcg.si1.service.EspecialidadeServiceImpl;
import com.ufcg.si1.service.UnidadeDeSaudeService;
import com.ufcg.si1.service.UnidadeDeSaudeServiceImpl;

import exceptions.Erro;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class EspecialidadeController {

	@Autowired
	EspecialidadeService especialidadeService = new EspecialidadeServiceImpl();

	@Autowired
	UnidadeDeSaudeService unidadeSaudeService = new UnidadeDeSaudeServiceImpl();

	@RequestMapping(
			value = "/especialidade/unidades", 
			method = RequestMethod.GET,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Especialidade> consultaEspecialidadeporUnidadeSaude(@RequestBody long codigoUnidadeSaude) {

		try {
			UnidadeDeSaude unidadeDeSaude = unidadeSaudeService.buscarPorId(codigoUnidadeSaude);
			Collection<Especialidade> especialidades = 
					((EspecialidadeServiceImpl) especialidadeService)
					.buscarEspecialidadesPorUnidadeDeSaude(unidadeDeSaude);
			return new ResponseEntity(especialidades, HttpStatus.OK);
		} catch (RuntimeException re) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
	}

	@RequestMapping(
			value = "/especialidade/", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Especialidade> incluirEspecialidade(@RequestBody Especialidade especialidade) {

		try {
			especialidade = especialidadeService.cadastrar(especialidade);
		} catch (RuntimeException re) {
			return new ResponseEntity(HttpStatus.CONFLICT);
		}

		return new ResponseEntity<Especialidade>(especialidade, HttpStatus.CREATED);
	}

	@RequestMapping(
			value = "/especialidade/{id}", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Especialidade> consultarEspecialidade(@PathVariable("id") long id) {

		Especialidade especialidade = especialidadeService.buscarPorId(id);
		if (especialidade == null) {
			return new ResponseEntity(
					new Erro("Especialidade não encontrada."),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Especialidade>(especialidade, HttpStatus.OK);
	}

}
