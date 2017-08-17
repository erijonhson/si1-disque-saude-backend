package com.ufcg.si1.model.prefeitura;

public enum SituacaoGeralDasQueixas {

	RUIM(0), REGULAR(1), BOM(2);

	private final int situacao;

	SituacaoGeralDasQueixas(int situacao) {
		this.situacao = situacao;
	}

	public int getSituacao() {
		return situacao;
	}

}
