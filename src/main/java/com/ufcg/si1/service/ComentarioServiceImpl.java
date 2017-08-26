package com.ufcg.si1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ufcg.si1.model.Comentario;
import com.ufcg.si1.repository.ComentarioRepository;

@Service("comentarioService")
public class ComentarioServiceImpl implements ComentarioService {

	@Autowired
	@Qualifier("comentarioRepository")
	private ComentarioRepository comentarioRepository;
	
	@Override
	public Comentario cadastrar(Comentario comentario) {
		return comentarioRepository.save(comentario);
	}

	@Override
	public Comentario atualizar(Comentario comentario) {
		return comentarioRepository.save(comentario);
	}

	/**
	 * Este método sempre retorna null!
	 */
	@Override
	public List<Comentario> buscarTodos() {
		return null;
	}

	@Override
	public Comentario buscarPorId(Long id) {
		return comentarioRepository.getOne(id);
	}

	@Override
	public void deletar(Long id) {

		if (!comentarioRepository.exists(id)) {
			throw new RuntimeException("Comentário inexistente ou inválido!");
		}

		comentarioRepository.delete(id);
	}

	@Override
	public List<Comentario> buscaTodosComentariosDeQueixa(Long id) {
		return comentarioRepository.findByQueixaId(id);
	}

}
