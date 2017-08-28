package com.ufcg.si1.queixa.state;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ufcg.si1.model.Queixa;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tb_queixa_state")
public abstract class QueixaState {

	@Transient
	protected Queixa queixa;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_state_queixa")
	private Long id;

	@Column(name = "situacao")
	protected String situacao;

	public QueixaState(){}
	
	public QueixaState(Queixa queixa) {
		this.queixa = queixa;
	}
	
	public QueixaState mudaEstadoQueixa() {
		return next();
	}
	
	protected abstract QueixaState next();
	

	public String getSituacao() {
		return this.situacao;
	}
}
