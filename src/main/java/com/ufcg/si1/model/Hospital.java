package com.ufcg.si1.model;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue(value = "hospital_unidade")
public class Hospital extends UnidadeDeSaude {

	@Transient // não salva no BD
	private br.edu.ufcg.Hospital hospital;

	public Hospital() {
		this("desconhecido");
	}

	public Hospital(String descricao) {
		super(descricao);
	}

	@Override
	public Long getId() {
		return (long) hospital.getCodigo();
	}

	@Override
	public void setId(Long id) {
		super.setId(id);
		hospital.setCodigo(id.intValue());
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

	@Override
	public Set<Especialidade> getEspecialidades() {
		// hospital.getEspecialidades().forEach();
		// solução confusa, não resolve nossos problemas
		return super.getEspecialidades();
	}

	// TODO: teremos que persistir isso?
	// observe a especificação do padrão adapter e aprecie a resposta
	public int getNumeroMedicos() {
		return hospital.getNumeroMedicos();
	}

	public void setNumeroMedicos(int numeroMedicos) {
		hospital.setNumeroMedicos(numeroMedicos);
	}

	// TODO: teremos que persistir isso?
	public int getNumeroPacientesDia() {
		return (int) hospital.getNumeroPacientesDia();
	}

	public void setNumeroPacientesDia(int numeroPacientesDia) {
		hospital.setNumeroPacientesDia(numeroPacientesDia);
	}

}
