package com.ufcg.si1.controller;

import java.util.ArrayList;
import java.util.Iterator;
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
import com.ufcg.si1.repository.CidadaoRepository;
import com.ufcg.si1.repository.EnderecoRepository;
import com.ufcg.si1.service.EspecialidadeService;
import com.ufcg.si1.service.EspecialidadeServiceImpl;
import com.ufcg.si1.service.QueixaService;
import com.ufcg.si1.service.QueixaServiceImpl;
import com.ufcg.si1.service.UnidadeDeSaudeService;
import com.ufcg.si1.service.UnidadeDeSaudeServiceImpl;
import com.ufcg.si1.util.CustomErrorType;
import com.ufcg.si1.util.ObjWrapper;

import br.edu.ufcg.Hospital;
import exceptions.ObjetoInvalidoException;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RestApiController {

	@Autowired
	QueixaService queixaService = new QueixaServiceImpl();
	
	@Autowired
	EspecialidadeService especialidadeService = new EspecialidadeServiceImpl();
	
	@Autowired
	UnidadeDeSaudeService unidadeSaudeService = new UnidadeDeSaudeServiceImpl();
	
	@Autowired
	CidadaoRepository cidadaoRepository;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	/*
	 * situação normal =0 situação extra =1
	 */
	private int situacaoAtualPrefeitura = 0;

	// Especialidade

	@RequestMapping(value = "/especialidade/unidades", method = RequestMethod.GET)
	public ResponseEntity<?> consultaEspecialidadeporUnidadeSaude(@RequestBody int codigoUnidadeSaude) {

		Object us = null;
		try {
			us = unidadeSaudeService.buscarPorId((long) codigoUnidadeSaude);
		} catch (Exception e) {
			return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
		}
		if (us instanceof UnidadeDeSaude) {
			UnidadeDeSaude us1 = (UnidadeDeSaude) us;
			return new ResponseEntity<>(us1.getEspecialidades(), HttpStatus.OK);
		}

		return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
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
			return new ResponseEntity(new CustomErrorType("Especialidade with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Especialidade>(q, HttpStatus.OK);
	}

	@RequestMapping(value = "/unidade/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarUnidadeSaude(@PathVariable("id") long id) {

		Object us = unidadeSaudeService.buscarPorId(id);
		if (us == null) {
			return new ResponseEntity(new CustomErrorType("Unidade with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(us, HttpStatus.OK);
	}

	@RequestMapping(value = "/geral/medicos/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> calcularMediaMedicoPacienteDia(@PathVariable("id") long id) {

		Object unidade = unidadeSaudeService.buscarPorId(id);

		if (unidade == null) {
			return new ResponseEntity<ObjWrapper<Double>>(HttpStatus.NOT_FOUND);
		}

		double c = 0.0;
		if (unidade instanceof PostoDeSaude)
			c = ((PostoDeSaude) unidade).getAtendentes() / ((PostoDeSaude) unidade).getTaxaDiariaAtendimentos();
		else if (unidade instanceof Hospital) {
			c = ((Hospital) unidade).getNumeroMedicos() / ((Hospital) unidade).getNumeroPacientesDia();
		}
		return new ResponseEntity<ObjWrapper<Double>>(new ObjWrapper<Double>(new Double(c)), HttpStatus.OK);
	}

	@RequestMapping(value = "/geral/situacao", method = RequestMethod.GET)
	public ResponseEntity<?> getSituacaoGeralQueixas() {

		// dependendo da situacao da prefeitura, o criterio de avaliacao muda
		// se normal, mais de 20% abertas eh ruim, mais de 10 eh regular
		// se extra, mais de 10% abertas eh ruim, mais de 5% eh regular
		if (situacaoAtualPrefeitura == 0) {
			if ((double) numeroQueixasAbertas() / queixaService.size() > 0.2) {
				return new ResponseEntity<ObjWrapper<Integer>>(new ObjWrapper<Integer>(0), HttpStatus.OK);
			} else {
				if ((double) numeroQueixasAbertas() / queixaService.size() > 0.1) {
					return new ResponseEntity<ObjWrapper<Integer>>(new ObjWrapper<Integer>(1), HttpStatus.OK);
				}
			}
		}
		if (this.situacaoAtualPrefeitura == 1) {
			if ((double) numeroQueixasAbertas() / queixaService.size() > 0.1) {
				return new ResponseEntity<ObjWrapper<Integer>>(new ObjWrapper<Integer>(0), HttpStatus.OK);
			} else {
				if ((double) numeroQueixasAbertas() / queixaService.size() > 0.05) {
					return new ResponseEntity<ObjWrapper<Integer>>(new ObjWrapper<Integer>(1), HttpStatus.OK);
				}
			}
		}

		// situacao retornada
		// 0: RUIM
		// 1: REGULAR
		// 2: BOM
		return new ResponseEntity<ObjWrapper<Integer>>(new ObjWrapper<Integer>(2), HttpStatus.OK);
	}

	@RequestMapping(value = "/unidade/busca", method = RequestMethod.GET)
	public ResponseEntity<?> consultarUnidadeSaudePorBairro(
			@RequestParam(value = "bairro", required = true) String bairro) {
		Object us = unidadeSaudeService.findByBairro(bairro);
		if (us == null && !(us instanceof UnidadeDeSaude)) {
			return new ResponseEntity(new CustomErrorType("Unidade with bairro " + bairro + " not found"),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<UnidadeDeSaude>((UnidadeDeSaude) us, HttpStatus.OK);
	}

	private double numeroQueixasAbertas() {
		int contador = 0;
		Iterator<Queixa> it = queixaService.getIterator();
		for (Iterator<Queixa> it1 = it; it1.hasNext();) {
			Queixa q = it1.next();
			if (q.getSituacao() == Queixa.ABERTA)
				contador++;
		}

		return contador;
	}

}
