package com.ufcg.si1.model.hospital;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.ufcg.si1.model.UnidadeDeSaude;

@Entity
@Table(name = "tb_unidade_de_saude_hospital")
public abstract class HospitalInterface extends UnidadeDeSaude {

	private static final long serialVersionUID = -58243804726375404L;

	@Column(name = "numero_de_medicos")
	private int numeroDeMedicos;

	@Column(name = "numero_de_pacientes_por_dia")
	private int numeroDePacientesPorDia;

	public HospitalInterface() {
		this("desconhecido", 0, 0);
	}

	public HospitalInterface(String descricao, int numeroDeMedicos, int numeroDePacientesPorDia) {
		super(descricao);
		this.numeroDeMedicos = numeroDeMedicos;
		this.numeroDePacientesPorDia = numeroDePacientesPorDia;
	}

	public int getNumeroDeMedicos() {
		return numeroDeMedicos;
	}

	public void setNumeroDeMedicos(int numeroDeMedicos) {
		this.numeroDeMedicos = numeroDeMedicos;
	}

	public int getNumeroDePacientesPorDia() {
		return numeroDePacientesPorDia;
	}

	public void setNumeroDePacientesPorDia(int numeroDePacientesPorDia) {
		this.numeroDePacientesPorDia = numeroDePacientesPorDia;
	}

}
