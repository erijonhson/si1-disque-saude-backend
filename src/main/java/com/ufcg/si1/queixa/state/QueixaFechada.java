package com.ufcg.si1.queixa.state;

import javax.persistence.Entity;

import com.ufcg.si1.model.Queixa;

@Entity
public class QueixaFechada extends QueixaState {

	public QueixaFechada() {
		super(3L, "FECHADA");
	}
	
	public QueixaFechada(Queixa queixa) {
		this();
		super.queixa = queixa;
	}

	@Override
	protected QueixaState next() {
		QueixaState next = new QueixaAberta(this.queixa);
		this.queixa.setQueixaState(next);
		return next;
	}

}
