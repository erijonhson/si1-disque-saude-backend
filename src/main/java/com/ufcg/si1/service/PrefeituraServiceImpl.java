package com.ufcg.si1.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ufcg.si1.model.prefeitura.PrefeituraSingleton;

@Service(value = "prefeituraService")
public class PrefeituraServiceImpl implements PrefeituraService {

	@Resource(name = "queixaService")
	QueixaService queixaService;

	@Override
	public Integer situacaoGeralDasQueixas() {
		long quantidadeDeQueixas = queixaService.quantidadeDeQueixas();
		long quantidadeDeQueixasAbertas = queixaService.quantidadeDeQueixasAbertas();
		PrefeituraSingleton prefeitura = PrefeituraSingleton.getInstance();

		double porcentagemQueixasAbertas = 0;
		if (quantidadeDeQueixasAbertas > 0) {
			porcentagemQueixasAbertas = quantidadeDeQueixas / quantidadeDeQueixasAbertas * 100;
		}

		return prefeitura.getSituacaoDasQueixas(porcentagemQueixasAbertas);
	}

}
