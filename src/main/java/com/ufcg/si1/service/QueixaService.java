package com.ufcg.si1.service;


import com.ufcg.si1.model.Queixa;
import com.ufcg.si1.queixa.state.QueixaState;

public interface QueixaService extends GenericService<Queixa> {

	public long quantidadeDeQueixas();

	public long quantidadeDeQueixasAbertas();

	public Queixa mudaStateQueixa(Queixa queixa);
	
	public QueixaState saveState(QueixaState state);

}
