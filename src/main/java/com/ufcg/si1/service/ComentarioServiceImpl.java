package com.ufcg.si1.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ufcg.si1.exception.ConflictRuntimeException;
import com.ufcg.si1.exception.ConstantesDeErro;
import com.ufcg.si1.exception.NotFoundRuntimeException;
import com.ufcg.si1.model.Comentario;
import com.ufcg.si1.repository.ComentarioRepository;

@Service(value = "comentarioService")
public class ComentarioServiceImpl implements ComentarioService {

	@Resource(name = "comentarioRepository")
	private ComentarioRepository comentarioRepository;
	
	@Override
	public Comentario cadastrar(Comentario comentario) {
		try {
			return comentarioRepository.save(comentario);
		} catch(Exception e) {
			throw new ConflictRuntimeException(ConstantesDeErro.COMENTARIO_CONFLITO);
		}
	}

	@Override
	public Comentario atualizar(Comentario comentario) {
		return this.cadastrar(comentario);
	}

	@Override
	public List<Comentario> buscarTodos() {
		List<Comentario> comentarios = comentarioRepository.findAll();
		return verifyNotFound(comentarios);
	}

	@Override
	public Comentario buscarPorId(Long id) {
		Comentario comentario = comentarioRepository.findOne(id);
		if (comentario == null) {
			throw new NotFoundRuntimeException(ConstantesDeErro.COMENTARIO_NAO_ENCONTRADO);
		}
		return comentario;
	}

	@Override
	public void deletar(Long id) {
		if (!comentarioRepository.exists(id)) {
			throw new ConflictRuntimeException(ConstantesDeErro.COMENTARIO_NAO_ENCONTRADO);
		}
		comentarioRepository.delete(id);
	}

	@Override
	public List<Comentario> buscaTodosComentariosDeQueixa(Long id) {
		List<Comentario> comentarios = comentarioRepository.findByQueixaId(id);
		return verifyNotFound(comentarios);
	}

	private List<Comentario> verifyNotFound(List<Comentario> comentarios) {
		if (comentarios == null || comentarios.isEmpty()) {
			throw new NotFoundRuntimeException(ConstantesDeErro.COMENTARIO_NAO_ENCONTRADO);
		}
		return comentarios;
	}

}
