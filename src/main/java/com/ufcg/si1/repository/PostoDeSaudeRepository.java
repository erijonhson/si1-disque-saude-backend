package com.ufcg.si1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.si1.model.PostoDeSaude;

public interface PostoDeSaudeRepository extends JpaRepository<PostoDeSaude, Long> {

	public List<PostoDeSaude> findByLocalBairro(String bairro);

}
