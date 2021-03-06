package com.ufcg.si1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufcg.si1.model.Administrador;

@Repository(value = "administradorRepository")
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

	public Administrador findByEmail(String email);

	public Administrador findByEmailAndSenha(String email, String senha);

}
