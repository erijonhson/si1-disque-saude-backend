package com.ufcg.si1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.si1.model.Queixa;
import com.ufcg.si1.model.SituacaoDeQueixa;

public interface QueixaRepository extends JpaRepository<Queixa, Long> {

	long countBySituacao(SituacaoDeQueixa situacao);

}
