package com.ufcg.si1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.si1.model.UnidadeDeSaude;

public interface UnidadeDeSaudeRepository extends JpaRepository<UnidadeDeSaude, Long> {

	List<UnidadeDeSaude> findByLocalBairro(String bairro);

}
