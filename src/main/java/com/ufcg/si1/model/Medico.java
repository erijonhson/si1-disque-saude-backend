package com.ufcg.si1.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "medico_servidor")
public class Medico extends Servidor {

	public Medico (String nome, String email, String rua, String uf,
			String cidade, String codigoDeServidor) {
		setDadosFuncionario(nome, email, rua, uf, cidade, codigoDeServidor);
	}
}
