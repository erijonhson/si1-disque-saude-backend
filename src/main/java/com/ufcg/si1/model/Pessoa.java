package com.ufcg.si1.model;

public class Pessoa extends Endereco {
	private String nome;

	private String email;

	public Pessoa(){
		super();
	}


	public Pessoa(String nome, String email, String rua, String uf,
			String cidade) {
		super(rua, uf, cidade);
		this.nome = nome;
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
