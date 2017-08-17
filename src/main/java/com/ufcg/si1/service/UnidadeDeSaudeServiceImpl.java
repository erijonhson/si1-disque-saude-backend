package com.ufcg.si1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.si1.model.UnidadeDeSaude;
import com.ufcg.si1.repository.UnidadeDeSaudeRepository;

@Service("unidadeSaudeService")
public class UnidadeDeSaudeServiceImpl implements UnidadeDeSaudeService {

	@Autowired
	UnidadeDeSaudeRepository unidadeDeSaudeRepository;

	@Override
	public UnidadeDeSaude cadastrar(UnidadeDeSaude unidadeDeSaude) {
		return unidadeDeSaudeRepository.save(unidadeDeSaude);
	}

	@Override
	public UnidadeDeSaude atualizar(UnidadeDeSaude unidadeDeSaude) {
		return unidadeDeSaudeRepository.save(unidadeDeSaude);
	}

	@Override
	public List<UnidadeDeSaude> buscarTodos() {
		return unidadeDeSaudeRepository.findAll();
	}

	@Override
	public UnidadeDeSaude buscarPorId(Long id) {
		return unidadeDeSaudeRepository.findOne(id);
	}

	@Override
	public void deletar(Long id) {

		if (!unidadeDeSaudeRepository.exists(id)) {
			throw new RuntimeException("Unidade de Saúde inexistente ou inválida!");
		}

		unidadeDeSaudeRepository.delete(id);
	}

	@Override
	public List<UnidadeDeSaude> buscaPorBairro(String bairro) {
		return unidadeDeSaudeRepository.findByLocalBairro(bairro);
	}

}
