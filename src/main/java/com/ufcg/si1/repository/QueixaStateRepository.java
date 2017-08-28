package com.ufcg.si1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufcg.si1.queixa.state.QueixaState;

@Repository(value = "queixaStateRepository")
public interface QueixaStateRepository extends JpaRepository<QueixaState, Long> {

}
