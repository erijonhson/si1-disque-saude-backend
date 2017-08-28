package com.ufcg.si1.queixa.state;

import javax.persistence.Entity;

import com.ufcg.si1.model.Queixa;

@Entity
public class QueixaAberta extends QueixaState {
	
	public QueixaAberta() {
		super(1L, "ABERTA");
	}

	public QueixaAberta(Queixa queixa) {
		this();
		super.queixa = queixa;
	}

	@Override
	protected QueixaState next() {
		QueixaState next = new QueixaAndamento(this.queixa);
		this.queixa.setQueixaState(next);
		return next;
	}

}
