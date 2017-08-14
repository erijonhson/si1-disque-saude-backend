package com.ufcg.si1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.si1.model.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {

	public List<Hospital> findByLocalBairro(String bairro);

}
