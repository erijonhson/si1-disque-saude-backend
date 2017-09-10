package com.ufcg.si1.controller;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.si1.model.Especialidade;
import com.ufcg.si1.service.EspecialidadeService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class EspecialidadeController {

	@Resource(name = "especialidadeService")
	EspecialidadeService especialidadeService;

	@RequestMapping(
			value = "/especialidade/unidade/{idUnidadeDeSaude}", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Especialidade> consultaEspecialidadeporUnidadeSaude(
			@PathVariable("idUnidadeDeSaude") long idUnidadeDeSaude) {
		return especialidadeService.buscarEspecialidadesPorUnidadeDeSaude(idUnidadeDeSaude);
	}

	@RequestMapping(
			value = "/especialidade/{id}", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Especialidade consultarEspecialidade(@PathVariable("id") long id) {
		return especialidadeService.buscarPorId(id);
	}

}
