package com.ufcg.si1.controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

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

import com.ufcg.si1.model.Endereco;
import com.ufcg.si1.model.Especialidade;
import com.ufcg.si1.model.UnidadeDeSaude;
import com.ufcg.si1.service.EnderecoService;
import com.ufcg.si1.service.EspecialidadeService;
import com.ufcg.si1.service.UnidadeDeSaudeService;

import exceptions.Erro;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UnidadeDeSaudeController {

	@Resource(name = "unidadeDeSaudeService")
	UnidadeDeSaudeService unidadeDeSaudeService;

	@Resource(name = "enderecoService")
	EnderecoService enderecoService;

	@Resource(name = "especialidadeService")
	EspecialidadeService especialidadeService;

	@RequestMapping(
			value = "/unidade/", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<UnidadeDeSaude>> getAllUnidades() {

		List<UnidadeDeSaude> unidades = unidadeDeSaudeService.buscarTodos();
		if (unidades.isEmpty()) {
			return new ResponseEntity(
					new Erro("Unidade de Saúde não encontrada."),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Collection<UnidadeDeSaude>>(unidades, HttpStatus.OK);
	}

	@RequestMapping(
			value = "/administrador/unidade/", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UnidadeDeSaude> incluirUnidadeDeSaude(@RequestBody UnidadeDeSaude unidadeDeSaude) {

		try {

			preparaUnidadeDeSaude(unidadeDeSaude);

			unidadeDeSaude = unidadeDeSaudeService.cadastrar(unidadeDeSaude);

			return new ResponseEntity<UnidadeDeSaude>(unidadeDeSaude, HttpStatus.CREATED);

		} catch (RuntimeException re) {
			return new ResponseEntity(
					new Erro("Unidade de Saúde inválida."),
					HttpStatus.NOT_FOUND);
		}

	}

	@RequestMapping(
			value = "/unidade/{id}", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UnidadeDeSaude> consultarUnidadeSaude(@PathVariable("id") long id) {

		UnidadeDeSaude unidadeDeSaude = unidadeDeSaudeService.buscarPorId(id);
		if (unidadeDeSaude == null) {
			return new ResponseEntity(
					new Erro("Unidade de Saúde não encontrada."),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<UnidadeDeSaude>(unidadeDeSaude, HttpStatus.OK);
	}

	@RequestMapping(
			value = "/unidade/busca", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<UnidadeDeSaude>> consultarUnidadeSaudePorBairro(
			@RequestParam(value = "bairro", required = true) String bairro) {

		try {

			Collection<UnidadeDeSaude> unidadesDeSaude = unidadeDeSaudeService.buscaPorBairro(bairro);

			return new ResponseEntity<Collection<UnidadeDeSaude>>(unidadesDeSaude, HttpStatus.OK);

		} catch(RuntimeException re) {
			return new ResponseEntity(
					new Erro("Unidades de Saúde não encontradas."),
					HttpStatus.NOT_FOUND);
		}

	}

	@RequestMapping(
			value = "/unidade/busca/{idEspecialidade}", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<UnidadeDeSaude>> consultarUnidadeSaudePorEspecialidade(@PathVariable("idEspecialidade") Long idEspecialidade) {

		try {

			Collection<UnidadeDeSaude> unidadesDeSaude = unidadeDeSaudeService.buscaPorEspecialidade(idEspecialidade);

			return new ResponseEntity<Collection<UnidadeDeSaude>>(unidadesDeSaude, HttpStatus.OK);

		} catch(RuntimeException re) {
			return new ResponseEntity(
					new Erro("Unidades de Saúde não encontradas."),
					HttpStatus.NOT_FOUND);
		}

	}

	// ----------- privated methods ---------------

	private void preparaUnidadeDeSaude(UnidadeDeSaude unidadeDeSaude) {
		preparaEnderecoUnidadeDeSaude(unidadeDeSaude);
		preparaEspecialidadesUnidadeDeSaude(unidadeDeSaude);
	}
	
	private void preparaEnderecoUnidadeDeSaude(UnidadeDeSaude unidadeDeSaude) {

		Endereco endereco = unidadeDeSaude.getLocal();

		Endereco enderoBD = enderecoService.buscarPorRuaECidade(endereco);
		if (enderoBD == null) {
			enderoBD = enderecoService.cadastrar(endereco);
		}

		unidadeDeSaude.setLocal(enderoBD);
	}

	private void preparaEspecialidadesUnidadeDeSaude(UnidadeDeSaude unidadeDeSaude){

		Set<Especialidade> especialidades = unidadeDeSaude.getEspecialidades();
		Set<Especialidade> result = new HashSet();

		for (Especialidade especialidade : especialidades) {
			Especialidade espBD = especialidadeService.buscarPorDescricao(especialidade.getDescricao());
			if (espBD == null) {
				especialidade = especialidadeService.cadastrar(especialidade);
			} else {
				especialidade = espBD;
			}
			result.add(especialidade);
		}

		unidadeDeSaude.setEspecialidades(result);
	}

}
