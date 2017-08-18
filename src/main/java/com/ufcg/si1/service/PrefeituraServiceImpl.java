package com.ufcg.si1.service;

import org.springframework.stereotype.Service;

import com.ufcg.si1.model.prefeitura.PrefeituraSingleton;
import com.ufcg.si1.model.prefeitura.SituacaoGeralDasQueixas;

@Service("prefeituraService")
public class PrefeituraServiceImpl implements PrefeituraService {

	@Override
	public SituacaoGeralDasQueixas situacaoGeralDasQueixas(long quantidadeDeQueixas, long quantidadeDeQueixasAbertas) {

		PrefeituraSingleton prefeitura = PrefeituraSingleton.getInstance();

		double porcentagemQueixasAbertas = 0;
		if (quantidadeDeQueixasAbertas > 0) {
			porcentagemQueixasAbertas = quantidadeDeQueixas / quantidadeDeQueixasAbertas * 100;
		}

		return prefeitura.getSituacaoDasQueixas(porcentagemQueixasAbertas);
	}

}
