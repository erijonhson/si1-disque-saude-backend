package com.ufcg.si1.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tb_unidade_de_saude_hospital")
public class Hospital extends UnidadeDeSaude {

	private static final long serialVersionUID = -58243804726375404L;

	@Transient // não salva no BD
	private br.edu.ufcg.Hospital hospital;
	
	@Column(name = "numero_de_medicos")
	private int numeroDeMedicos;

	@Column(name = "numero_de_pacientes_por_dia")
	private int numeroDePacientesPorDia;

	public Hospital() {
		this("desconhecido");
	}

	public Hospital(String descricao) {
		super(descricao);
	}

	@Override
	public String getDescricao() {
		return hospital.getDescricao();
	}

	@Override
	public void setDescricao(String descricao) {
		hospital.setDescricao(descricao);
		super.setDescricao(descricao);
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
		hospital.setNumeroPacientesDia(numeroDePacientesPorDia);
	}

}
