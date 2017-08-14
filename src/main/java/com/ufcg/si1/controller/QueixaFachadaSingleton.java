package com.ufcg.si1.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.ufcg.si1.model.Cidadao;
import com.ufcg.si1.model.Queixa;
import com.ufcg.si1.service.CidadaoServiceImpl;
import com.ufcg.si1.service.GenericService;

public class QueixaFachadaSingleton {

	private static QueixaFachadaSingleton instance;

	private QueixaFachadaSingleton() {
	}

	public static QueixaFachadaSingleton getInstance() {
		if (instance == null) {
			instance = new QueixaFachadaSingleton();
		}
		return instance;
	}

	@Autowired
	GenericService<Cidadao> cidadaoService = new CidadaoServiceImpl();

	public void preparaQueixa(Queixa queixa) {
		Cidadao solicitante = queixa.getSolicitante();
		Cidadao solicitanteBD = ((CidadaoServiceImpl) cidadaoService).buscarPorEmail(solicitante.getEmail());
		queixa.setSolicitante(solicitanteBD);
	}

}
