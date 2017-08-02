package com.ufcg.si1.model;


public class Especialidade {

    private int codigo;

    private String descricao;

    public Especialidade(String descricao) {
        this.codigo = 0; // gerado no repositorio
        this.descricao = descricao;
    }

    public Especialidade(){

    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int cod) {
        this.codigo = cod;
    }
}
