package com.ufcg.si1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.si1.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

}
