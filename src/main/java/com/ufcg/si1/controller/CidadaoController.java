package com.ufcg.si1.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.si1.model.Cidadao;
import com.ufcg.si1.service.CidadaoService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CidadaoController {

	@Autowired
	CidadaoService cidadaoService;

	@RequestMapping(
			value = "/cidadao/", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Cidadao>> listAllCidadaos() {

		Collection<Cidadao> cidadaos = cidadaoService.buscarTodos();

		if (cidadaos.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Collection<Cidadao>>(cidadaos, HttpStatus.OK);
	}

}
