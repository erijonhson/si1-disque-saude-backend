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
	public void deletar(UnidadeDeSaude unidadeDeSaude) {
		unidadeDeSaudeRepository.delete(unidadeDeSaude);
	}

	@Override
	public List<UnidadeDeSaude> findByBairro(String bairro) {
		return unidadeDeSaudeRepository.findByLocalBairro(bairro);
	}

}
