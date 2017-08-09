package com.ufcg.si1.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.si1.model.Queixa;
import com.ufcg.si1.repository.QueixaRepository;

// TODO: por enquanto vamos deixar passar os absurdos do contrato QueixaService
// mas tão logo refatoremos os Controllers, reavaliaremos esses contratos 
@Service("queixaService")
public class QueixaServiceImpl implements QueixaService {

	@Autowired
	QueixaRepository queixaRepository;

	@Override
	public List<Queixa> findAllQueixas() {
		return queixaRepository.findAll();
	}

	@Override
	public void saveQueixa(Queixa queixa) {
		queixaRepository.save(queixa);
	}

	@Override
	public Queixa findById(long id) {
		return queixaRepository.findOne(id);
	}

	@Override
	public void updateQueixa(Queixa queixa) {
		queixaRepository.save(queixa);
	}

	@Override
	public void deleteQueixaById(long id) {
		queixaRepository.delete(id);
	}

	@Override
	public int size() {
		return queixaRepository.findAll().size();
	}

	@Override
	public Iterator<Queixa> getIterator() {
		return queixaRepository.findAll().iterator();
	}
}
