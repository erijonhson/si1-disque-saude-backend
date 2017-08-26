package com.ufcg.si1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufcg.si1.model.Medico;

@Repository("medicoRepository")
public interface MedicoRepository extends JpaRepository<Medico, Long> {

	public Medico findByEmail(String email);

}
