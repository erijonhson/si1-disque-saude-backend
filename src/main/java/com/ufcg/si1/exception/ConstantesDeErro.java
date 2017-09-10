package com.ufcg.si1.exception;

public class ConstantesDeErro {

	private static final String CONFLITO = "Erro ao cadastrar ou atualizar %s.";
	private static final String NAO_ENCONTRADO = "%s inexistente(s) ou inválido(s)!";
	private static final String NAO_ENCONTRADA = "%s inexistente(s) ou inválida(s)!";

	public static final String ADMINISTRADOR_CONFLITO = String.format(CONFLITO, "Administrador");
	public static final String ADMINISTRADOR_NAO_ENCONTRADO = String.format(NAO_ENCONTRADO, "Administrador(es)");

	public static final String CIDADAO_CONFLITO = String.format(CONFLITO, "Cidadão");
	public static final String CIDADAO_NAO_ENCONTRADO = String.format(NAO_ENCONTRADO, "Cidadão(s)");

	public static final String COMENTARIO_CONFLITO = String.format(CONFLITO, "Comentário");
	public static final String COMENTARIO_NAO_ENCONTRADO = String.format(NAO_ENCONTRADO, "Comentário(s)");

	public static final String ENDERECO_CONFLITO = String.format(CONFLITO, "Endereço");
	public static final String ENDERECO_NAO_ENCONTRADO = String.format(NAO_ENCONTRADO, "Endereço(s)");

	public static final String ESPECIALIDADE_CONFLITO = String.format(CONFLITO, "Especialidade");
	public static final String ESPECIALIDADE_NAO_ENCONTRADA = String.format(NAO_ENCONTRADA, "Especialidade(s)");

	public static final String LOGIN_INVALIDO = "Email ou senha inválidos!";

	public static final String QUEIXA_CONFLITO = String.format(CONFLITO, "Queixa");
	public static final String QUEIXA_NAO_ENCONTRADA = String.format(NAO_ENCONTRADA, "Queixa(s)");

	public static final String UNIDADE_DE_SAUDE_CONFLITO = String.format(CONFLITO, "Unidade de Saúde");
	public static final String UNIDADE_DE_SAUDE_NAO_ENCONTRADA = String.format(NAO_ENCONTRADA, "Unidade(s) de Saúde");

}
