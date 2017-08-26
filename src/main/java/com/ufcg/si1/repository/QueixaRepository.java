package com.ufcg.si1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufcg.si1.model.Queixa;
import com.ufcg.si1.model.SituacaoDeQueixa;

@Repository(value = "queixaRepository")
public interface QueixaRepository extends JpaRepository<Queixa, Long> {

	long countBySituacao(SituacaoDeQueixa situacao);

}
