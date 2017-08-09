package com.ufcg.si1.service;

import java.util.List;

import com.ufcg.si1.model.UnidadeDeSaude;

public interface UnidadeDeSaudeService extends GenericService<UnidadeDeSaude> {

	List<UnidadeDeSaude> findByBairro(String bairro);

}
