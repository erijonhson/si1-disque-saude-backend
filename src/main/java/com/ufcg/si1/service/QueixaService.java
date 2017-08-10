package com.ufcg.si1.service;


import com.ufcg.si1.model.Queixa;
import com.ufcg.si1.model.prefeitura.SituacaoGeralDasQueixas;

import exceptions.ObjetoInvalidoException;

public interface QueixaService extends GenericService<Queixa> {

	public SituacaoGeralDasQueixas situacaoGeralDasQueixas();

	public Queixa fecharQueixa(Queixa queixa) throws ObjetoInvalidoException;

}
