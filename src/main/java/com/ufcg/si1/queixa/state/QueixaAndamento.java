package com.ufcg.si1.queixa.state;

import javax.persistence.Entity;

import com.ufcg.si1.model.Queixa;

@Entity
public class QueixaAndamento extends QueixaState {

	public QueixaAndamento() {
		super.id = 2L;
		this.situacao = "EM ANDAMENTO";
	}
	
	public QueixaAndamento(Queixa queixa) {
		super(queixa);
		super.id = 2L;
		this.situacao = "EM ANDAMENTO";
	}

	@Override
	protected QueixaState next() {
		QueixaState next = new QueixaFechada(this.queixa);
		this.queixa.setQueixaState(next);
		return next;
	}

}
