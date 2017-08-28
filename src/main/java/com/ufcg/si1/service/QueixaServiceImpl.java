package com.ufcg.si1.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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

		long totalAbertas = queixaRepository.countByState(new QueixaAberta());
		long totalEmAndamento = queixaRepository.countByState(new QueixaAndamento());
		return totalAbertas + totalEmAndamento;

	}
	

	public QueixaState saveState(QueixaState state) {
		return queixaStateRepository.save(state);
	}
	
	
	@Override
	public Queixa mudaStateQueixa(Queixa queixa) {
		Queixa aFechar = buscarPorId(queixa.getId());
		QueixaState state = aFechar.mudaStateQueixa();
		// saveState(state);
		// aFechar.setQueixaState(state);
		aFechar = atualizar(aFechar);
		
		return aFechar;
	}

}
