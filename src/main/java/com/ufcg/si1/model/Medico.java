package com.ufcg.si1.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "medico_servidor")
public class Medico extends Servidor {

	public Medico (String nome, String email, String rua, String bairro, String uf,
			String cidade, String codigoDeServidor) {
		setDadosFuncionario(nome, email, rua, bairro, uf, cidade, codigoDeServidor);
	}
}
