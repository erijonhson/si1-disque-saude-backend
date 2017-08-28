package com.ufcg.si1.queixa.state;

import javax.persistence.Entity;

import com.ufcg.si1.model.Queixa;

@Entity
public class QueixaAndamento extends QueixaState {

	public QueixaAndamento() {
		super(2L, "EM ANDAMENTO");
	}
	
	public QueixaAndamento(Queixa queixa) {
		this();
		super.queixa = queixa;
	}

	@Override
	protected QueixaState next() {
		QueixaState next = new QueixaFechada(this.queixa);
		this.queixa.setQueixaState(next);
		return next;
	}

}
