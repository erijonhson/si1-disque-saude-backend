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
@Table(name = "tb_especialidade")
public class Especialidade implements Serializable {

	private static final long serialVersionUID = 8749919939488270668L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_especialidade")
	private Long codigo;

	@Column(name = "descricao")
	private String descricao;

	@ManyToOne //(fetch = FetchType.LAZY)
	@JoinColumn(name = "unidade_de_saude_id")
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
