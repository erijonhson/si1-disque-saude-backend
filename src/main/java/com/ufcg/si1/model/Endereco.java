package com.ufcg.si1.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "tb_endereco")
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_endereco")
	private Long id;

	@Column(name = "rua")
	private String rua;

	@Column(name = "bairro")
	private String bairro;

	@Column(name = "cidade")
	private String cidade;

	@Column(name = "uf")
	private String uf;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "endereco")
	@JsonManagedReference
	private Set<Cidadao> moradores;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "local")
	@JsonManagedReference
	private Set<UnidadeDeSaude> unidadesDeSaude;

	public Endereco() {
		this("desconhecido", "desconhecido", "desconhecido");
	}

	public Endereco(String rua, String uf, String cidade) {
		this.rua = rua;
		this.uf = uf;
		this.cidade = cidade;
	}

	public String getRua() {
		return this.rua;
	}

	public String getUf() {
		return this.uf;
	}

	public String getCidade() {
		return this.cidade;
	}

	public Set<Cidadao> getMoradores() {
		return moradores;
	}

	public void setMoradores(Set<Cidadao> moradores) {
		this.moradores = moradores;
	}

	public void addMorador(Cidadao cidadao) {
		this.moradores.add(cidadao);
	}

	@Override
	public String toString() {
		return "[rua=" + rua + " uf=" + uf + " cidade=" + cidade + "]";
	}
}
