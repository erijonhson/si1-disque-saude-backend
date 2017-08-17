package com.ufcg.si1.model.prefeitura;

public class Extra implements SituacaoDaPrefeitura {

	@Override
	public SituacaoGeralDasQueixas getSituacaoDasQueixas(double porcentagemQueixasAbertas) {
		if (porcentagemQueixasAbertas > 10) {
			return SituacaoGeralDasQueixas.RUIM;
		} else if (porcentagemQueixasAbertas > 5) {
			return SituacaoGeralDasQueixas.REGULAR;
		} else {
			return SituacaoGeralDasQueixas.BOM;
		}
	}

}
