package com.ufcg.si1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufcg.si1.model.UnidadeDeSaude;

@Repository("unidadeDeSaudeRepository")
public interface UnidadeDeSaudeRepository extends JpaRepository<UnidadeDeSaude, Long> {

	public List<UnidadeDeSaude> findByLocalBairro(String bairro);

	public List<UnidadeDeSaude> findByEspecialidadesId(Long idEspecialidade);

}
