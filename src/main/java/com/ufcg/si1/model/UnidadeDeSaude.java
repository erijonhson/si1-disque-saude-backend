package com.ufcg.si1.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_unidade_de_saude", discriminatorType = DiscriminatorType.STRING, length = 30)
@DiscriminatorValue(value = "unidade")
@Table(name = "tb_unidade_de_saude")
public class UnidadeDeSaude {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_unidade_de_saude")
	private Long id;

	@Column(name = "descricao")
	private String descricao;

	@OneToMany(mappedBy = "unidadeDeSaude")
	@JsonManagedReference
	private Set<Especialidade> especialidades;

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
