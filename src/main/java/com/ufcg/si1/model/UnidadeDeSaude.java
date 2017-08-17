package com.ufcg.si1.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

// TODO: existe UnidadeDeSaude que não é Hospital nem Posto?
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tb_unidade_de_saude")
public abstract class UnidadeDeSaude implements Serializable {

	private static final long serialVersionUID = 1842734734661302140L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_unidade_de_saude")
	private Long id;

	@Column(name = "descricao")
	private String descricao;

	@ManyToOne //(fetch = FetchType.LAZY)
	@JoinColumn(name = "endereco_id")
	private Endereco local;

	public UnidadeDeSaude() {
		this("desconhecido");
	}

	public UnidadeDeSaude(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public abstract double mediaDeMedicoPorPacienteEmUmDia();

}
