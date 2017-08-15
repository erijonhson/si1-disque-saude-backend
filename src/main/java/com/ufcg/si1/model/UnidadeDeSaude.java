package com.ufcg.si1.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

// TODO: existe UnidadeDeSaude que não é Hospital nem Posto?
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tb_unidade_de_saude")
public class UnidadeDeSaude implements Serializable {

	private static final long serialVersionUID = 1842734734661302140L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_unidade_de_saude")
	private Long id;

	@Column(name = "descricao")
	private String descricao;

	@OneToMany(mappedBy = "unidadeDeSaude")
	@JsonManagedReference(value="unidade-especialidade-movement")
	private Set<Especialidade> especialidades;

	@ManyToOne //(fetch = FetchType.LAZY)
	@JoinColumn(name = "endereco_id")
	@JsonBackReference(value="endereco-unidade-movement")
	private Endereco local;

	public UnidadeDeSaude() {
		this("desconhecido");
	}

	public UnidadeDeSaude(String descricao) {
		this.descricao = descricao;
		this.especialidades = new HashSet<>();
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Set<Especialidade> getEspecialidades() {
		return this.especialidades;
	}

	public void adicionarEspecialidade(Especialidade especialidade) {
		this.especialidades.add(especialidade);
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
