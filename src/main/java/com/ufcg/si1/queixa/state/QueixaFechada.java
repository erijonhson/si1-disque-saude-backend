package com.ufcg.si1.queixa.state;

import javax.persistence.Entity;

import com.ufcg.si1.model.Queixa;

@Entity
public class QueixaFechada extends QueixaState {

	public QueixaFechada() {
		super.id = 3L;
		this.situacao = "FECHADA";
	}
	
	public QueixaFechada(Queixa queixa) {
		super(queixa);
		super.id = 3L;
		this.situacao = "FECHADA";
	}

	@Override
	protected QueixaState next() {
		QueixaState next = new QueixaAberta(this.queixa);
		this.queixa.setQueixaState(next);
		return next;
	}

}
