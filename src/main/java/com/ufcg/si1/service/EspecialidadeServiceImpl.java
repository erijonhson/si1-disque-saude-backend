package com.ufcg.si1.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.si1.model.Especialidade;
import com.ufcg.si1.model.UnidadeDeSaude;
import com.ufcg.si1.repository.EspecialidadeRepository;

@Service("especialidadeService")
public class EspecialidadeServiceImpl implements EspecialidadeService {

	@Autowired
	EspecialidadeRepository especialidadeRepository;

	@Override
	public Especialidade cadastrar(Especialidade especialidade) {
		return especialidadeRepository.save(especialidade);
	}

	@Override
	public Especialidade atualizar(Especialidade especialidade) {
		return especialidadeRepository.save(especialidade);
	}

	@Override
	public List<Especialidade> buscarTodos() {
		return especialidadeRepository.findAll();
	}

	@Override
	public Especialidade buscarPorId(Long id) {
		return especialidadeRepository.findOne(id);
	}

	@Override
	public void deletar(Especialidade especialidade) {
		especialidadeRepository.delete(especialidade);
	}

	@Override
	public Collection<Especialidade> buscarEspecialidadesPorUnidadeDeSaude(UnidadeDeSaude unidadeDeSaude) {
		return especialidadeRepository.findByUnidadeDeSaudeId(unidadeDeSaude.getId());
	}
	
	

}
