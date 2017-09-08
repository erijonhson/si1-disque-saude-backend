package com.ufcg.si1.controller;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
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

	@Resource(name = "cidadaoService")
	CidadaoService cidadaoService;

	@RequestMapping(
			value = "/cidadao/", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Cidadao> listAllCidadaos() {
		return cidadaoService.buscarTodos();
	}

}
