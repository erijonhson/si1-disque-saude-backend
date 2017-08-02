package com.ufcg.si1.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PostoSaude.class, name = "posto")
})
public class UnidadeSaude {
    private int codigo;

    private String descricao;

    private List especialidades = new ArrayList();

    private long [] numeroQueixas = new long[1000];
    int contador = 0;

    public UnidadeSaude(String descricao) {
        this.codigo = 0; // gerado no repositorio
        this.descricao = descricao;
    }
    public UnidadeSaude(){
    }

    public void addQueixaProxima(long id) {
        if (this instanceof PostoSaude){
            numeroQueixas[contador++] = id;
        }
    }

    public String pegaDescricao() {
        return this.descricao;
    }

    public void mudaDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Especialidade> getEspecialidades() {
        return this.especialidades;
    }

    public void adicionarEspecialidade(Especialidade esp) {
        this.especialidades.add(esp);
    }

    public int pegaCodigo() {
        return this.codigo;
    }

    public void mudaCodigo(int cod) {
        this.codigo = cod;
    }

}
