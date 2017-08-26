package com.ufcg.si1.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ufcg.si1.model.Cidadao;
import com.ufcg.si1.repository.CidadaoRepository;

@Service("cidadaoService")
public class CidadaoServiceImpl implements CidadaoService {

	@Resource
	@Qualifier("cidadaoRepository")
	CidadaoRepository cidadaoRepository;

	@Override
	public Cidadao cadastrar(Cidadao cidadao) {
		return cidadaoRepository.save(cidadao);
	}

	@Override
	public Cidadao atualizar(Cidadao cidadao) {
		Cidadao cidadaoBD = cidadaoRepository.findByEmail(cidadao.getEmail());
		if (cidadaoBD != null)
			return cidadaoRepository.save(cidadaoBD);
		else
			return cidadaoRepository.save(cidadao);
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
	public void deletar(Long id) {

		if (!cidadaoRepository.exists(id)) {
			throw new RuntimeException("Cidadao inexistente ou inválido!");
		}

		cidadaoRepository.delete(id);
	}

	@Override
	public Cidadao buscarPorEmail(String email) {
		return cidadaoRepository.findByEmail(email);
	}

}
