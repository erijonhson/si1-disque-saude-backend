package com.ufcg.si1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.si1.model.Queixa;
import com.ufcg.si1.model.SituacaoDeQueixa;
import com.ufcg.si1.repository.QueixaRepository;

@Service("queixaService")
public class QueixaServiceImpl implements QueixaService {

	@Autowired
	QueixaRepository queixaRepository;
	
	@Override
	public Queixa cadastrar(Queixa queixa) {
		return queixaRepository.save(queixa);
	}

	@Override
	public Queixa atualizar(Queixa queixa) {

		if (!queixaRepository.exists(queixa.getId())) {
			throw new RuntimeException("Queixa inexistente ou inválida!");
		}

		return queixaRepository.save(queixa);
	}

	@Override
	public List<Queixa> buscarTodos() {
		return queixaRepository.findAll();
	}

	@Override
	public Queixa buscarPorId(Long id) {
		return queixaRepository.findOne(id);
	}

	@Override
	public void deletar(Long id) {

		if (!queixaRepository.exists(id)) {
			throw new RuntimeException("Queixa inexistente ou inválida!");
		}

		queixaRepository.delete(id);
	}

	public long quantidadeDeQueixas() {

		return queixaRepository.count();

	}

	public long quantidadeDeQueixasAbertas() {

		return queixaRepository.countBySituacao(SituacaoDeQueixa.ABERTA) * 100;

	}

	public Queixa fecharQueixa(Queixa queixa) {
		
		Queixa aFechar = buscarPorId(queixa.getId());
		aFechar.fechar();
		aFechar = atualizar(aFechar);
		
		return aFechar;
	}
}
