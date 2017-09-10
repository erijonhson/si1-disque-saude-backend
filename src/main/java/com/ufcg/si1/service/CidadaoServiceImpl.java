package com.ufcg.si1.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ufcg.si1.exception.ConflictRuntimeException;
import com.ufcg.si1.exception.ConstantesDeErro;
import com.ufcg.si1.exception.NotFoundRuntimeException;
import com.ufcg.si1.model.Cidadao;
import com.ufcg.si1.repository.CidadaoRepository;

@Service(value = "cidadaoService")
public class CidadaoServiceImpl implements CidadaoService {

	@Resource(name = "cidadaoRepository")
	CidadaoRepository cidadaoRepository;

	@Override
	public Cidadao cadastrar(Cidadao cidadao) {
		try {
			return cidadaoRepository.save(cidadao);
		} catch(Exception e) {
			throw new ConflictRuntimeException(ConstantesDeErro.CIDADAO_CONFLITO);
		}
	}

	@Override
	public Cidadao atualizar(Cidadao cidadao) {
		try {
			Cidadao cidadaoBD = cidadaoRepository.findByEmail(cidadao.getEmail());
			if (cidadaoBD != null) {
				cidadao = cidadaoBD;
			}
			return cidadaoRepository.save(cidadao);
		} catch(Exception e) {
			throw new ConflictRuntimeException(ConstantesDeErro.CIDADAO_CONFLITO);
		}
	}

	@Override
	public List<Cidadao> buscarTodos() {
		List<Cidadao> cidadaos = cidadaoRepository.findAll();
		if (cidadaos == null || cidadaos.isEmpty()) {
			throw new NotFoundRuntimeException(ConstantesDeErro.CIDADAO_NAO_ENCONTRADO);
		}
		return cidadaos;
	}

	@Override
	public Cidadao buscarPorId(Long id) {
		Cidadao cidadao = cidadaoRepository.findOne(id);
		return verifyNotFound(cidadao);
	}

	@Override
	public void deletar(Long id) {
		if (!cidadaoRepository.exists(id)) {
			throw new ConflictRuntimeException(ConstantesDeErro.CIDADAO_NAO_ENCONTRADO);
		}
		cidadaoRepository.delete(id);
	}

	@Override
	public Cidadao buscarPorEmail(String email) {
		Cidadao cidadao = cidadaoRepository.findByEmail(email);
		return verifyNotFound(cidadao);
	}

	private Cidadao verifyNotFound(Cidadao cidadao) {
		if (cidadao == null) {
			throw new NotFoundRuntimeException(ConstantesDeErro.CIDADAO_NAO_ENCONTRADO);
		}
		return cidadao;
	}

}
