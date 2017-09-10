package com.ufcg.si1.service;


import java.util.Collection;

import com.ufcg.si1.model.Comentario;
import com.ufcg.si1.model.Queixa;

public interface QueixaService extends GenericService<Queixa> {

	public long quantidadeDeQueixas();

	public long quantidadeDeQueixasAbertas();

	public Queixa mudaStateQueixa(Queixa queixa);

	public Comentario adicionarComentario(Long idQueixa, Comentario comentario);

	public Collection<Comentario> buscarComentariosDeQueixa(Long idQueixa);

}
