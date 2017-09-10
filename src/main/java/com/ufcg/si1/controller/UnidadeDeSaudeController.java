package com.ufcg.si1.controller;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.si1.interceptor.LoginRequired;
import com.ufcg.si1.model.Especialidade;
import com.ufcg.si1.model.UnidadeDeSaude;
import com.ufcg.si1.service.UnidadeDeSaudeService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UnidadeDeSaudeController {

	@Resource(name = "unidadeDeSaudeService")
	UnidadeDeSaudeService unidadeDeSaudeService;

	@RequestMapping(
			value = "/unidade/", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<UnidadeDeSaude> getAllUnidades() {
		return unidadeDeSaudeService.buscarTodos();
	}

	@LoginRequired
	@RequestMapping(
			value = "/administrador/unidade/", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UnidadeDeSaude incluirUnidadeDeSaude(@RequestBody UnidadeDeSaude unidadeDeSaude) {
		return unidadeDeSaudeService.cadastrar(unidadeDeSaude);
	}

	@RequestMapping(
			value = "/unidade/{id}", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UnidadeDeSaude consultarUnidadeSaude(@PathVariable("id") long id) {
		return unidadeDeSaudeService.buscarPorId(id);
	}

	@RequestMapping(
			value = "/unidade/busca", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<UnidadeDeSaude> consultarUnidadeSaudePorBairro(
			@RequestParam(value = "bairro", required = true) String bairro) {
		return unidadeDeSaudeService.buscaPorBairro(bairro);
	}

	@RequestMapping(
			value = "/unidade/busca/{idEspecialidade}", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<UnidadeDeSaude> consultarUnidadeSaudePorEspecialidade(
			@PathVariable("idEspecialidade") Long idEspecialidade) {
		return unidadeDeSaudeService.buscaPorEspecialidade(idEspecialidade);
	}

	@LoginRequired
	@RequestMapping(
			value = "/administrador/especialidade/{id}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Especialidade incluirEspecialidade(
			@PathVariable("id") long id, @RequestBody Especialidade especialidade) {
		return unidadeDeSaudeService.incluirEspecialidadeEmUnidadeDeSaude(id, especialidade);
	}

	@LoginRequired
	@RequestMapping(
			value = "/administrador/geral/medicos/{idUnidadeDeSaude}", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public double calcularMediaMedicoPacienteDia(
		@PathVariable("idUnidadeDeSaude") long idUnidadeDeSaude) {
		return unidadeDeSaudeService.mediaDeMedicoPorPacienteEmUmDia(idUnidadeDeSaude);
	}

}
