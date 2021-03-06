package com.ufcg.si1.model.prefeitura;

public class PrefeituraSingleton {

	private PrefeituraSingleton() {
		this.situacao = new Normal();
	}

	public static PrefeituraSingleton instance;

	public synchronized static PrefeituraSingleton getInstance() {
		if (instance == null) {
			instance = new PrefeituraSingleton();
		}
		return instance;
	}

	private SituacaoDaPrefeitura situacao;

	public SituacaoDaPrefeitura getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoDaPrefeitura situacao) {
		this.situacao = situacao;
	}

	public Integer getSituacaoDasQueixas(double porcentagemQueixasAbertas) {
		return this.situacao.getSituacaoDasQueixas(porcentagemQueixasAbertas).getSituacao();
	}

}
