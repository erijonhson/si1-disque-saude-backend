package com.ufcg.si1.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_especialidade")
public class Especialidade implements Serializable {

	private static final long serialVersionUID = 8749919939488270668L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_especialidade")
	private Long id;

	@Column(name = "descricao", nullable = false)
	private String descricao;

	@ManyToMany(mappedBy="especialidades", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JsonIgnore
	private Set<UnidadeDeSaude> unidadesDeSaude;

	public Especialidade() {
		this("desconhecido");
	}

	public Especialidade(String descricao) {
		this.id = 0L;
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

	public Set<UnidadeDeSaude> getUnidadesDeSaude() {
		return unidadesDeSaude;
	}

	public void setUnidadesDeSaude(Set<UnidadeDeSaude> unidadesDeSaude) {
		this.unidadesDeSaude = unidadesDeSaude;
	}

	@Override
	public String toString() {
		return "id=" + id + " descricao=" + descricao;
	}

}
