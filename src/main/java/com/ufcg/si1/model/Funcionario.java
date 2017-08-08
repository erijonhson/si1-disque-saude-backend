package com.ufcg.si1.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public abstract class Funcionario extends Cidadao {
	
	@Column(name = "Codigo_do_Servidor")
	private String codigoDeServidor;
	
	public String getCodigoDeServidor() {
		return this.codigoDeServidor;
	}
	
	public void setCodigoDeServidor(String codigo) {
		this.codigoDeServidor = codigo;
	}
	
	/**
	 * Esse método adiciona os dados do Funcionario. Estes dados são referente
	 * @param nome
	 * @param email
	 * @param rua
	 * @param uf
	 * @param cidade
	 * @param codigoDeServidor
	 */
	protected void setDadosFuncionario(String nome, String email, String rua, String uf,
			String cidade, String codigoDeServidor) {
		setNome(nome);
		setEmail(email);
		setEndereço(rua, uf, cidade);
		setCodigoDeServidor(codigoDeServidor);
	}
}
