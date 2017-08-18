package com.ufcg.si1.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_unidade_de_saude_posto")
public class PostoDeSaude extends UnidadeDeSaude {

	private static final long serialVersionUID = 4379483195292866939L;

	@Column(name = "atendentes")
	private int atendentes;

	@Column(name = "taxa_diaria_atendimentos")
	private int taxaDiariaAtendimentos;

	public PostoDeSaude() {
		this("desconhecido", 0, 0);
	}

	public PostoDeSaude(String descricao, int atendentes, int taxa) {
		super(descricao);
		this.atendentes = atendentes;
		this.taxaDiariaAtendimentos = taxa;
	}

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

	@Override
	public double mediaDeMedicoPorPacienteEmUmDia() {
		if (this.getTaxaDiariaAtendimentos() > 0)
			return this.getAtendentes() / this.getTaxaDiariaAtendimentos();
		else
			return 0.0;
	}

}
