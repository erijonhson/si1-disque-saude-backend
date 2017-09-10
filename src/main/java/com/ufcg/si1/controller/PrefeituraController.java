package com.ufcg.si1.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.si1.interceptor.LoginRequired;
import com.ufcg.si1.service.PrefeituraService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PrefeituraController {

	@Resource(name = "prefeituraService")
	PrefeituraService prefeituraService;

	@LoginRequired
	@RequestMapping(
			value = "/administrador/geral/situacao", 
			method = RequestMethod.GET)
	public Integer getSituacaoGeralQueixas() {
		return prefeituraService.situacaoGeralDasQueixas();
	}

}
