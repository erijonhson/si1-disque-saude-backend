package com.ufcg.si1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufcg.si1.model.Especialidade;

@Repository(value = "especialidadeRepository")
public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {

	public List<Especialidade> findByUnidadesDeSaudeId(Long idUnidadedesaude);

	public Especialidade findByDescricao(String descricao);

}
