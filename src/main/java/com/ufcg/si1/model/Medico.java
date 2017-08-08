package com.ufcg.si1.model;

import javax.persistence.Entity;

@Entity
public class Medico extends Funcionario {

	public Medico (String nome, String email, String rua, String uf,
			String cidade, String codigoDeServidor) {
		setDadosFuncionario(nome, email, rua, uf, cidade, codigoDeServidor);
	}
}
