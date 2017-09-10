package com.ufcg.si1.service;

import java.util.List;

import com.ufcg.si1.model.Comentario;

public interface ComentarioService extends GenericService<Comentario> {

	public List<Comentario> buscaComentariosDeQueixa(Long idQueixa);

	
}
