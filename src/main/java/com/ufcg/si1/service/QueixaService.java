package com.ufcg.si1.service;

import java.util.Iterator;
import java.util.List;

import com.ufcg.si1.model.Queixa;

public interface QueixaService {

	List<Queixa> findAllQueixas();

	void saveQueixa(Queixa queixa);

	Queixa findById(long id);

	void updateQueixa(Queixa queixa);

	void deleteQueixaById(long id);

	// TODO: apagar esse contrato ridículo
	int size();

	// TODO: apagar esse contrato ridículo
	Iterator<Queixa> getIterator();

}
