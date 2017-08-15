package com.ufcg.si1.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "tb_endereco")
public class Endereco implements Serializable {

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

	@OneToMany(mappedBy = "endereco")
	@JsonManagedReference(value="endereco-queixa-movement")
	private Set<Queixa> queixas;

	@OneToMany(mappedBy = "local")
	@JsonManagedReference(value="endereco-unidade-movement")
	private Set<UnidadeDeSaude> unidadesDeSaude;

	public Endereco() {
		this("desconhecido", "desconhecido", "desconhecido", "desconhecido");
	}

	public Endereco(String rua, String bairro, String uf, String cidade) {
		this.rua = rua;
		this.bairro = bairro;
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

	public Set<Queixa> getQueixas() {
		return queixas;
	}

	public Set<UnidadeDeSaude> getUnidadesDeSaude() {
		return unidadesDeSaude;
	}

	public void addQueixa(Queixa queixa) {
		this.queixas.add(queixa);
	}

	public void addUnidadeDeSaude(UnidadeDeSaude unidadeDeSaude) {
		this.unidadesDeSaude.add(unidadeDeSaude);
	}

	@Override
	public String toString() {
		return "[rua=" + rua + " uf=" + uf + " cidade=" + cidade + "]";
	}

}
