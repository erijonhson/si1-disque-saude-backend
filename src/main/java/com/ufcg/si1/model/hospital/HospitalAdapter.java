package com.ufcg.si1.model.hospital;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.edu.ufcg.Hospital;

/**
 * Esse adaptador serve para demonstrar domínio no uso do padrão adapter. No
 * entando, não há comportamentos da classe Hospital que possam ser adaptados e
 * aproveitados no sistema atual.
 */
@Entity
@Table(name = "tb_unidade_de_saude_hospital_adapter")
public class HospitalAdapter extends HospitalInterface {

	private static final long serialVersionUID = -1835922612111749517L;

	@JsonIgnore
	@Transient
	private Hospital hospital;

	public HospitalAdapter() {
		this.hospital = new Hospital("desconhecido", 0, 0.0F);
	}

	@Override
	public double mediaDeMedicoPorPacienteEmUmDia() {
		return super.getNumeroDeMedicos() / super.getNumeroDePacientesPorDia();
	}

}
