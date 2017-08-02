package com.ufcg.si1.model;

public class Endereco {

	private String rua;

	private String uf;

	private String cidade;

	public Endereco(){

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
}
