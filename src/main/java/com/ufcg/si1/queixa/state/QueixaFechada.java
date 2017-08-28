package com.ufcg.si1.queixa.state;

import javax.persistence.Entity;

import com.ufcg.si1.model.Queixa;

@Entity
public class QueixaFechada extends QueixaState {

	public QueixaFechada() {}
	
	public QueixaFechada(Queixa queixa) {
		super(queixa);
		this.situacao = "FECHADA";
	}

	@Override
	protected QueixaState next() {
		QueixaState next = new QueixaAberta(this.queixa);
		
		atualizaQueixaState();
		
		return next;
	}

	protected void atualizaQueixaState() {
		this.queixa.setQueixaState(this);
	}
}
