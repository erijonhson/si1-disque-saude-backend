package com.ufcg.si1.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_servidor_administrador")
public class Administrador extends Servidor {

	private static final long serialVersionUID = 1510491667389611246L;

	@Column(name = "senha")
	private String senha;

	public Administrador() {
		this("desconhecido", "desconhecido", "desconhecido", "desconhecido");
	}

	public Administrador(String nome, String email, String codigoDeServidor, String senha) {
		setDadosFuncionario(nome, email, codigoDeServidor);
		this.senha = senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenha() {
		return this.senha;
	}
}
