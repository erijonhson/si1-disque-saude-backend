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

import com.ufcg.si1.model.Cidadao;
import com.ufcg.si1.model.Comentario;
import com.ufcg.si1.model.Endereco;
import com.ufcg.si1.model.Queixa;
import com.ufcg.si1.service.CidadaoService;
import com.ufcg.si1.service.ComentarioService;
import com.ufcg.si1.service.EnderecoService;
import com.ufcg.si1.service.QueixaService;

import exceptions.Erro;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class QueixaController {

	@Autowired
	QueixaService queixaService;

	@Autowired
	CidadaoService cidadaoService;
	
	@Autowired
	EnderecoService enderecoService;
	
	@Autowired
	ComentarioService comentarioService;

	@RequestMapping(
			value = "/queixa/", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Queixa>> listAllQueixas() {

		Collection<Queixa> queixas = queixaService.buscarTodos();

		return new ResponseEntity<Collection<Queixa>>(queixas, HttpStatus.OK);
	}

	@RequestMapping(
			value = "/queixa/", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Queixa> abrirQueixa(@RequestBody Queixa queixa) {

		try {

			preparaQueixa(queixa);

			queixa = queixaService.cadastrar(queixa);

			return new ResponseEntity<Queixa>(queixa, HttpStatus.CREATED);

		} catch (RuntimeException re) {
			return new ResponseEntity(
					HttpStatus.CONFLICT);
		}

	}

	@RequestMapping(
			value = "/administrador/queixa/addall", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Queixa>> abrirPaioDeQueixa(@RequestBody Collection<Queixa> queixas) {

		try {
			queixas.forEach(q -> {
				preparaQueixa(q);
				q = queixaService.cadastrar(q);
			});
			return new ResponseEntity<Collection<Queixa>>(queixas, HttpStatus.CREATED);
		} catch (RuntimeException re) {
			return new ResponseEntity(HttpStatus.CONFLICT);
		}

	}

	@RequestMapping(
			value = "/queixa/{id}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Queixa> consultarQueixa(@PathVariable("id") long id) {

		Queixa queixa = queixaService.buscarPorId(id);

		if (queixa == null) {
			return new ResponseEntity(
					new Erro("Queixa não encontrada"), 
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Queixa>(queixa, HttpStatus.OK);
	}

	@RequestMapping(
			value = "/administrador/queixa/", 
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Queixa> updateQueixa(@RequestBody Queixa queixa) {

		try {

			queixa = queixaService.atualizar(queixa);

			return new ResponseEntity<Queixa>(queixa, HttpStatus.OK);

		} catch(RuntimeException re) {
			return new ResponseEntity(
					new Erro(re.getMessage()),
					HttpStatus.NOT_FOUND);
		}

	}

	@RequestMapping(
			value = "/administrador/queixa/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity deleteQueixa(@PathVariable("id") long id) {

		try {

			queixaService.deletar(id);

		} catch(RuntimeException re) {
			return new ResponseEntity(
					new Erro(re.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		
		//TODO: bad smell de nome de variavel, que estava user
		Queixa queixaEncontrada = queixaService.buscarPorId(id);
		if (queixaEncontrada == null) {
			return new ResponseEntity(
					new Erro("Não é possível deletar. Queixa não encontrada."),
					HttpStatus.NOT_FOUND);
		}
		

		//TODO badsmel not content
		return new ResponseEntity<Queixa>(HttpStatus.OK);
	}

	@RequestMapping(
			value = "/administrador/queixa/fechamento", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Queixa> fecharQueixa(@RequestBody Queixa queixa) {

		// TODO: bad smell: variavel public queixaAFechar.situacao
		Queixa queixaFechada = null;

		try {

			queixaFechada = queixaService.fecharQueixa(queixa);

			return new ResponseEntity<Queixa>(queixaFechada, HttpStatus.OK);

		} catch (RuntimeException re) {
			return new ResponseEntity(
					new Erro("Não é possível fechar Queixa. Erro interno no sistema."),
					HttpStatus.NOT_MODIFIED);
		}

	}

	@RequestMapping(
			value = "/queixa/comentario/{idQueixa}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Comentario> adicionaComentario(@PathVariable("idQueixa") Long idQueixa, @RequestBody Comentario comentario) {

		Queixa queixaBD = queixaService.buscarPorId(idQueixa);

		if(queixaBD == null) {
			return new ResponseEntity(
					new Erro("Não é possível adicionar comentário em Queixa inexistente!"),
					HttpStatus.NOT_ACCEPTABLE);
		}

		comentario.setQueixa(queixaBD);
		comentario = comentarioService.cadastrar(comentario);
		return new ResponseEntity<Comentario>(comentario, HttpStatus.CREATED);

	}

	@RequestMapping(
			value = "/queixa/comentario/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Comentario>> buscaComentariosDaQueixa(@PathVariable("id") Long idQueixa) {

		Collection<Comentario> listaComentariosQueixa = comentarioService.buscaTodosComentariosDeQueixa(idQueixa);

		return new ResponseEntity<Collection<Comentario>>(listaComentariosQueixa, HttpStatus.OK);

	}

	// ----------- privated methods ---------------

	private void preparaQueixa(Queixa queixa) {
		preparaSolicitanteDaQueixa(queixa);
		preparaEnderecoDaQueixa(queixa);
	}

	private void preparaSolicitanteDaQueixa(Queixa queixa) {

		Cidadao solicitante = queixa.getSolicitante();

		Cidadao solicitanteBD = cidadaoService.buscarPorEmail(solicitante.getEmail());
		if (solicitanteBD == null) {
			solicitanteBD = cidadaoService.cadastrar(solicitante);
		}

		queixa.setSolicitante(solicitanteBD);
	}
	
	private void preparaEnderecoDaQueixa(Queixa queixa) {
		
		Endereco endereco = queixa.getEndereco();

		Endereco enderoBD = enderecoService.buscarPorRuaECidade(endereco);
		if (enderoBD == null) {
			enderoBD = enderecoService.cadastrar(endereco);
		}

		queixa.setEndereco(enderoBD);
	}

}
