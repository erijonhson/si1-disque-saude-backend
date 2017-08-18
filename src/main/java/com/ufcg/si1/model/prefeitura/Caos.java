package com.ufcg.si1.model.prefeitura;

public class Caos implements SituacaoDaPrefeitura {

	@Override
	public SituacaoGeralDasQueixas getSituacaoDasQueixas(double porcentagemQueixasAbertas) {
		if (porcentagemQueixasAbertas > 5) {
			return SituacaoGeralDasQueixas.RUIM;
		} else if (porcentagemQueixasAbertas > 2) {
			return SituacaoGeralDasQueixas.REGULAR;
		} else {
			return SituacaoGeralDasQueixas.BOM;
		}
	}

}
