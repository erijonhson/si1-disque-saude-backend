package com.ufcg.si1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.si1.model.Endereco;
import com.ufcg.si1.repository.EnderecoRepository;

@Service("enderecoService")
public class EnderecoServiceImpl implements EnderecoService {

	@Autowired
	EnderecoRepository enderecoRepository;

	@Override
	public Endereco cadastrar(Endereco endereco) {
		return enderecoRepository.save(endereco);
	}

	@Override
	public Endereco atualizar(Endereco endereco) {
		return enderecoRepository.save(endereco);
	}

	@Override
	public List<Endereco> buscarTodos() {
		return enderecoRepository.findAll();
	}

	@Override
	public Endereco buscarPorId(Long id) {
		return enderecoRepository.findOne(id);
	}

	@Override
	public void deletar(Endereco endereco) {
		enderecoRepository.delete(endereco);
	}
	
	public Endereco buscarPorRuaECidade(Endereco endereco) {
		return enderecoRepository.findByRuaAndCidade(endereco.getRua(), endereco.getCidade());
	}

}
