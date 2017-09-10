package com.ufcg.si1.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ufcg.si1.exception.ConflictRuntimeException;
import com.ufcg.si1.exception.ConstantesDeErro;
import com.ufcg.si1.exception.NotFoundRuntimeException;
import com.ufcg.si1.model.Especialidade;
import com.ufcg.si1.model.UnidadeDeSaude;
import com.ufcg.si1.repository.UnidadeDeSaudeRepository;

@Service(value = "unidadeDeSaudeService")
public class UnidadeDeSaudeServiceImpl implements UnidadeDeSaudeService {

	@Resource(name = "unidadeDeSaudeRepository")
	UnidadeDeSaudeRepository unidadeDeSaudeRepository;

	@Resource(name = "especialidadeService")
	EspecialidadeService especialidadeService;

	@Override
	public UnidadeDeSaude cadastrar(UnidadeDeSaude unidadeDeSaude) {
		try {
			return unidadeDeSaudeRepository.save(unidadeDeSaude);
		} catch (Exception e) {
			throw new ConflictRuntimeException(ConstantesDeErro.UNIDADE_DE_SAUDE_CONFLITO);
		}
	}

	@Override
	public UnidadeDeSaude atualizar(UnidadeDeSaude unidadeDeSaude) {
		return this.cadastrar(unidadeDeSaude);
	}

	@Override
	public List<UnidadeDeSaude> buscarTodos() {
		List<UnidadeDeSaude> unidadesDeSaude = unidadeDeSaudeRepository.findAll();
		return verifyNotFound(unidadesDeSaude);
	}

	@Override
	public UnidadeDeSaude buscarPorId(Long id) {
		UnidadeDeSaude unidadeDeSaude = unidadeDeSaudeRepository.findOne(id);
		if (unidadeDeSaude == null) {
			throw new NotFoundRuntimeException(ConstantesDeErro.UNIDADE_DE_SAUDE_NAO_ENCONTRADA);
		}
		return unidadeDeSaude;
	}

	@Override
	public void deletar(Long id) {
		if (!unidadeDeSaudeRepository.exists(id)) {
			throw new ConflictRuntimeException(ConstantesDeErro.UNIDADE_DE_SAUDE_NAO_ENCONTRADA);
		}
		unidadeDeSaudeRepository.delete(id);
	}

	@Override
	public List<UnidadeDeSaude> buscaPorBairro(String bairro) {
		List<UnidadeDeSaude> unidadesDeSaude = unidadeDeSaudeRepository.findByLocalBairro(bairro);
		return verifyNotFound(unidadesDeSaude);
	}

	@Override
	public List<UnidadeDeSaude> buscaPorEspecialidade(Long idEspecialidade) {
		List<UnidadeDeSaude> unidadesDeSaude = unidadeDeSaudeRepository.findByEspecialidadesId(idEspecialidade);
		return verifyNotFound(unidadesDeSaude);
	}

	@SuppressWarnings("finally")
	@Override
	public Especialidade incluirEspecialidadeEmUnidadeDeSaude(Long idUnidadeDeSaude, Especialidade especialidade) {
		UnidadeDeSaude unidadeDeSaudeBD = this.buscarPorId(idUnidadeDeSaude);
		try {
			Especialidade especialidadeBD = especialidadeService.buscarPorDescricao(especialidade.getDescricao());
			especialidade = especialidadeBD;
		} finally {
			unidadeDeSaudeBD.addEspecialidade(especialidade);
			unidadeDeSaudeBD = this.atualizar(unidadeDeSaudeBD);
			return especialidade;
		}
	}

	@Override
	public double mediaDeMedicoPorPacienteEmUmDia(long idUnidadeDeSaude) {
		UnidadeDeSaude unidadeDeSaude = this.buscarPorId(idUnidadeDeSaude);
		return unidadeDeSaude.mediaDeMedicoPorPacienteEmUmDia();
	}

	private List<UnidadeDeSaude> verifyNotFound(List<UnidadeDeSaude> unidadesDeSaude) {
		if (unidadesDeSaude == null || unidadesDeSaude.isEmpty()) {
			throw new NotFoundRuntimeException(ConstantesDeErro.UNIDADE_DE_SAUDE_NAO_ENCONTRADA);
		}
		return unidadesDeSaude;
	}

}
