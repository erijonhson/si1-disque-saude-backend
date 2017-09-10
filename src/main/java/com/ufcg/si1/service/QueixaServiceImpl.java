package com.ufcg.si1.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ufcg.si1.exception.ConflictRuntimeException;
import com.ufcg.si1.exception.ConstantesDeErro;
import com.ufcg.si1.exception.NotFoundRuntimeException;
import com.ufcg.si1.model.Queixa;
import com.ufcg.si1.queixa.state.QueixaAberta;
import com.ufcg.si1.queixa.state.QueixaAndamento;
import com.ufcg.si1.queixa.state.QueixaState;
import com.ufcg.si1.repository.QueixaRepository;
import com.ufcg.si1.repository.QueixaStateRepository;

@Service(value = "queixaService")
public class QueixaServiceImpl implements QueixaService {

	@Resource(name = "queixaRepository")
	QueixaRepository queixaRepository;
	
	@Resource(name = "queixaStateRepository")
	QueixaStateRepository queixaStateRepository;
	
	@Override
	public Queixa cadastrar(Queixa queixa) {
		try {
			return queixaRepository.save(queixa);
		} catch (Exception e) {
			throw new ConflictRuntimeException(ConstantesDeErro.QUEIXA_CONFLITO);
		}
	}

	@Override
	public Queixa atualizar(Queixa queixa) {
		return this.cadastrar(queixa);
	}

	@Override
	public List<Queixa> buscarTodos() {
		List<Queixa> queixas = queixaRepository.findAll();
		if (queixas == null || queixas.isEmpty()) {
			throw new NotFoundRuntimeException(ConstantesDeErro.QUEIXA_NAO_ENCONTRADA);
		}
		return queixas;
	}

	@Override
	public Queixa buscarPorId(Long id) {
		Queixa queixa = queixaRepository.findOne(id);
		if (queixa == null) {
			throw new NotFoundRuntimeException(ConstantesDeErro.QUEIXA_NAO_ENCONTRADA);
		}
		return queixa;
	}

	@Override
	public void deletar(Long id) {
		if (!queixaRepository.exists(id)) {
			throw new ConflictRuntimeException(ConstantesDeErro.QUEIXA_NAO_ENCONTRADA);
		}
		queixaRepository.delete(id);
	}

	public long quantidadeDeQueixas() {
		return queixaRepository.count();
	}

	public long quantidadeDeQueixasAbertas() {
		long totalAbertas = queixaRepository.countByState(new QueixaAberta());
		long totalEmAndamento = queixaRepository.countByState(new QueixaAndamento());
		return totalAbertas + totalEmAndamento;
	}

	public QueixaState saveState(QueixaState state) {
		try {
			return queixaStateRepository.save(state);
		} catch (Exception e) {
			throw new ConflictRuntimeException(ConstantesDeErro.QUEIXA_CONFLITO);
		}
	}

	@Override
	public Queixa mudaStateQueixa(Queixa queixa) {
		Queixa queixaBD = this.buscarPorId(queixa.getId());
		queixaBD.mudaStateQueixa();
		queixaBD = this.atualizar(queixaBD);
		return queixaBD;
	}

}
