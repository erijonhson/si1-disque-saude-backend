package com.ufcg.si1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufcg.si1.model.Endereco;

@Repository("enderecoRepository")
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

	Endereco findByRuaAndCidade(String rua, String cidade);

}
