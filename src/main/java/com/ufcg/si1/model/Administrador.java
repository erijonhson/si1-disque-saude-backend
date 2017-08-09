package com.ufcg.si1.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "administrador_servidor")
public class Administrador extends Servidor {
	
	@Column(name = "senha")
	private String senha;
	
	public Administrador() {
		this("desconhecido", "desconhecido", "desconhecido", "desconhecido",
				"desconhecido", "desconhecido", "desconhecido");
	}
	
	public Administrador(String nome, String email, String rua, String uf,
			String cidade, String codigoDeServidor, String senha) {
		setDadosFuncionario(nome, email, rua, uf, cidade, codigoDeServidor);
		this.senha = senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenha() {
		return this.senha;
	}
}
