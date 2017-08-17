package com.ufcg.si1.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "tb_queixa")
public class Queixa implements Serializable {

	private static final long serialVersionUID = -8364601133955924234L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_queixa")
	private Long id;

	@Column(name = "descricao")
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "cidadao_id")
	private Cidadao solicitante;

	@Column(name = "situacao")
	private SituacaoDeQueixa situacao;

	@ManyToOne //(fetch = FetchType.LAZY)
	@JoinColumn(name = "endereco_id")
	private Endereco endereco;
	
	public Queixa() {
		this(0, "desconhecido", SituacaoDeQueixa.ABERTA, new Cidadao());
	}

	public Queixa(long id, String descricao, SituacaoDeQueixa situacao, Cidadao solicitante) {
		this.descricao = descricao;
		this.situacao = situacao;
		this.solicitante = solicitante;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public SituacaoDeQueixa getSituacao() {
		return situacao;
	}
	
	public void abrir() {
		if (this.situacao != SituacaoDeQueixa.EM_ANDAMENTO)
			this.situacao = SituacaoDeQueixa.ABERTA;
		else
			throw new IllegalStateException("Status inválido");
	}

	public void fechar() {
		if (this.situacao == SituacaoDeQueixa.EM_ANDAMENTO || this.situacao == SituacaoDeQueixa.ABERTA) {
			this.situacao = SituacaoDeQueixa.FECHADA;
		} else
			throw new IllegalStateException("Status inválido");
	}

	public Cidadao getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Cidadao solicitante) {
		this.solicitante = solicitante;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Queixa other = (Queixa) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public Endereco getEndereco() {
		return endereco;
	}
	
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

}
