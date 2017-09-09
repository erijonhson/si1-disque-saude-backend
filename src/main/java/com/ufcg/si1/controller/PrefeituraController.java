package com.ufcg.si1.controller;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.si1.exception.Erro;
import com.ufcg.si1.model.UnidadeDeSaude;
import com.ufcg.si1.model.prefeitura.SituacaoGeralDasQueixas;
import com.ufcg.si1.service.PrefeituraService;
import com.ufcg.si1.service.QueixaService;
import com.ufcg.si1.service.UnidadeDeSaudeService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PrefeituraController {

	@Resource(name = "prefeituraService")
	PrefeituraService prefeituraService;
	
	@Resource(name = "unidadeDeSaudeService")
	UnidadeDeSaudeService unidadeDeSaudeService;

	@Resource(name = "queixaService")
	QueixaService queixaService;

	@RequestMapping(
			value = "/administrador/geral/medicos/{idUnidadeDeSaude}", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> calcularMediaMedicoPacienteDia(@PathVariable("idUnidadeDeSaude") long idUnidadeDeSaude) {

		UnidadeDeSaude unidadeDeSaude = unidadeDeSaudeService.buscarPorId(idUnidadeDeSaude);

		if (unidadeDeSaude == null) {
			return new ResponseEntity(
					new Erro("Unidade de saúde não encontrada"), 
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(unidadeDeSaude.mediaDeMedicoPorPacienteEmUmDia(), HttpStatus.OK);
	}

	
	@RequestMapping(
			value = "/administrador/geral/situacao", 
			method = RequestMethod.GET)
	public ResponseEntity<Integer> getSituacaoGeralQueixas() {

		long quantidadeDeQueixas = queixaService.quantidadeDeQueixas();
		
		// reabilitar
		long quantidadeDeQueixasAbertas = queixaService.quantidadeDeQueixasAbertas();

		SituacaoGeralDasQueixas situacaoGeralDasQueixas = 
				prefeituraService.situacaoGeralDasQueixas(
						quantidadeDeQueixas, quantidadeDeQueixasAbertas);

		return new ResponseEntity<Integer>(situacaoGeralDasQueixas.getSituacao(), HttpStatus.OK);
	}
	

}
