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
@Table(name = "tb_comentario")
public class Comentario implements Serializable {

	private static final long serialVersionUID = 5894930156191632828L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_comentario")
	private Long id;

	@Column(name = "descricao")
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "queixa_id")
	private Queixa queixa;

	public Comentario() {
		this.descricao = "Nada a declarar";
	}

	public Comentario(String descricao) {
		this.descricao = descricao;
	}

	public Long getId() {
		return this.id;
	}
	
	public void setDescrição(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public void setQueixa(Queixa queixa) {
		this.queixa = queixa;
	}

}
