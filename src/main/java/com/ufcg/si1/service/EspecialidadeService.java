package com.ufcg.si1.service;

import java.util.Collection;

import com.ufcg.si1.model.Especialidade;

public interface EspecialidadeService extends GenericService<Especialidade> {

	public Collection<Especialidade> buscarEspecialidadesPorUnidadeDeSaude(Long idUnidadeDeSaude);

	public Especialidade buscarPorDescricao(String descricao);

}
