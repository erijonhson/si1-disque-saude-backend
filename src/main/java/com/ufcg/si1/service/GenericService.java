package com.ufcg.si1.service;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ufcg.si1.model.Especialidade;
import com.ufcg.si1.model.UnidadeDeSaude;

@Service
public interface GenericService<T> {

	public T cadastrar(T t);

	public T atualizar(T t);

	public List<T> buscarTodos();

	public T buscarPorId(Long id);

	public void deletar(T t);

}
