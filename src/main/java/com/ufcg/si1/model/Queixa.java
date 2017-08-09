package com.ufcg.si1.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import exceptions.ObjetoInvalidoException;

@Entity
@Table(name = "tb_queixa")
public class Queixa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_queixa")
	private Long id;

	@Column(name = "descricao")
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "cidadao_id")
	@JsonBackReference
	private Cidadao solicitante;

	@OneToMany(mappedBy = "queixa")
	@JsonManagedReference
	private Set<Comentario> comentarios;

	public int situacao;

	public static final int ABERTA = 1;
	public static final int EM_ANDAMENTO = 2;
	public static final int FECHADA = 3;

	public Queixa() {
	}

	public Queixa(long id, String descricao, int situacao, Cidadao solicitante) {
		this.descricao = descricao;
		this.situacao = situacao;
		this.solicitante = solicitante;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getSituacao() {
		return situacao;
	}

	public void abrir() throws ObjetoInvalidoException {
		if (this.situacao != Queixa.EM_ANDAMENTO)
			this.situacao = Queixa.ABERTA;
		else
			throw new ObjetoInvalidoException("Status inválido");
	}

	public void fechar() throws ObjetoInvalidoException {
		if (this.situacao == Queixa.EM_ANDAMENTO || this.situacao == Queixa.ABERTA) {
			this.situacao = Queixa.FECHADA;
			// TODO: avaliar funcionamento dessa questão
			// this.comentario = coment;
		} else
			throw new ObjetoInvalidoException("Status Inválido");
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

}
