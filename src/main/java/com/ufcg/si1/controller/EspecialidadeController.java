package com.ufcg.si1.controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ufcg.si1.model.Especialidade;
import com.ufcg.si1.model.UnidadeDeSaude;
import com.ufcg.si1.service.EspecialidadeService;
import com.ufcg.si1.service.UnidadeDeSaudeService;

import exceptions.Erro;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class EspecialidadeController {

	@Autowired
	EspecialidadeService especialidadeService;

	@Autowired
	UnidadeDeSaudeService unidadeDeSaudeService;

	@RequestMapping(
			value = "/especialidade/unidade/{idUnidadeDeSaude}", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Especialidade>> consultaEspecialidadeporUnidadeSaude(
			@PathVariable("idUnidadeDeSaude") long idUnidadeDeSaude) {

		try {

			Collection<Especialidade> especialidades = 
					especialidadeService.buscarEspecialidadesPorUnidadeDeSaude(idUnidadeDeSaude);

			return new ResponseEntity<Collection<Especialidade>>(especialidades, HttpStatus.OK);

		} catch (RuntimeException re) {
			return new ResponseEntity(
					new Erro("Não foi possível consultar Especialidades."),
					HttpStatus.NOT_FOUND);
		}
		
	}

	// TODO: não está funcionando, mas é um extra
	@RequestMapping(
			value = "/administrador/especialidade/",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Especialidade> incluirEspecialidade(@RequestBody UnidadeEspecialidade context) {

		try {

			Especialidade especialidade = context.getEspecialidade();
			UnidadeDeSaude unidadeDeSaude = context.getUnidadeDeSaude();
			Set<UnidadeDeSaude> unidades = new HashSet<>();
			unidades.add(unidadeDeSaude);
			especialidade.setUnidadesDeSaude(unidades);
			especialidade = especialidadeService.cadastrar(especialidade);

			return new ResponseEntity<Especialidade>(especialidade, HttpStatus.CREATED);

		} catch (RuntimeException re) {
			return new ResponseEntity(
					new Erro("Não foi possível adicionar Especialidade."),
					HttpStatus.CONFLICT);
		}

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

	@JsonDeserialize
	private class UnidadeEspecialidade {

		private UnidadeDeSaude unidadeDeSaude;
		private Especialidade especialidade;

		public UnidadeEspecialidade() {
			
		}

		public UnidadeDeSaude getUnidadeDeSaude() {
			return unidadeDeSaude;
		}

		public void setUnidadeDeSaude(UnidadeDeSaude unidadeDeSaude) {
			this.unidadeDeSaude = unidadeDeSaude;
		}

		public Especialidade getEspecialidade() {
			return especialidade;
		}

		public void setEspecialidade(Especialidade especialidade) {
			this.especialidade = especialidade;
		}
	}
}
