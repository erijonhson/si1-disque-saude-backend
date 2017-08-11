package com.ufcg.si1.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

	// Especialidade

	@RequestMapping(value = "/especialidade/unidades", method = RequestMethod.GET)
	public ResponseEntity<Especialidade> consultaEspecialidadeporUnidadeSaude(@RequestBody int codigoUnidadeSaude) {

		try {
			UnidadeDeSaude us = unidadeSaudeService.buscarPorId((long) codigoUnidadeSaude);
			Collection<Especialidade> especialidades = us.getEspecialidades();
			return new ResponseEntity(especialidades, HttpStatus.OK);
		} catch (RuntimeException re) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
	}

	@RequestMapping(value = "/unidade/", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUnidades() {
		List<UnidadeDeSaude> unidades = unidadeSaudeService.buscarTodos();
		if (unidades.isEmpty())
			return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
		else {
			List<UnidadeDeSaude> unidadeSaudes = new ArrayList<>();
			for (Object saude : unidades) {
				if (saude instanceof UnidadeDeSaude) {
					unidadeSaudes.add((UnidadeDeSaude) saude);
				}
			}
			return new ResponseEntity<>(unidadeSaudes, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/especialidade/", method = RequestMethod.POST)
	public ResponseEntity<String> incluirEspecialidade(@RequestBody Especialidade esp, UriComponentsBuilder ucBuilder) {
		try {
			especialidadeService.cadastrar(esp);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/especialidade/{id}").buildAndExpand(esp.getCodigo()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// how to save a subclass object?
	@RequestMapping(value = "/unidade/", method = RequestMethod.POST)
	public ResponseEntity<String> incluirUnidadeSaude(@RequestBody UnidadeDeSaude us, UriComponentsBuilder ucBuilder) {

		try {
			unidadeSaudeService.cadastrar(us);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/unidade/{id}").buildAndExpand(us.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/especialidade/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarEspecialidade(@PathVariable("id") long id) {

		Especialidade q = especialidadeService.buscarPorId(id);
		if (q == null) {
			return new ResponseEntity(
					new Error("Especialidade with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Especialidade>(q, HttpStatus.OK);
	}

	@RequestMapping(value = "/unidade/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarUnidadeSaude(@PathVariable("id") long id) {

		Object us = unidadeSaudeService.buscarPorId(id);
		if (us == null) {
			return new ResponseEntity(new Error("Unidade with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(us, HttpStatus.OK);
	}

	@RequestMapping(value = "/geral/medicos/{id}", method = RequestMethod.GET)
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

	@RequestMapping(value = "/unidade/busca", method = RequestMethod.GET)
	public ResponseEntity<?> consultarUnidadeSaudePorBairro(
			@RequestParam(value = "bairro", required = true) String bairro) {

		try {
			Collection<UnidadeDeSaude> us = ((UnidadeDeSaudeService) unidadeSaudeService).findByBairro(bairro);
			return new ResponseEntity<UnidadeDeSaude>((UnidadeDeSaude) us, HttpStatus.OK);
		} catch(RuntimeException re) {
			return new ResponseEntity(
					new Error("Unidade with bairro " + bairro + " not found"),
					HttpStatus.NOT_FOUND);
		}
	}

}
