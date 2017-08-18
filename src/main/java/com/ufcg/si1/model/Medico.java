package com.ufcg.si1.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_servidor_medico")
public class Medico extends Servidor {

	private static final long serialVersionUID = -6684509343541720221L;

	public Medico(String nome, String email, String codigoDeServidor) {
		setDadosFuncionario(nome, email, codigoDeServidor);
	}
}
