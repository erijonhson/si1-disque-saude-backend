package com.ufcg.si1.service;

import java.util.Collection;

import com.ufcg.si1.model.Especialidade;
import com.ufcg.si1.model.UnidadeDeSaude;

public interface EspecialidadeService extends GenericService<Especialidade> {

	public Collection<Especialidade> buscarEspecialidadesPorUnidadeDeSaude(UnidadeDeSaude unidadeDeSaude);

}
