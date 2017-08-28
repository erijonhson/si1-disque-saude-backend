package com.ufcg.si1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufcg.si1.model.Queixa;
import com.ufcg.si1.model.SituacaoDeQueixa;
import com.ufcg.si1.queixa.state.QueixaState;

@Repository(value = "queixaRepository")
public interface QueixaRepository extends JpaRepository<Queixa, Long> {

	long countByState(QueixaState state);

}
