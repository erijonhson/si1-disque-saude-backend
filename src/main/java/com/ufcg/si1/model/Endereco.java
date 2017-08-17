package com.ufcg.si1.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_endereco")
public class Endereco implements Serializable {

	private static final long serialVersionUID = -2679280688929055056L;

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

	public String getBairro() {
		return bairro;
	}

	public String getUf() {
		return this.uf;
	}

	public String getCidade() {
		return this.cidade;
	}

	@Override
	public String toString() {
		return "rua=" + rua + " bairro=" + bairro + " uf=" + uf + " cidade=" + cidade;
	}

}
