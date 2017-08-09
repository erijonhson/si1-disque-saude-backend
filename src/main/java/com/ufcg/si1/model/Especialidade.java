package com.ufcg.si1.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "tb_especialidade")
public class Especialidade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_especialidade")
	private Long codigo;

	@Column(name = "descricao")
	private String descricao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unidade_de_saude_id")
	@JsonBackReference
	private UnidadeDeSaude unidadeDeSaude;

	public Especialidade() {
		this("desconhecido");
	}

	public Especialidade(String descricao) {
		this.codigo = 0L;
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Long cod) {
		this.codigo = cod;
	}

	public UnidadeDeSaude getUnidadeDeSaude() {
		return unidadeDeSaude;
	}

	public void setUnidadeDeSaude(UnidadeDeSaude unidadeDeSaude) {
		this.unidadeDeSaude = unidadeDeSaude;
	}
	
	@Override
	public String toString() {
		return "[codigo=" + codigo + " descricao=" + descricao + "]";
	}

}
