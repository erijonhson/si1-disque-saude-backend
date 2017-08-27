package com.ufcg.si1.service;


import com.ufcg.si1.model.Queixa;

public interface QueixaService extends GenericService<Queixa> {

	public long quantidadeDeQueixas();

	public long quantidadeDeQueixasAbertas();

	public Queixa fecharQueixa(Queixa queixa);

	public Queixa reabrirQueixa(Queixa queixa);

}
