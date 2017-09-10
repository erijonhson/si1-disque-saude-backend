package com.ufcg.si1.service;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ufcg.si1.exception.ConflictRuntimeException;
import com.ufcg.si1.exception.ConstantesDeErro;
import com.ufcg.si1.exception.NotFoundRuntimeException;
import com.ufcg.si1.model.Especialidade;
import com.ufcg.si1.repository.EspecialidadeRepository;

@Service(value = "especialidadeService")
public class EspecialidadeServiceImpl implements EspecialidadeService {

	@Resource(name = "especialidadeRepository")
	EspecialidadeRepository especialidadeRepository;

	@Override
	public Especialidade cadastrar(Especialidade especialidade) {
		try {
			return especialidadeRepository.save(especialidade);
		} catch (Exception e) {
			throw new ConflictRuntimeException(ConstantesDeErro.ESPECIALIDADE_CONFLITO);
		}
	}

	@Override
	public Especialidade atualizar(Especialidade especialidade) {
		return this.cadastrar(especialidade);
	}

	@Override
	public List<Especialidade> buscarTodos() {
		List<Especialidade> especialidades = especialidadeRepository.findAll();
		if (especialidades == null || especialidades.isEmpty()) {
			throw new NotFoundRuntimeException(ConstantesDeErro.ESPECIALIDADE_NAO_ENCONTRADA);
		}
		return especialidades;
	}

	@Override
	public Especialidade buscarPorId(Long id) {
		Especialidade especialidade = especialidadeRepository.findOne(id);
		return verifyNotFound(especialidade);
	}

	@Override
	public void deletar(Long id) {
		if (!especialidadeRepository.exists(id)) {
			throw new ConflictRuntimeException(ConstantesDeErro.ESPECIALIDADE_NAO_ENCONTRADA);
		}
		especialidadeRepository.delete(id);
	}

	@Override
	public Collection<Especialidade> buscarEspecialidadesPorUnidadeDeSaude(Long idUnidadeDeSaude) {
		Collection<Especialidade> especialidades = especialidadeRepository.findByUnidadesDeSaudeId(idUnidadeDeSaude);
		if (especialidades == null || especialidades.isEmpty()) {
			throw new NotFoundRuntimeException(ConstantesDeErro.ESPECIALIDADE_NAO_ENCONTRADA);
		}
		return especialidades;
	}

	@Override
	public Especialidade buscarPorDescricao(String descricao) {
		Especialidade especialidade = especialidadeRepository.findByDescricao(descricao);
		return verifyNotFound(especialidade);
	}

	private Especialidade verifyNotFound(Especialidade especialidade) {
		if (especialidade == null) {
			throw new NotFoundRuntimeException(ConstantesDeErro.ESPECIALIDADE_NAO_ENCONTRADA);
		}
		return especialidade;
	}

}
