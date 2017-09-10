package com.ufcg.si1.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ufcg.si1.exception.ConflictRuntimeException;
import com.ufcg.si1.exception.ConstantesDeErro;
import com.ufcg.si1.exception.NotFoundRuntimeException;
import com.ufcg.si1.model.Endereco;
import com.ufcg.si1.repository.EnderecoRepository;

@Service(value = "enderecoService")
public class EnderecoServiceImpl implements EnderecoService {

	@Resource(name = "enderecoRepository")
	EnderecoRepository enderecoRepository;

	@Override
	public Endereco cadastrar(Endereco endereco) {
		try {
			return enderecoRepository.save(endereco);
		} catch (Exception e) {
			throw new ConflictRuntimeException(ConstantesDeErro.ENDERECO_CONFLITO);
		}
	}

	@Override
	public Endereco atualizar(Endereco endereco) {
		return this.cadastrar(endereco);
	}

	@Override
	public List<Endereco> buscarTodos() {
		List<Endereco> enderecos = enderecoRepository.findAll();
		if (enderecos == null || enderecos.isEmpty()) {
			throw new NotFoundRuntimeException(ConstantesDeErro.ENDERECO_NAO_ENCONTRADO);
		}
		return enderecos;
	}

	@Override
	public Endereco buscarPorId(Long id) {
		Endereco endereco = enderecoRepository.findOne(id);
		return verifyNotFound(endereco);
	}

	@Override
	public void deletar(Long id) {
		if (!enderecoRepository.exists(id)) {
			throw new ConflictRuntimeException(ConstantesDeErro.ENDERECO_NAO_ENCONTRADO);
		}
		enderecoRepository.delete(id);
	}

	public Endereco buscarPorRuaECidade(Endereco endereco) {
		endereco = enderecoRepository.findByRuaAndCidade(endereco.getRua(), endereco.getCidade());
		return verifyNotFound(endereco);
	}

	private Endereco verifyNotFound(Endereco endereco) {
		if (endereco == null) {
			throw new NotFoundRuntimeException(ConstantesDeErro.ENDERECO_NAO_ENCONTRADO);
		}
		return endereco;
	}

}
