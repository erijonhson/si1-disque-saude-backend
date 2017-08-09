package com.ufcg.si1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.si1.model.Cidadao;
import com.ufcg.si1.repository.CidadaoRepository;

@Service("cidadaoService")
public class CidadaoServiceImpl implements CidadaoService {

	@Autowired
	CidadaoRepository cidadaoRepository;

	@Override
	public Cidadao cadastrar(Cidadao cidadao) {
		return cidadaoRepository.save(cidadao);
	}

	@Override
	public Cidadao atualizar(Cidadao cidadao) {
		Cidadao cidadaoBD = cidadaoRepository.findByEmail(cidadao.getEmail());
		return cidadaoRepository.save(cidadaoBD);
	}

	@Override
	public List<Cidadao> buscarTodos() {
		return cidadaoRepository.findAll();
	}

	@Override
	public Cidadao buscarPorId(Long id) {
		return cidadaoRepository.findOne(id);
	}

	@Override
	public void deletar(Cidadao cidadao) {
		cidadaoRepository.delete(cidadao);
	}

}
