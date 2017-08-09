package com.ufcg.si1.model.prefeitura;

public class Normal implements SituacaoDaPrefeitura {

	@Override
	public SituacaoGeralDasQueixas getSituacaoDasQueixas(float porcentagemQueixasAbertas) {
		if (porcentagemQueixasAbertas > 20) {
			return SituacaoGeralDasQueixas.RUIM;
		} else if (porcentagemQueixasAbertas > 10) {
			return SituacaoGeralDasQueixas.REGULAR;
		} else {
			return SituacaoGeralDasQueixas.BOM;
		}
	}

}
