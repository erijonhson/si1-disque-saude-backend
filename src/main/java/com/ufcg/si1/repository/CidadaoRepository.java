package com.ufcg.si1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufcg.si1.model.Cidadao;
import java.lang.String;

@Repository("cidadaoRepository")
public interface CidadaoRepository extends JpaRepository<Cidadao, Long> {

	public Cidadao findByEmail(String email);

}
