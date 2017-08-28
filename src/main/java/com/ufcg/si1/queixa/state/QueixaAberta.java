package com.ufcg.si1.queixa.state;

import javax.persistence.Entity;

import com.ufcg.si1.model.Queixa;
import com.ufcg.si1.model.SituacaoDeQueixa;

@Entity
public class QueixaAberta extends QueixaState {
	
	public QueixaAberta() {}
	
	public QueixaAberta(Queixa queixa) {
		super(queixa);
		this.situacao = "ABERTA";
	}

	@Override
	protected QueixaState next() {
		QueixaState next = new QueixaAndamento(this.queixa);
		return next;
	}

}
