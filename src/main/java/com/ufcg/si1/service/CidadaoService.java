package com.ufcg.si1.service;

import com.ufcg.si1.model.Cidadao;

public interface CidadaoService extends GenericService<Cidadao> {

	public Cidadao buscarPorEmail(String email);

}
