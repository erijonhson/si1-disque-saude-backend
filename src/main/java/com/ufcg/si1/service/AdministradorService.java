package com.ufcg.si1.service;

import com.ufcg.si1.model.Administrador;

public interface AdministradorService extends GenericService<Administrador> {

	public Administrador login(Administrador administrador);
	
	public Administrador buscarPorEmail(String email);

}
