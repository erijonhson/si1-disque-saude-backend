package com.ufcg.si1.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_cidadao_servidor")
public abstract class Servidor extends Cidadao {

	private static final long serialVersionUID = 8027223823168799125L;

	@Column(name = "codigo_de_servidor")
	private String codigoDeServidor;

	public String getCodigoDeServidor() {
		return this.codigoDeServidor;
	}

	public void setCodigoDeServidor(String codigo) {
		this.codigoDeServidor = codigo;
	}

	/**
	 * Esse método adiciona os dados do Funcionario. Estes dados são referente
	 * 
	 * @param nome
	 * @param email
	 * @param codigoDeServidor
	 */
	protected void setDadosFuncionario(String nome, String email, String codigoDeServidor) {
		setNome(nome);
		setEmail(email);
		setCodigoDeServidor(codigoDeServidor);
	}
}
