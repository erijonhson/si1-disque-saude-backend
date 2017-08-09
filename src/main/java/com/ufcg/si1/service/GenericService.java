package com.ufcg.si1.service;

import java.util.List;

public interface GenericService<T> {

	public T cadastrar(T t);

	public T atualizar(T t);

	public List<T> buscarTodos();

	public T buscarPorId(Long id);

	public void deletar(T t);

}
