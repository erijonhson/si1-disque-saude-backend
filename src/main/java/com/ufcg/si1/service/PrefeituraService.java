package com.ufcg.si1.service;


import com.ufcg.si1.model.prefeitura.SituacaoGeralDasQueixas;

public interface PrefeituraService {

	public SituacaoGeralDasQueixas situacaoGeralDasQueixas(
			long quantidadeDeQueixas, long quantidadeDeQueixasAbertas);

}
