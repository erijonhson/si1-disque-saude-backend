package com.ufcg.si1.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "servidor_cidadao")
public abstract class Servidor extends Cidadao {
	
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
	 * @param nome
	 * @param email
	 * @param rua
	 * @param uf
	 * @param cidade
	 * @param codigoDeServidor
	 */
	protected void setDadosFuncionario(String nome, String email, String rua, String bairro, String uf,
			String cidade, String codigoDeServidor) {
		setNome(nome);
		setEmail(email);
		setEndereco(new Endereco(rua, bairro, uf, cidade));
		setCodigoDeServidor(codigoDeServidor);
	}
}
