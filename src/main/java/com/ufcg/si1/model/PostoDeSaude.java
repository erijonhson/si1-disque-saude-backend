package com.ufcg.si1.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "posto_unidade")
public class PostoDeSaude extends UnidadeDeSaude {

	@Column(name = "atendentes")
	private int atendentes;

	@Column(name = "taxa_Diaria_Atendimentos")
	private int taxaDiariaAtendimentos;

	public PostoDeSaude() {
		this("desconhecido", 0, 0);
	}

	public PostoDeSaude(String descricao, int atendentes, int taxa) {
		super(descricao);
		this.atendentes = atendentes;
		this.taxaDiariaAtendimentos = taxa;
	}

	// implementacoes vazias
	public int getAtendentes() {
		return atendentes;
	}

	public void setAtendentes(int atendentes) {
		this.atendentes = atendentes;
	}

	public int getTaxaDiariaAtendimentos() {
		return taxaDiariaAtendimentos;
	}

	public void setTaxaDiariaAtendimentos(int taxaDiariaAtendimentos) {
		this.taxaDiariaAtendimentos = taxaDiariaAtendimentos;
	}
}
