package com.ufcg.si1.service;

import com.ufcg.si1.model.Endereco;

public interface EnderecoService extends GenericService<Endereco> {

	Endereco buscarPorRuaECidade(Endereco endereco);

}
