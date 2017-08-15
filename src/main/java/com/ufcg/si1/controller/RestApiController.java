package com.ufcg.si1.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.util.UriComponentsBuilder;

import com.ufcg.si1.model.Especialidade;
import com.ufcg.si1.model.PostoDeSaude;
import com.ufcg.si1.model.Queixa;
import com.ufcg.si1.model.UnidadeDeSaude;
import com.ufcg.si1.service.EspecialidadeServiceImpl;
import com.ufcg.si1.service.GenericService;
import com.ufcg.si1.service.QueixaServiceImpl;
import com.ufcg.si1.service.UnidadeDeSaudeService;
import com.ufcg.si1.service.UnidadeDeSaudeServiceImpl;

import br.edu.ufcg.Hospital;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RestApiController {

	@Autowired
	GenericService<Queixa> queixaService = new QueixaServiceImpl();

	@Autowired
	GenericService<Especialidade> especialidadeService = new EspecialidadeServiceImpl();

	@Autowired
	GenericService<UnidadeDeSaude> unidadeSaudeService = new UnidadeDeSaudeServiceImpl();

	@RequestMapping(
			value = "/geral/medicos/{id}", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> calcularMediaMedicoPacienteDia(@PathVariable("id") long id) {

		Object unidade = unidadeSaudeService.buscarPorId(id);

		if (unidade == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		// TODO: Bad small pesado aqui, hein?
		// acredito que dá pra usar State Pattern ;)
		double c = 0.0;
		if (unidade instanceof PostoDeSaude)
			c = ((PostoDeSaude) unidade).getAtendentes() / ((PostoDeSaude) unidade).getTaxaDiariaAtendimentos();
		else if (unidade instanceof Hospital) {
			c = ((Hospital) unidade).getNumeroMedicos() / ((Hospital) unidade).getNumeroPacientesDia();
		}
		return new ResponseEntity(new Double(c), HttpStatus.OK);
	}

	@RequestMapping(value = "/geral/situacao", method = RequestMethod.GET)
	public ResponseEntity<?> getSituacaoGeralQueixas() {
		return new ResponseEntity(new Integer(2), HttpStatus.OK);
	}

}
