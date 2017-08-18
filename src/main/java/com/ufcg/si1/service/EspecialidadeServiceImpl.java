package com.ufcg.si1.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.si1.model.Especialidade;
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
	public void deletar(Long id) {

		if (!especialidadeRepository.exists(id)) {
			throw new RuntimeException("Especialidade inexistente ou inválida!");
		}

		especialidadeRepository.delete(id);
	}

	@Override
	public Collection<Especialidade> buscarEspecialidadesPorUnidadeDeSaude(Long idUnidadeDeSaude) {
		return especialidadeRepository.findByUnidadesDeSaudeId(idUnidadeDeSaude);
	}

	@Override
	public Especialidade buscarPorDescricao(String descricao) {
		return especialidadeRepository.findByDescricao(descricao);
	}

}
