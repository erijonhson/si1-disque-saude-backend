package com.ufcg.si1.queixa.state;

import javax.persistence.Entity;

import com.ufcg.si1.model.Queixa;

@Entity
public class QueixaAberta extends QueixaState {
	
	public QueixaAberta() {
		super.id = 1L;
		this.situacao = "ABERTA";
	}
	
	public QueixaAberta(Queixa queixa) {
		super(queixa);
		super.id = 1L;
		this.situacao = "ABERTA";
	}

	@Override
	protected QueixaState next() {
		QueixaState next = new QueixaAndamento(this.queixa);
		this.queixa.setQueixaState(next);
		return next;
	}

}
